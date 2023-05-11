package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.TimeInterval;

public interface AddTimeIntervalRepository extends JpaRepository<TimeInterval, Integer>{

	@Query(value = "select * from time_interval where current_date =?1",nativeQuery = true)
	public TimeInterval getTimeByDate(String date);
}
