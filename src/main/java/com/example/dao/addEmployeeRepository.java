package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entities.Employee;



public interface addEmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query("select employee from Employee employee where employee.employeeEmail = :employeeEmail ")
	public Employee getUserByUserName(@Param("employeeEmail") String email);
	@Query("select employee from Employee employee")
	public List<Employee> getAllEmployee();
	@Query(value = "select * from Employee",nativeQuery = true)
	public Page<Employee> getUser(Pageable  pageable);
	@Query(value = "select * from employee where employee_role='Project Manager' AND status = 'Not Assign'",nativeQuery = true)
	public List<Employee> getNotAssignProjectManager();
	
	public Employee findByEmployeeId(int employeeId);
	
	public Employee findEmployeeByEmployeeEmail(String employeeEmail);
	
	@Query(value = "select * from employee where employee_role='Employee'",nativeQuery = true)
	public List<Employee> getEmployee();
	
	//@Query("SELECT assignproject FROM AssignProject assignproject JOIN assignproject.Project project WHERE project.projectId = :projectId")
	
	
}
