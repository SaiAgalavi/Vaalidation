package com.journaldev.spring.service;

import com.journaldev.spring.dao.EmployeeDAO;
import com.journaldev.spring.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private static final Logger logger =
            Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    private EmployeeDAO employeeDAO;

    public List<Employee> findAll() throws SQLException {
        logger.info("Service: Request to fetch all employees");
        return employeeDAO.findAll();
    }

    public Employee findById(int id) throws SQLException {
        logger.info("Service: Request to find employee ID " + id);

        if (id <= 0) {
            logger.warning("Service: Invalid employee ID " + id);
            throw new IllegalArgumentException("Employee ID must be positive");
        }

        return employeeDAO.findById(id);
    }

    public void save(Employee employee) throws SQLException {
        logger.info("Service: Request to save employee ID "
                + employee.getEmpId());

        validateEmployee(employee);

        if (employee.getEmpId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be a positive number");
        }

        Employee existing = employeeDAO.findById(employee.getEmpId());

        if (existing != null) {
            logger.warning("Service: Duplicate employee ID "
                    + employee.getEmpId());
            throw new IllegalArgumentException(
                    "An employee with this ID already exists");
        }

        employeeDAO.save(employee);
        logger.info("Service: Employee saved");
    }

    public void update(Employee employee) throws SQLException {
        logger.info("Service: Request to update employee ID "
                + employee.getEmpId());

        validateEmployee(employee);

        if (employee.getEmpId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be a positive number");
        }

        Employee existing = employeeDAO.findById(employee.getEmpId());

        if (existing == null) {
            logger.warning("Service: Employee not found for update, ID "
                    + employee.getEmpId());
            throw new IllegalArgumentException("Employee not found");
        }

        employeeDAO.update(employee);
        logger.info("Service: Employee updated");
    }

    public void delete(int id) throws SQLException {
        logger.info("Service: Request to delete employee ID " + id);

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be a positive number");
        }

        Employee existing = employeeDAO.findById(id);

        if (existing == null) {
            logger.warning("Service: Employee not found for deletion, ID "
                    + id);
            throw new IllegalArgumentException("Employee not found");
        }

        employeeDAO.delete(id);
        logger.info("Service: Employee deleted");
    }

    private void validateEmployee(Employee employee) {
        if (employee == null) {
            logger.warning("Service: Employee object is null");
            throw new IllegalArgumentException("Employee details are required");
        }

        if (employee.getName() == null
                || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }

        if (employee.getDesignation() == null
                || employee.getDesignation().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Employee designation is required");
        }
    }
}