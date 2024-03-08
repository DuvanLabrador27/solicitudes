package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.RequestVacationDto;

import java.util.List;

public interface IRequestVacationService {

    public List<RequestVacationDto> getAllRequestVacation(Long userId);
    public List<RequestVacationDto> getRequestVacationById(Long requestId, Long userId);
    public RequestVacationDto createRequestVacation(RequestVacationDto requestVacation, Long userId);


}
