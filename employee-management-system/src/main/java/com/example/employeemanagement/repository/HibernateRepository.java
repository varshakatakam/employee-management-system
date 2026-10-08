package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface HibernateRepository {

    Employee save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    void delete(Employee employee);

    boolean existsByEmail(String email);

    Optional<Employee>findEmployeeWithHighestSalary();

    List<Employee> findEmployeesByDepartment(String department);
}
