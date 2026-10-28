package com.journaldev.spring.controller;


//validation
import com.journaldev.spring.model.EmployeeValidation;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;


import com.journaldev.spring.model.Employee;
import com.journaldev.spring.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private static final Logger logger =
            Logger.getLogger(HomeController.class.getName());

    @Autowired
    private EmployeeService employeeService;

    // Display employee list
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String listEmployees(Model model) {
        logger.info("Controller: GET /employees");

        try {
            List<Employee> employees = employeeService.findAll();
            model.addAttribute("employees", employees);

            logger.info("Controller: Displaying "
                    + employees.size() + " employees");

            return "employees";

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: Could not load employee list", e);

            model.addAttribute("errorMessage",
                    "Unable to load employees. Check the database connection.");

            return "employees";
        }
    }

    // Display regular add form, if you use it
    @RequestMapping(value = "/employee/new", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        logger.info("Controller: GET /employee/new");

        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    // Save using regular form
    @RequestMapping(value = "/employee/save", method = RequestMethod.POST)
    public String saveEmployee(
            Employee employee,
            RedirectAttributes redirectAttributes) {

        logger.info("Controller: POST /employee/save");

        try {
            employeeService.save(employee);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Employee added successfully");

            return "redirect:/employees";

        } catch (IllegalArgumentException e) {
            logger.warning("Controller: Save rejected - " + e.getMessage());

            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());

            return "redirect:/employees";

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: Database error while saving employee", e);

            redirectAttributes.addFlashAttribute(
                    "errorMessage", getDatabaseErrorMessage(e));

            return "redirect:/employees";
        }
    }

    // Display regular edit form, if you use it
    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        logger.info("Controller: GET /employee/edit/" + id);

        try {
            Employee employee = employeeService.findById(id);

            if (employee == null) {
                redirectAttributes.addFlashAttribute(
                        "errorMessage", "Employee not found");
                return "redirect:/employees";
            }

            model.addAttribute("employee", employee);
            return "employee-form";

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: Could not load employee for editing", e);

            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Unable to load employee");

            return "redirect:/employees";
        }
    }

    // Update using regular form
    @RequestMapping(value = "/employee/update", method = RequestMethod.POST)
    public String updateEmployee(
            Employee employee,
            RedirectAttributes redirectAttributes) {

        logger.info("Controller: POST /employee/update");

        try {
            employeeService.update(employee);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Employee updated successfully");

            return "redirect:/employees";

        } catch (IllegalArgumentException e) {
            logger.warning("Controller: Update rejected - " + e.getMessage());

            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());

            return "redirect:/employees";

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: Database error while updating employee", e);

            redirectAttributes.addFlashAttribute(
                    "errorMessage", getDatabaseErrorMessage(e));

            return "redirect:/employees";
        }
    }

    // Delete using regular form/link
    @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.POST)
    public String deleteEmployee(
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {

        logger.info("Controller: POST /employee/delete/" + id);

        try {
            employeeService.delete(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "Employee deleted successfully");

        } catch (IllegalArgumentException e) {
            logger.warning("Controller: Delete rejected - " + e.getMessage());

            redirectAttributes.addFlashAttribute(
                    "errorMessage", e.getMessage());

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: Database error while deleting employee", e);

            redirectAttributes.addFlashAttribute(
                    "errorMessage", getDatabaseErrorMessage(e));
        }

        return "redirect:/employees";
    }

    // AJAX save endpoint used by the Add Employee modal
    @ResponseBody
    @RequestMapping(value = "/employee/ajax-save", method = RequestMethod.POST)
    public String ajaxSaveEmployee(Employee employee) {
        logger.info("Controller: AJAX save employee");

        try {
            employeeService.save(employee);

            logger.info("Controller: AJAX save successful");
            return "OK";

        } catch (IllegalArgumentException e) {
            logger.warning("Controller: AJAX save rejected - "
                    + e.getMessage());

            return "ERROR: " + e.getMessage();

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: AJAX database error while saving", e);

            return "ERROR: " + getDatabaseErrorMessage(e);
        }
    }

    // AJAX delete endpoint used by the Delete confirmation modal
    @ResponseBody
    @RequestMapping(
            value = "/employee/ajax-delete/{id}",
            method = RequestMethod.POST)
    public String ajaxDeleteEmployee(@PathVariable("id") int id) {
        logger.info("Controller: AJAX delete employee ID " + id);

        try {
            employeeService.delete(id);

            logger.info("Controller: AJAX delete successful");
            return "OK";

        } catch (IllegalArgumentException e) {
            logger.warning("Controller: AJAX delete rejected - "
                    + e.getMessage());

            return "ERROR: " + e.getMessage();

        } catch (SQLException e) {
            logger.log(Level.SEVERE,
                    "Controller: AJAX database error while deleting", e);

            return "ERROR: " + getDatabaseErrorMessage(e);
        }
    }

    private String getDatabaseErrorMessage(SQLException e) {
        // Oracle ORA-00001: unique constraint violated (duplicate primary key)
        if (e.getErrorCode() == 1) {
            return "Employee ID already exists";
        }

        return "A database error occurred. Check the server logs.";
    }
}