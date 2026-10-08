package com.example.employeemanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.impl.ServiceFactory;

@Configuration
public class HibernateConfig {
    @Bean
    public ServiceFactory serviceFactory(EmployeeService employeeService){
        return new ServiceFactory(employeeService);
    }
}
