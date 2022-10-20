package com.tuesdayrefactoring.model;

public class Employee {

	private String employeeId;
	private String employeeName;
	private String employeeAddress;
	private String employeeFaculty;
	private String employeeDepartment;
	private String employeeDesignation;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeID) {
		employeeId = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String fullName) {
		employeeName = fullName;
	}

	public String getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(String address) {
		employeeAddress = address;
	}

	public String getEmployeeFaculty() {
		return employeeFaculty;
	}

	public void setEmployeeFaculty(String facultyName) {
		employeeFaculty = facultyName;
	}

	public String getEmployeeDepartment() {
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String department) {
		employeeDepartment = department;
	}

	public String getEmployeeDesignation() {
		return employeeDesignation;
	}

	public void setEmployeeDesignation(String designation) {
		employeeDesignation = designation;
	}

	@Override
	public String toString() {
		
		return "Employee ID = " + employeeId + "\n" + "FullName = " + employeeName + "\n" + "Address = " + employeeAddress + "\n"
				+ "Faculty Name = " + employeeFaculty + "\n" + "Department = " + employeeDepartment + "\n" + "Designation = "
				+ employeeDesignation;
	}
}
