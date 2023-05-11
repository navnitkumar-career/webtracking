package com.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AssignProject")
public class AssignProject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true)
	private int assignProjectId;
	private String projectName;
	private String projectStartDate;
	private String projectEndDate;
	private int employeeId;
	private String projectDescription;
	
	private String projectId;

	public AssignProject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignProject(int assignProjectId, String projectName, String projectStartDate, String projectEndDate,
			int employeeId, String projectDescription, String projectId) {
		super();
		this.assignProjectId = assignProjectId;
		this.projectName = projectName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.employeeId = employeeId;
		this.projectDescription = projectDescription;
		this.projectId = projectId;
	}



	public int getAssignProjectId() {
		return assignProjectId;
	}

	public void setAssignProjectId(int assignProjectId) {
		this.assignProjectId = assignProjectId;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "AssignProject [assignProjectId=" + assignProjectId + ", projectName=" + projectName
				+ ", projectStartDate=" + projectStartDate + ", projectEndDate=" + projectEndDate + ", employeeId="
				+ employeeId + ", projectDescription=" + projectDescription + ", projectId=" + projectId + "]";
	}}
