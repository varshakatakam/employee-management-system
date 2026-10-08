package com.example.employeemanagement.controller;

import com.example.employeemanagement.service.impl.ServiceFactory;
import org.springframework.http.HttpStatus;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(ServiceFactory serviceFactory) {
        this.employeeService=serviceFactory.getEmployeeService();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.createEmployee(employeeDTO);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/highest-salary")
    public EmployeeDTO
    getEmployeeWithHighestSalary(){
        return employeeService.getEmployeeWithHighestSalary();
    }

    @GetMapping("/department/{department}")
    public List<EmployeeDTO>
    getEmployeesByDepartment(@PathVariable String department){
        return employeeService.getEmployeesByDepartment(department);
    }
}
