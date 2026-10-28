package com.journaldev.spring.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
public class EmployeeValidation {

	
	@NotBlank(message = "Employee name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$",
             message = "Name must contain letters only")
    private String empName;
	
	@NotBlank(message = "City is Required")
	@Size(max = 50 , message = "City cannot exceed 50 characters")
	@Pattern(regexp = "^[A-Za-z]+$", message= "City must contain letters only")
    private String city;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
