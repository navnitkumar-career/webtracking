package com.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "taskmaster")
public class TaskMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private int taskId;
	private String taskName;
	private String startDate;
	private int estimateHours;
	private String status;
	@Column(length = 200)
	private String description;
	private String documentationPDF;

	private String assign_status;

	private int employeeId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "project_id")
	private Project project;

	public TaskMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskMaster(int taskId, String taskName, String startDate, int estimateHours, String status,
			String description, String documentationPDF, String assign_status, int employeeId, Project project) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.startDate = startDate;

		this.estimateHours = estimateHours;
		this.status = status;
		this.description = description;
		this.documentationPDF = documentationPDF;
		this.assign_status = assign_status;
		this.employeeId = employeeId;
		this.project = project;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getEstimateHours() {
		return estimateHours;
	}

	public void setEstimateHours(int estimateHours) {
		this.estimateHours = estimateHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentationPDF() {
		return documentationPDF;
	}

	public void setDocumentationPDF(String documentationPDF) {
		this.documentationPDF = documentationPDF;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getAssign_status() {
		return assign_status;
	}

	public void setAssign_status(String assign_status) {
		this.assign_status = assign_status;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.taskId == ((TaskMaster) obj).getTaskId();
	}

}
