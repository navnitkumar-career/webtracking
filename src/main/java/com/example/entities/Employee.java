package com.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true)
	private int employeeId;
	@NotBlank(message = "First name can not be null !!")
	@Size(min = 5, max = 30, message = "First name must be between 5 to 30 character.")
	private String firstName;
	@NotBlank(message = "Last name can not be null !!")
	@Size(min = 5, max = 30, message = "Last name must be between 5 to 30 character.")
	private String lastName;
	private String employeePassword;
	@Email(regexp = "/^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$/", message = "Invalid Email")
	private String employeeEmail;
	private String phone;

	private String employeeJoiningDate;
	@Column(length = 1000)
	private String address;

	private String employeeRole;

	private String status;

	public Employee(int employeeId, String firstName, String lastName, String employeePassword, String employeeEmail,
			String phone, String employeeJoiningDate, String address, String employeeRole, String status) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeePassword = employeePassword;
		this.employeeEmail = employeeEmail;
		this.phone = phone;
		this.employeeJoiningDate = employeeJoiningDate;
		this.address = address;
		this.employeeRole = employeeRole;
		this.status = status;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeeJoiningDate() {
		return employeeJoiningDate;
	}

	public void setEmployeeJoiningDate(String employeeJoiningDate) {
		this.employeeJoiningDate = employeeJoiningDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", employeePassword=" + employeePassword + ", employeeEmail=" + employeeEmail + ", phone=" + phone
				+ ", employeeJoiningDate=" + employeeJoiningDate + ", address=" + address + ", employeeRole="
				+ employeeRole + ", status=" + status + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.employeeId == ((Employee) obj).getEmployeeId();
	}

}
