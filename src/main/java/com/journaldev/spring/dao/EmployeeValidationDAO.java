
package com.journaldev.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.journaldev.spring.model.EmployeeValidation;

public class EmployeeValidationDAO {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:xe";

    private static final String USERNAME = "SYSTEM";
    private static final String PASSWORD = "NewPass123";

    public boolean save(EmployeeValidation employee) {

        String sql = "INSERT INTO EMPLOYEE_VALIDATION "
                   + "(ID, EMP_NAME, CITY) "
                   + "VALUES (EMPLOYEE_VALIDATION_SEQ.NEXTVAL, ?, ?)";

        try (Connection con = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, employee.getEmpName());
            ps.setString(2, employee.getCity());

            int rows = ps.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
