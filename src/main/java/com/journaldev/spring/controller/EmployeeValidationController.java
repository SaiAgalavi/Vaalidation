
package com.journaldev.spring.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.journaldev.spring.dao.EmployeeValidationDAO;
import com.journaldev.spring.model.EmployeeValidation;

@Controller
@RequestMapping("/employee/validation")
public class EmployeeValidationController {

    private final EmployeeValidationDAO dao =
            new EmployeeValidationDAO();

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute(
                "employeeValidation",
                new EmployeeValidation()
        );
        return "employeeValidation";
    }

    @PostMapping
    public String submitForm(
            @Valid @ModelAttribute("employeeValidation")
            EmployeeValidation employee,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "employeeValidation";
        }

        boolean saved = dao.save(employee);

        if (saved) {
            model.addAttribute(
                    "successMessage",
                    "Employee details saved successfully!"
            );
        } else {
            model.addAttribute(
                    "errorMessage",
                    "Unable to save employee details. Please check the server logs."
            );
        }

        return "employeeValidation";
    }
}
