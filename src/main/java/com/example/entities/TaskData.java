package com.example.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_data")
public class TaskData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startTime;
	private String taskId;
	private String keyCode;
	private Integer keyCount;
	private int employeeId;

	public TaskData() {
		super();
	}

	public TaskData(Long id, LocalDateTime startTime, String taskId, String keyCode, Integer keyCount, int employeeId) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.taskId = taskId;
		this.keyCode = keyCode;
		this.keyCount = keyCount;
		this.employeeId = employeeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public Integer getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(Integer keyCount) {
		this.keyCount = keyCount;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	
	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public String toString() {
		return "TaskData [id=" + id + ", startTime=" + startTime + ", taskId=" + taskId + ", key=" + keyCode + ", keyCount="
				+ keyCount + ", employeeId=" + employeeId + "]";
	}

}
