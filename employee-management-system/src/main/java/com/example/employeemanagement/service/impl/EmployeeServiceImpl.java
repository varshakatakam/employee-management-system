package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.DuplicationEmailException;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.HibernateRepository;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final HibernateRepository hibernateRepository;

    public EmployeeServiceImpl(HibernateRepository hibernateRepository) {
        this.hibernateRepository = hibernateRepository;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        if (hibernateRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new DuplicationEmailException(
                    "Employee with email " + employeeDTO.getEmail() + " already exists"
            );
        }

        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getDepartment(),
                employeeDTO.getDesignation(),
                employeeDTO.getSalary()
        );

        Employee savedEmployee = hibernateRepository.save(employee);

        return new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartment(),
                savedEmployee.getDesignation(),
                savedEmployee.getSalary()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return hibernateRepository.findAll()
                .stream()
                .map(employee -> new EmployeeDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getDepartment(),
                        employee.getDesignation(),
                        employee.getSalary()
                ))
                .toList();
    }
    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = hibernateRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getSalary()
        );
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Employee employee = hibernateRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
          if(!
          employee.getEmail().equals(employeeDTO.getEmail())
                  &&
            hibernateRepository.existsByEmail(employeeDTO.getEmail())
          ){
              throw new DuplicationEmailException(
                      "Employee already exists with email:"+ employeeDTO.getEmail()
              );
          }
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = hibernateRepository.save(employee);

        return new EmployeeDTO(
                updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getDepartment(),
                updatedEmployee.getDesignation(),
                updatedEmployee.getSalary()
        );
    }


    @Override
    public void deleteEmployee(Long id) {

        Employee employee = hibernateRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        hibernateRepository.delete(employee);
    }

    @Override
    public EmployeeDTO
    getEmployeeWithHighestSalary(){

        Employee employee=
                hibernateRepository.findEmployeeWithHighestSalary()
                        .orElseThrow(()-> new
                EmployeeNotFoundException("No employees found"));
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getSalary()
        );
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        List<Employee> employees=
                hibernateRepository.findEmployeesByDepartment(department);
        List<EmployeeDTO> employeeDTOs=new ArrayList<>();
        for(Employee employee : employees){
            employeeDTOs.add(new EmployeeDTO(
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getDepartment(),
                    employee.getDesignation(),
                    employee.getSalary()
            ));
        }
        return employeeDTOs;
    }
}
