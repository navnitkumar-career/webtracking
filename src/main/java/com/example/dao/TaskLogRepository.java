package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.TaskLog;

public interface TaskLogRepository extends JpaRepository<TaskLog, Integer> {

	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log GROUP BY task_id", nativeQuery = true)
	 List<TaskLog> getGroupedResult();
	 
	 
	 @Query(value = "SELECT task_log_id,date,SUM(duration) as 'duration' ,employee_id,task_id FROM task_log", nativeQuery = true)
	 TaskLog getGroupedAllTimeTrackedResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log GROUP BY employee_id", nativeQuery = true)
	 List<TaskLog> getGroupedEmployeeWiseResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where employee_id =?1 GROUP BY task_id ", nativeQuery = true)
	 List<TaskLog> getGroupedEmployeeTaskWiseResult(int employeeId);
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log  GROUP BY task_id", nativeQuery = true)
	 List<TaskLog> getGroupedTaskWiseResult();
	 
	 @Query(value = "SELECT task_log_id,date,SUM(duration) as 'duration' ,employee_id,task_id FROM task_log WHERE date = CURDATE()", nativeQuery = true)
	 TaskLog getGroupedTodaysTimeTrackedResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date = CURDATE() GROUP BY task_id ", nativeQuery = true)
	 List<TaskLog> getGroupedTaskWiseTodayResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date = CURDATE() GROUP BY employee_id", nativeQuery = true)
	 List<TaskLog> getGroupedEmployeeWiseTodayResult();
	 
	 @Query(value = "SELECT task_log_id,date,SUM(duration) as 'duration' ,employee_id,task_id FROM task_log WHERE date = CURDATE()-INTERVAL 1 DAY", nativeQuery = true)
	 TaskLog getGroupedYesterdayTimeTrackedResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date = CURDATE() - INTERVAL 1 DAY GROUP BY task_id ", nativeQuery = true)
	 List<TaskLog> getGroupedTaskWiseYesterdayResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date = CURDATE() - INTERVAL 1 DAY GROUP BY employee_id", nativeQuery = true)
	 List<TaskLog> getGroupedEmployeeWiseYesterdayResult();
	 
	 @Query(value = "SELECT task_log_id,date,SUM(duration) as 'duration' ,employee_id,task_id FROM task_log WHERE date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
	 TaskLog getGroupedLastSevenDaysTimeTrackedResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY task_id ", nativeQuery = true)
	 List<TaskLog> getGroupedTaskWiseLastSevenDaysResult();
	 
	 @Query(value = "SELECT  task_log_id,date,SUM(duration) as 'duration',employee_id,task_id FROM task_log where date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY employee_id", nativeQuery = true)
	 List<TaskLog> getGroupedEmployeeWiseLastSevenDaysResult();
	 
}
