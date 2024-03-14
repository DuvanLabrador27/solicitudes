package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.persistence.dto.UserDto;
import com.semillero.solicitudes.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Tag(name = "Users", description = "User management")
public class UserController {

    private final IUserService userService;

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto, @RequestParam Long employeeId) {
        UserDto user = this.userService.createUser(userDto, employeeId);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
