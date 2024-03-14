package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceBadRequestException;
import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestVacationServiceImpl implements IRequestVacationService {

    private final RequestVacationRepository requestVacationRepository;
    private final UserRepository userRepository;
    private final RequestVacationMapper requestVacationMapper;

    @Override
    public List<RequestVacationDto> getAllRequestVacation(Long userId) {
        List<RequestVacationEntity> requestVacationEntities = this.requestVacationRepository.findAllRequestByUser(userId);
        return requestVacationEntities.stream().map(req -> this.requestVacationMapper.requestVacationToRequestVacationDto(req)).collect(Collectors.toList());
    }

    @Override
    public List<RequestVacationDto> getRequestVacationById(Long requestId, Long userId) {
        List<RequestVacationEntity> requestVacationEntities = this.requestVacationRepository.findByNmIdRequestAndNmIdUser(requestId, userId);
        return requestVacationEntities.stream().map(req -> this.requestVacationMapper.requestVacationToRequestVacationDto(req)).collect(Collectors.toList());
    }


    @Override
    public RequestVacationDto createRequestVacation(RequestVacationDto requestVacation, Long userId) {
        verifyUserExistence(userId);
        UserEntity user = this.userRepository.findById(userId).get();

        boolean periodProbation = validateMonth(user);
        boolean oneYear = validateOneYear(user);


        TypeOfContract contractType = user.getEmployeeEntity().getDsTypeOfContract();
        if (contractType != TypeOfContract.FIXED_TERM && contractType != TypeOfContract.INDEFINITE_TERM) {
            throw new ResourceBadRequestException("Vacation request can only be created for employees with fixed term or indefinite term contracts");
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
                throw new ResourceBadRequestException("You must enter the number of vacation days");
            }
            if (requestVacation.getNmNumberOfDaysRequested() < 6 || requestVacation.getNmNumberOfDaysRequested() > 15) {
                throw new ResourceBadRequestException("Number of vacation days must be between 6 and 15");
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
            throw new ResourceBadRequestException("Vacations can only be requested if you have more than 2 months");
        }

        validateVacationRequestDate(requestVacation.getFeStartDate());

        RequestVacationEntity requestVacationEntity = createRequest(requestVacation, user);
        RequestVacationEntity requestSaved = this.requestVacationRepository.save(requestVacationEntity);
        return this.requestVacationMapper.requestVacationToRequestVacationDto(requestSaved);
    }

    private void verifyUserExistence(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND_MESSAGE + id);
        }
    }

    private boolean validateOneYear(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(hireDate, currentDate);

        if (period.getYears() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateMonth(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(hireDate, currentDate);

        if (period.getYears() == 0 && period.getMonths() > 2) {
            return true;
        } else {
            return false;
        }
    }


    private int calculateDayAvailable(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
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
            throw new ResourceBadRequestException("Vacation request must be made at least 15 days before the start date");
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
