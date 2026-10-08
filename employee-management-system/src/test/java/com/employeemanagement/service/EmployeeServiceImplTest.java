package com.employeemanagement.service;

import com.example.employeemanagement.service.impl.EmployeeServiceImpl;
import com.example.employeemanagement.repository.HibernateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.DuplicationEmailException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private HibernateRepository hibernateRepository;
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(hibernateRepository);
    }
    @Test
    void
    createEmployee_ShouldCreateEmployeesSuccessfully() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setName("Rahul Sharma");
        employeeDTO.setEmail("rahul.sharma@gmail.com");
        employeeDTO.setDepartment("Engineering");
        employeeDTO.setDesignation("Senior Software Developer");
        employeeDTO.setSalary(850000.0);
        when(hibernateRepository.existsByEmail(employeeDTO.getEmail()))
                .thenReturn(false);
        Employee savedEmployee=new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setName(employeeDTO.getName());
        savedEmployee.setEmail(employeeDTO.getEmail());
        savedEmployee.setDepartment(employeeDTO.getDepartment());
        savedEmployee.setDesignation(employeeDTO.getDesignation());
        savedEmployee.setSalary(employeeDTO.getSalary());
        when(hibernateRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);
        EmployeeDTO result=employeeService.createEmployee(employeeDTO);
        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals("Rahul Sharma",result.getName());
        assertEquals("rahul.sharma@gmail.com",result.getEmail());
        assertEquals("Engineering",result.getDepartment());
        assertEquals("Senior Software Developer",result.getDesignation());
        assertEquals(850000.0,result.getSalary());
        verify(hibernateRepository).save(any(Employee.class));
    }
    @Test
    void
    getEmployeeById_ShouldReturnEmployeeSuccessfully(){
        Employee employee=new Employee();
        employee.setId(1L);
        employee.setName("Rahul Sharma");
        employee.setEmail("rahul.sharma@gmail.com");
        employee.setDepartment("Engineering");
        employee.setDesignation("Senior Software Developer");
        employee.setSalary(850000.0);
        when(hibernateRepository.findById(1L))
                .thenReturn(java.util.Optional.of(employee));
        EmployeeDTO result=employeeService.getEmployeeById(1L);
        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals("Rahul Sharma",result.getName());
        assertEquals("rahul.sharma@gmail.com",result.getEmail());
        assertEquals("Engineering",result.getDepartment());
        assertEquals("Senior Software Developer",result.getDesignation());
        assertEquals(850000.0,result.getSalary());
        verify(hibernateRepository).findById(1L);
    }
    @Test
    void
    getEmployeeById_ShouldThrowException_WhenEmployeeNotFound(){
        when(hibernateRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(EmployeeNotFoundException.class,
                ()->
                employeeService.getEmployeeById(99L));
        verify(hibernateRepository).findById(99L);
    }
    @Test
    void
    createEmployee_ShouldThrowException_WhenEmailAlreadyExists() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("rahul.sharma@gmail.com");
        when(hibernateRepository.existsByEmail(employeeDTO.getEmail()))
                .thenReturn(true);
        assertThrows(DuplicationEmailException.class,
                () ->
                        employeeService.createEmployee(employeeDTO));
        verify(hibernateRepository,never()).save(any(Employee.class));
    }
    @Test
    void
    getAllEmployees_ShouldReturnAllEmployeesSuccessfully() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Rahul Sharma");
        employee1.setEmail("rahul.sharma@gmail.com");
        employee1.setDepartment("Engineering");
        employee1.setDesignation("Senior Software Developer");
        employee1.setSalary(850000.0);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Anil Kumar");
        employee2.setEmail("anil.kumar@gmail.com");
        employee2.setDepartment("IT");
        employee2.setDesignation("Software Engineer");
        employee2.setSalary(700000.0);
        when(hibernateRepository.findAll())
                .thenReturn(java.util.List.of(employee1, employee2));
        List<EmployeeDTO> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
        assertEquals("Rahul Sharma", result.get(0).getName());
        assertEquals("Anil Kumar", result.get(1).getName());
        verify(hibernateRepository).findAll();
    }
    @Test
    void
    updateEmployee_ShouldUpdateEmployeeSuccessfully() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Rahul Sharma");
        existingEmployee.setEmail("rahul.sharma@gmail.com");
        existingEmployee.setDepartment("Engineering");
        existingEmployee.setDesignation("Senior Software Developer");
        existingEmployee.setSalary(850000.0);

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setName("Rahul Sharma Updated");
        updatedEmployeeDTO.setEmail("rahul.sharma@gmail.com");
        updatedEmployeeDTO.setDepartment("Engineering");
        updatedEmployeeDTO.setDesignation("Lead Software Developer");
        updatedEmployeeDTO.setSalary(950000.0);

        when(hibernateRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingEmployee));
        when(hibernateRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        EmployeeDTO result = employeeService.updateEmployee(1L, updatedEmployeeDTO);
        assertNotNull(result);
        assertEquals("Rahul Sharma Updated", result.getName());
        assertEquals("Lead Software Developer", result.getDesignation());
        assertEquals(950000.0, result.getSalary());
        verify(hibernateRepository).save(any(Employee.class));
    }
    @Test
    void
    deleteEmployee_ShouldDeleteEmployeeSuccessfully() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul Sharma");
        employee.setEmail("rahul.sharma@gmail.com");

        when(hibernateRepository.findById(1L))
                .thenReturn(java.util.Optional.of(employee));
        employeeService.deleteEmployee(1L);
        verify(hibernateRepository).delete(employee);
    }
    @Test
    void
    deleteEmployee_ShouldThrowException_WhenEmployeeNotFound() {
        when(hibernateRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(EmployeeNotFoundException.class,
                () ->
                        employeeService.deleteEmployee(99L));
        verify(hibernateRepository, never()).delete(any(Employee.class));
    }
    @Test
    void testGetEmployeeWithHighestSalary() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Rahul Sharma");
        employee1.setEmail("rahul.sharma@gmail.com");
        employee1.setDepartment("Engineering");
        employee1.setDesignation("Senior Software Developer");
        employee1.setSalary(850000.0);

        when(hibernateRepository.findEmployeeWithHighestSalary())
                .thenReturn(Optional.of(employee1));
        EmployeeDTO result= employeeService.getEmployeeWithHighestSalary();
        assertEquals(850000.0, result.getSalary());
        verify(hibernateRepository).findEmployeeWithHighestSalary();
    }
    @Test
    void testGetEmployeeByDepartment() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Rahul Sharma");
        employee.setEmail("rahul.sharma@gmail.com");
        employee.setDepartment("Engineering");
        employee.setDesignation("Senior Software Developer");
        employee.setSalary(850000.0);

        when(hibernateRepository.findEmployeesByDepartment("Engineering"))
                .thenReturn(List.of(employee));

        List<EmployeeDTO> result = employeeService.getEmployeesByDepartment("Engineering");
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getDepartment());
        verify(hibernateRepository).findEmployeesByDepartment("Engineering");
    }
}