package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.MouseClick;

public interface AddMouseClickCount extends JpaRepository<MouseClick, Integer> {

	@Query(value = "SELECT id,employee_id,SUM(click_count) as 'click_count',start_time,task_id FROM mouse_click", nativeQuery = true)
	MouseClick getGroupedAllKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(click_count) as 'click_count',start_time,task_id FROM mouse_click WHERE DATE(start_time) = CURDATE()", nativeQuery = true)
	MouseClick getGroupedTodayKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(click_count) as 'click_count',start_time,task_id FROM mouse_click WHERE DATE(start_time) = CURDATE() - INTERVAL 1 DAY", nativeQuery = true)
	MouseClick getGroupedYesterdayKeyCountResult();
	
	@Query(value = "SELECT id,employee_id,SUM(click_count) as 'click_count',start_time,task_id FROM mouse_click WHERE DATE(start_time) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
	MouseClick getGroupedLastSevenDaysKeyCountResult();
}
