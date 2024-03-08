package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceBadRequestException;
import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import com.semillero.solicitudes.persistence.entities.UserEntity;
import com.semillero.solicitudes.persistence.enums.StatusRequestVacation;
import com.semillero.solicitudes.persistence.mappers.RequestVacationMapper;
import com.semillero.solicitudes.persistence.repositories.RequestVacationRepository;
import com.semillero.solicitudes.persistence.repositories.UserRepository;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestVacationServiceImpl implements IRequestVacationService {
    private final RequestVacationRepository requestVacationRepository;
    private final UserRepository userRepository;
    private final RequestVacationMapper requestVacationMapper;


    public RequestVacationServiceImpl(RequestVacationRepository requestVacationRepository, UserRepository userRepository, RequestVacationMapper requestVacationMapper) {
        this.requestVacationRepository = requestVacationRepository;
        this.userRepository = userRepository;
        this.requestVacationMapper = requestVacationMapper;
    }


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

        boolean oneYear = validateOneYear(user);
        boolean periodProbation = !oneYear && validateMonth(user);



        if(oneYear){
            if ( requestVacation.getNmNumberOfDaysRequested()<1 || requestVacation.getNmNumberOfDaysRequested()>15){
                throw new ResourceBadRequestException("Number of vacation days must be between 1 and 15");
            }
        }

        RequestVacationEntity requestVacationEntity = createRequest(requestVacation, user);
        RequestVacationEntity requestSaved = this.requestVacationRepository.save(requestVacationEntity);
        return this.requestVacationMapper.requestVacationToRequestVacationDto(requestSaved);
    }

    public void verifyUserExistence(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    public boolean validateOneYear(UserEntity user) {
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate vacationVerify = hireDate.plusYears(1);

        if (currentDate.isBefore(vacationVerify)) {
            throw new ResourceBadRequestException("Vacations can only be requested after one year of employment, try in another moment");
        } else {
            return true;
        }
    }

    public boolean validateMonth(UserEntity user){
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate vacationVerify = hireDate.plusMonths(2);
        if (currentDate.isBefore(vacationVerify)) {
            return false;
        } else {
            return true;
        }
    }

    public int calculateDayAvailable(UserEntity user){
        LocalDate hireDate = user.getEmployeeEntity().getFeHireDate();
        LocalDate current = LocalDate.now();

        long monthsWorked = Period.between(hireDate,current).toTotalMonths();
        int maxDays = 15;
        int monthsYear = 12;
        int availableDays = (int) Math.round((double) (monthsWorked/monthsYear) * maxDays);
        return availableDays;
    }

    private RequestVacationEntity createRequest(RequestVacationDto requestVacation, UserEntity user){
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
