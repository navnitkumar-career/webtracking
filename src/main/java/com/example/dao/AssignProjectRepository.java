package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.AssignProject;

public interface AssignProjectRepository extends JpaRepository<AssignProject, Integer>{

	@Query(value = "SELECT * FROM assign_project  JOIN project ON assign_project.project_id = project.project_id WHERE assign_project.employee_id =?1",nativeQuery = true)
	public List<AssignProject> findByProjectId(int projectId);
}
