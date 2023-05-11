package com.example.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskLog")
public class TaskLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "taskLogId")
	private int taskLogId;
	private int taskId;
	private int employeeId;
	private long duration;
	private LocalDate date = LocalDate.now();
	

	public TaskLog(int taskLogId, int taskId, int employeeId, long duration, LocalDate date) {
		super();
		this.taskLogId = taskLogId;
		this.taskId = taskId;
		this.employeeId = employeeId;
		this.duration = duration;
		this.date = date;
	}

	public TaskLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(int taskLogId) {
		this.taskLogId = taskLogId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
