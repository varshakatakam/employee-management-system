package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

public class EmployeeDTO {

    private Long id;
    @NotBlank(message="name is required")
    @Pattern(
            regexp = "^[a-zA-Z\\s]+$",
            message = "name must contain only letters and spaces")
    private String name;
    @NotBlank(message="email is required")
    @Email(message="Please enter a valid email address")
    private String email;
    @NotBlank(message="department is required")
    private String department;
    @NotBlank(message="designation is required")
    private String designation;
    @NotNull(message="salary is required")
    @Positive(message="salary must be greater than zero")
    private Double salary;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public EmployeeDTO(Long id, String name, String email, String department, String designation, Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
    }
    public EmployeeDTO() {
    }
}
