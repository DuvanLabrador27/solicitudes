package com.semillero.solicitudes.controllers;

import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeeDto> getEmployees() {
        return this.employeeService.getEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long employeeId) throws ResourceNotFoundException {
        EmployeeDto employee = this.employeeService.getEmployeeById(employeeId);
        if(employee == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employeesCreate")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employee) {
            EmployeeDto employeeDto = this.employeeService.createEmployee(employee);
            return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);

    }

    @PutMapping("/employeesUpdate/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employee) {
        EmployeeDto employeeDto = this.employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);

    }

}
