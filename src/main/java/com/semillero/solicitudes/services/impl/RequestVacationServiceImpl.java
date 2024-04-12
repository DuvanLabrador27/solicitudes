package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceBadRequestException;
import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.enums.StatusRequestVacation;
import com.semillero.solicitudes.persistence.enums.TypeOfContract;
import com.semillero.solicitudes.persistence.mappers.RequestVacationMapper;
import com.semillero.solicitudes.persistence.repositories.RequestVacationRepository;
import com.semillero.solicitudes.persistence.repositories.UserRepository;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import com.semillero.solicitudes.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestVacationServiceImpl implements IRequestVacationService {

    private final RequestVacationRepository requestVacationRepository;
    private final UserRepository userRepository;
    private final RequestVacationMapper requestVacationMapper;

    @Override
    public List<RequestVacationDto> getAllRequestVacation(Long userId) {
        verifyUserExistence(userId);
        List<RequestVacationEntity> requestVacationEntities = this.requestVacationRepository.findAllRequestByUser(userId);
        if(requestVacationEntities.isEmpty()){
            throw new ResourceNotFoundException(Constants.LIST_NOT_FOUND_MESSAGE);
        }
        return requestVacationEntities.stream().map(req -> this.requestVacationMapper.requestVacationToRequestVacationDto(req)).collect(Collectors.toList());
    }

    @Override
    public List<RequestVacationDto> getRequestVacationById(Long requestId, Long userId) {
        verifyUserExistence(userId);
        List<RequestVacationEntity> requestVacationEntities = this.requestVacationRepository.findByNmIdRequestAndNmIdUser(requestId, userId);
        if(requestVacationEntities.isEmpty()){
            throw new ResourceNotFoundException(Constants.LIST_NOT_FOUND_MESSAGE);
        }
        return requestVacationEntities.stream().map(req -> this.requestVacationMapper.requestVacationToRequestVacationDto(req)).collect(Collectors.toList());
    }


    @Override
    public RequestVacationDto createRequestVacation(RequestVacationDto requestVacation, Long userId) {
        verifyUserExistence(userId);
        UserEntity user = this.userRepository.findById(userId).get();

        boolean periodProbation = validateMonth(user);
        boolean oneYear = validateOneYear(user);

        EmployeeEntity employee = user.getEmployeeEntity();
        if (employee == null) {
            throw new ResourceBadRequestException(Constants.EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        TypeOfContract contractType = employee.getDsTypeOfContract();
        if (contractType == null || (contractType != TypeOfContract.FIXED_TERM && contractType != TypeOfContract.INDEFINITE_TERM)) {
            throw new ResourceBadRequestException(Constants.TYPE_CONTRACT_MESSAGE);
        }

        if (periodProbation && !oneYear) {
            int availableDay = calculateDayAvailable(user);
            requestVacation.setNmNumberOfDaysRequested(availableDay);

            LocalDate reinstatementDate = reinstatementDate(requestVacation.getFeStartDate(), availableDay);
            requestVacation.setFeReinstatementDate(reinstatementDate);

            LocalDate endDate = calculateEndDate(requestVacation.getFeStartDate(), availableDay);
            if (isBusinessDay(endDate.plusDays(1))) {
                requestVacation.setFeEndDate(endDate);
                requestVacation.setFeReinstatementDate(endDate.plusDays(1));
            } else {
                LocalDate nextBusinessDay = searchBusinessDay(endDate.plusDays(1));
                requestVacation.setFeEndDate(endDate);
                requestVacation.setFeReinstatementDate(nextBusinessDay);
            }
        } else if (oneYear) {
            if (requestVacation.getNmNumberOfDaysRequested() == null) {
                throw new ResourceBadRequestException(Constants.NM_NUMBER_DAYS_NULL_MESSAGE);
            }
            if (requestVacation.getNmNumberOfDaysRequested() < 6 || requestVacation.getNmNumberOfDaysRequested() > 15) {
                throw new ResourceBadRequestException(Constants.NM_NUMBER_DAYS_BETWEEN_RANGE_MESSAGE);
            }

            LocalDate reinstatementDate = reinstatementDate(requestVacation.getFeStartDate(), requestVacation.getNmNumberOfDaysRequested());
            requestVacation.setFeReinstatementDate(reinstatementDate);

            LocalDate endDate = calculateEndDate(requestVacation.getFeStartDate(), requestVacation.getNmNumberOfDaysRequested());
            if (isBusinessDay(endDate.plusDays(1))) {
                requestVacation.setFeEndDate(endDate);
                requestVacation.setFeReinstatementDate(endDate.plusDays(1));
            } else {
                LocalDate nextBusinessDay = searchBusinessDay(endDate.plusDays(1));
                requestVacation.setFeEndDate(endDate);
                requestVacation.setFeReinstatementDate(nextBusinessDay);
            }

        } else {
            throw new ResourceBadRequestException(Constants.MONTHS_GREATER_THAN_TWO_MESSAGE);
        }

        validateVacationRequestDate(requestVacation.getFeStartDate());

        RequestVacationEntity requestVacationEntity = createRequest(requestVacation, user);
        RequestVacationEntity requestSaved = this.requestVacationRepository.save(requestVacationEntity);
        return this.requestVacationMapper.requestVacationToRequestVacationDto(requestSaved);
    }

    private void verifyUserExistence(Long id) {
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND_MESSAGE + id);
        }
    }

    //Improve
    private boolean validateOneYear(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();

        if (hireDate != null && currentDate != null) {
            Period period = Period.between(hireDate, currentDate);
            return period.getYears() >= 1;
        } else {
            return false;
        }
    }
    //Improve
    private boolean validateMonth(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();

        if (hireDate != null && currentDate != null) {
            Period period = Period.between(hireDate, currentDate);
            return period.getYears() == 0 && period.getMonths() > 2;
        } else {
            return false;
        }
    }

    //Improve
    private int calculateDayAvailable(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        if (hireDate == null) {
            return 0;
        }

        LocalDate current = LocalDate.now();

        long monthsWorked = Period.between(hireDate, current).toTotalMonths();

        int months = (int) monthsWorked;
        int maxDays = 15;
        int monthsYear = 12;
        double div = (double) months / monthsYear;
        int availableDays = (int) Math.ceil(div * maxDays);
        return availableDays;
    }


    private boolean isBusinessDay(LocalDate dateAvailable) {
        return dateAvailable.getDayOfWeek() != DayOfWeek.SATURDAY && dateAvailable.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    private LocalDate searchBusinessDay(LocalDate dateAvailable) {
        LocalDate nextDay = dateAvailable.plusDays(1);

        while (!isBusinessDay(nextDay)) {
            nextDay = nextDay.plusDays(1);
        }
        return nextDay;
    }


    private LocalDate reinstatementDate(LocalDate startDate, int availableDays) {
        LocalDate departureDay = startDate.plusDays(availableDays);
        return searchBusinessDay(departureDay);
    }


    private void validateVacationRequestDate(LocalDate startDate) {
        LocalDate today = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(today, startDate);

        if (daysDifference < 15) {
            throw new ResourceBadRequestException(Constants.VACATIONS_REQUEST_DAYS_MESSAGE);
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, int numberOfDaysRequested) {
        LocalDate endDate = startDate;
        int daysAdded = 1;
        while (daysAdded < numberOfDaysRequested) {
            endDate = endDate.plusDays(1);
            if (isBusinessDay(endDate)) {
                daysAdded++;
            }
        }
        return endDate;
    }


    private RequestVacationEntity createRequest(RequestVacationDto requestVacation, UserEntity user) {
        RequestVacationEntity requestVacationEntity = new RequestVacationEntity();
        requestVacationEntity.setNameRequest(requestVacation.getNameRequest());
        requestVacationEntity.setDescription(requestVacation.getDescription());
        requestVacationEntity.setNmNumberOfDaysRequested(requestVacation.getNmNumberOfDaysRequested());
        requestVacationEntity.setFeStartDate(requestVacation.getFeStartDate());
        requestVacationEntity.setFeEndDate(requestVacation.getFeEndDate());
        requestVacationEntity.setFeReinstatementDate(requestVacation.getFeReinstatementDate());
        requestVacationEntity.setDsStatus(StatusRequestVacation.PENDING);
        requestVacationEntity.setUserEntity(user);
        return requestVacationEntity;
    }

}
