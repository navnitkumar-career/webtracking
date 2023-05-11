package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Project;

public interface addProjectRepository extends JpaRepository<Project, Integer>{

	@Query("select project from Project project")
	public List<Project> getAllProject();
	
	@Query(value = "select * from Project project where project.status = 'Not Assign' ",nativeQuery = true)
	public List<Project> getAllNotAssignProject();
	
	public Project findById(int projectId);
	
	
}
