package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.persistence.entities.RequestVacationEntity;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RequestVacationController {
    private final IRequestVacationService requestVacationService;
    public RequestVacationController(IRequestVacationService requestVacationService){
        this.requestVacationService =requestVacationService;
    }

    @GetMapping("requestVacation/{userId}")
    public ResponseEntity<?> getAllRequestVacationByUser(
            @PathVariable Long userId){
        List<RequestVacationDto> request = requestVacationService.getAllRequestVacation(userId);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }


    @GetMapping("requestVacation/{requestId}/{userId}")
    public ResponseEntity<?> getRequestVacationByIdUser(
            @PathVariable Long requestId,
            @PathVariable Long userId){
        List<RequestVacationDto> request = requestVacationService.getRequestVacationById(requestId, userId);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("createRequest/{userId}")
    public ResponseEntity<?> createRequest(
            @RequestBody RequestVacationDto requestVacationDto,
            @PathVariable Long userId){
        RequestVacationDto request = this.requestVacationService.createRequestVacation(requestVacationDto,userId);
        return new ResponseEntity<>(request,HttpStatus.CREATED);
    }

}
