package com.example.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mouse_click")
public class MouseClick {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startTime;
	private String taskId;
	private int clickCount;
	private int employeeId;

	public MouseClick(Long id, LocalDateTime startTime, String taskId, int clickCount, int employeeId) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.taskId = taskId;
		this.clickCount = clickCount;
		this.employeeId = employeeId;
	}

	public MouseClick() {
		super();
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

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "MouseClick [id=" + id + ", startTime=" + startTime + ", taskId=" + taskId + ", clickCount=" + clickCount
				+ ", employeeId=" + employeeId + "]";
	}

	
}
