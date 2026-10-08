package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.service.EmployeeService;


public class ServiceFactory {

    private final EmployeeService employeeService;

    public ServiceFactory(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}