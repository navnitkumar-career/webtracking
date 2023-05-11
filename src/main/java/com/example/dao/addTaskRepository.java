package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.TaskMaster;

public interface addTaskRepository extends JpaRepository<TaskMaster, Integer> {

	@Query(value = "select * from  taskmaster",nativeQuery = true)
	public List<TaskMaster> getAllTaskList();
	
	@Query("from TaskMaster as taskMaster")
	public Page<TaskMaster> findAllTaskList(Pageable  pageable);
	
	@Query(value = "select * from  taskmaster where project_id =?1",nativeQuery = true)
	public List<TaskMaster> getAllTaskListByProjectId(int projectId);
	
	public TaskMaster findById(int taskId);
	
	@Query(value = "select * from  taskmaster where employee_id =?1",nativeQuery = true)
	public List<TaskMaster> getAllTaskListByEmployeeId(int employeeId);
	
	
//	@Query(value = "UPDATE taskmaster SET assign_status = 'Assign', employee_id = :employeeId WHERE task_id = :taskId", nativeQuery = true)
//	void assignTask(@Param("employeeId") int employeeId,@Param("taskId") int taskId);
//	
//	@Query(value = "insert into taskmaster SET assign_status = 'Assign', employee_id = :employeeId WHERE task_id = :taskId", nativeQuery = true)
//	void assignTask(@Param("employeeId") int employeeId,@Param("taskId") int taskId);
	
}
