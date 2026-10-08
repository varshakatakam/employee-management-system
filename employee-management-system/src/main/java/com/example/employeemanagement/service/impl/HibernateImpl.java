package com.example.employeemanagement.service.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.HibernateRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import jakarta.persistence.EntityManagerFactory;

@Repository
public class HibernateImpl implements HibernateRepository {

    private final SessionFactory sessionFactory;

    public HibernateImpl(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    @Transactional
    public Employee save(Employee employee){
        Session session = sessionFactory.getCurrentSession();
        session.persist(employee);
        return employee;
    }

    @Override
    public List<Employee>findAll(){
        Session session=sessionFactory.getCurrentSession();
        return session
                .createQuery("FROM Employee e", Employee.class).getResultList();
    }

    @Override
    public Optional<Employee>findById(Long id){
        Session session=sessionFactory.getCurrentSession();
        Employee employee = session.find(Employee.class, id);
        return Optional.ofNullable(employee);
    }

    @Override
    @Transactional
    public void delete(Employee employee){
        Session session = sessionFactory.getCurrentSession();
        if(!session.contains(employee)){
            employee = session.merge(employee);
        }
        session.remove(employee);
    }

    @Override
    public boolean existsByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Long count = session.createQuery(
                "SELECT COUNT(e) FROM Employee e WHERE e.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<Employee> findEmployeeWithHighestSalary() {
        Session session=sessionFactory.getCurrentSession();
        List<Employee>employees=session.createQuery(
                "FROM Employee e ORDER BY e.salary DESC", Employee.class)
                .setMaxResults(1)
                .getResultList();
        return employees.stream().findFirst();
    }

    @Override
    public List<Employee> findEmployeesByDepartment(String department) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("FROM Employee e WHERE e.department = :department", Employee.class)
                .setParameter("department", department)
                .getResultList();
    }

}