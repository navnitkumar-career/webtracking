package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TimeInterval {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int timeId;

	private int time;

	private String currentDate;

	public TimeInterval() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeInterval(int timeId, int time, String currentDate) {
		super();
		this.timeId = timeId;
		this.time = time;
		this.currentDate = currentDate;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public int getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public String toString() {
		return "TimeInterval [timeId=" + timeId + ", time=" + time + ", currentDate=" + currentDate + "]";
	}

}
