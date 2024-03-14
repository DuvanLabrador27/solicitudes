package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.persistence.dto.RequestVacationDto;
import com.semillero.solicitudes.services.interfaces.IRequestVacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Tag(name = "Request Vacation", description = "Request Vacation management")
public class RequestVacationController {

    private final IRequestVacationService requestVacationService;

    @Operation(
            summary = "Find all RequestVacation",
            description = "This endpoint is used to find all RequestVacation",
            tags = {"RequestVacation"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestVacationDto.class)), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json"))
    })
    @GetMapping(value = "requestVacation/{userId}", produces = "application/json")
    public ResponseEntity<?> getAllRequestVacationByUser(
            @PathVariable Long userId) {
        List<RequestVacationDto> request = requestVacationService.getAllRequestVacation(userId);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }


    @GetMapping("requestVacation/{requestId}/{userId}")
    public ResponseEntity<?> getRequestVacationByIdUser(
            @PathVariable Long requestId,
            @PathVariable Long userId) {
        List<RequestVacationDto> request = requestVacationService.getRequestVacationById(requestId, userId);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @Operation(
            summary = "Create RequestVacation",
            description = "This endpoint If the user has been working for less than a year," +
                    " then the number of vacation days is not passed. " +
                    "If he or she has been working for one year or more," +
                    " it is necessary to pass the number of days that" +
                    " he or she wishes to request.",
            tags = {"RequestVacation"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = RequestVacationDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Error.class), mediaType = "application/json"))
    })
    @PostMapping(value = "createRequest/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createRequest(
            @RequestBody RequestVacationDto requestVacationDto,
            @PathVariable Long userId) {
        RequestVacationDto request = this.requestVacationService.createRequestVacation(requestVacationDto, userId);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

}
