package com.journaldev.spring.dao;

import com.journaldev.spring.model.Employee;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class EmployeeDAO {

    private static final Logger logger =
            Logger.getLogger(EmployeeDAO.class.getName());

    // Change these to match your Oracle database.
    private static final String JDBC_URL =
            "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "SYSTEM";
    private static final String DB_PASSWORD = "NewPass123";

    private Connection getConnection() throws SQLException {
        logger.fine("Opening Oracle database connection");

        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Oracle JDBC driver not found", e);
            throw new SQLException("Oracle JDBC driver not found", e);
        }

        return DriverManager.getConnection(
                JDBC_URL, DB_USERNAME, DB_PASSWORD);
    }
    

    public List<Employee> findAll() throws SQLException {
        logger.info("DAO: Fetching all employees");

        String sql = "SELECT EMP_ID, NAME, DESIGNATION FROM EMPLOYEE";
        List<Employee> employees = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("EMP_ID"));
                employee.setName(rs.getString("NAME"));
                employee.setDesignation(rs.getString("DESIGNATION"));

                employees.add(employee);
            }

            logger.info("DAO: Retrieved " + employees.size() + " employees");
            return employees;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "DAO: Failed to fetch employees", e);
            throw e;
        }
    }

    public Employee findById(int id) throws SQLException {
        logger.info("DAO: Finding employee with ID " + id);

        String sql =
                "SELECT EMP_ID, NAME, DESIGNATION FROM EMPLOYEE WHERE EMP_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmpId(rs.getInt("EMP_ID"));
                    employee.setName(rs.getString("NAME"));
                    employee.setDesignation(rs.getString("DESIGNATION"));

                    logger.info("DAO: Employee found with ID " + id);
                    return employee;
                }
            }

            logger.warning("DAO: No employee found with ID " + id);
            return null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "DAO: Failed to find employee ID " + id, e);
            throw e;
        }
    }

    public void save(Employee employee) throws SQLException {
        logger.info("DAO: Inserting employee ID " + employee.getEmpId());

        String sql =
                "INSERT INTO EMPLOYEE (EMP_ID, NAME, DESIGNATION) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employee.getEmpId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getDesignation());

            ps.executeUpdate();

            logger.info("DAO: Employee inserted successfully");

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "DAO: Failed to insert employee", e);
            throw e;
        }
    }

    public void update(Employee employee) throws SQLException {
        logger.info("DAO: Updating employee ID " + employee.getEmpId());

        String sql =
                "UPDATE EMPLOYEE SET NAME = ?, DESIGNATION = ? WHERE EMP_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDesignation());
            ps.setInt(3, employee.getEmpId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                logger.warning("DAO: No employee updated for ID "
                        + employee.getEmpId());
            } else {
                logger.info("DAO: Employee updated successfully");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "DAO: Failed to update employee", e);
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        logger.info("DAO: Deleting employee ID " + id);

        String sql = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                logger.warning("DAO: No employee deleted for ID " + id);
            } else {
                logger.info("DAO: Employee deleted successfully");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "DAO: Failed to delete employee ID " + id, e);
            throw e;
        }
    }
}