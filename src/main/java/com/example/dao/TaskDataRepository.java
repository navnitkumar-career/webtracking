package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.TaskData;

public interface TaskDataRepository extends JpaRepository<TaskData, Integer> {

	@Query(value = "SELECT id,employee_id,start_time,task_id,key_code,SUM(key_count) as 'key_count' FROM task_data where task_id =?1 GROUP BY key_code", nativeQuery = true)
	List<TaskData> getGroupedCountKeyPresses(int taskId);
	
	@Query(value = "SELECT id,employee_id,SUM(key_count) as 'key_count',key_code,start_time,task_id FROM task_data", nativeQuery = true)
	TaskData getGroupedAllKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(key_count) as 'key_count',key_code,start_time,task_id FROM task_data WHERE DATE(start_time) = CURDATE()", nativeQuery = true)
	TaskData getGroupedTodayKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(key_count) as 'key_count',key_code,start_time,task_id FROM task_data WHERE DATE(start_time) = CURDATE() - INTERVAL 1 DAY", nativeQuery = true)
	TaskData getGroupedYesterdayKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(key_count) as 'key_count',key_code,start_time,task_id FROM task_data WHERE DATE(start_time) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
	TaskData getGroupedLastSevenDaysKeyCountResult();
}
