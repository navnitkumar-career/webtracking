package com.example.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.AssignProjectRepository;
import com.example.dao.addEmployeeRepository;
import com.example.dao.addTaskRepository;
import com.example.entities.AssignProject;
import com.example.entities.Employee;
import com.example.entities.TaskMaster;

@Controller
@RequestMapping("/projectManager")
public class ProjectManagerController {

	@Autowired
	private addEmployeeRepository addEmployeeRepository;
	
	@Autowired
	private AssignProjectRepository assignProjectRepository;
	
	@Autowired
	private addTaskRepository addTaskRepository;

	@RequestMapping("/index")
	public String dispalyProjectManagerDashboard(Model model, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		model.addAttribute("title", "Project Manager Dashboard - Work Tracking System");
		return "projectManager/dashboard";
	}
	
	@GetMapping("/viewProject")
	public String viewProject(Model model, HttpSession session) {

		Integer id = (int) session.getAttribute("id");
		System.out.println("Brijesh" + id);
		List<AssignProject>  allProject = this.assignProjectRepository.findByProjectId(id);
		model.addAttribute("title", "Show Projects");
		model.addAttribute("allProject", allProject);
		return "projectmanager/showProject";
	}
	
	@RequestMapping("/showTaskForm/{projectId}")
	public String showTaskList(@PathVariable("projectId") Integer projectId, Model model,
			Principal principal) {
		
		List<TaskMaster> allTaskListByProjectId = this.addTaskRepository.getAllTaskListByProjectId(projectId);
		System.out.println(allTaskListByProjectId);
		model.addAttribute("title", "Show Task List");
		model.addAttribute("allTasks", allTaskListByProjectId);
		return "projectmanager/showTaskList";
	}

	@RequestMapping("/assignTaskForm/{taskId}")
	public String showAssignTaskForm(@PathVariable("taskId") Integer taskId, Model model,
			Principal principal) {
		model.addAttribute("title", "Assign Task");
		
		List<Employee> allEmployee = this.addEmployeeRepository.getEmployee();
		
		TaskMaster taskMaster = this.addTaskRepository.findById(taskId).get();
		model.addAttribute("task", taskMaster);
		model.addAttribute("allEmployee", allEmployee);

		return "projectmanager/showAssignTaskForm";
	}
	
	@PostMapping("/assignTaskProcess")
	public String assignTaskrocess(@ModelAttribute TaskMaster taskMaster,@RequestParam("taskId") int taskId,@RequestParam("employeeId") int employeeId,Model model) {
		System.out.println("employee id : " + employeeId);
		System.out.println("Task id : " + taskId);
		model.addAttribute("title", "Assign Task");
		
		TaskMaster findById = this.addTaskRepository.findById(taskId);
		findById.setAssign_status("Assign");
		findById.setEmployeeId(employeeId);
		
		TaskMaster updateTaskStatus = addTaskRepository.save(findById);
		System.out.println("assign " + updateTaskStatus);
		
		return "projectmanager/showTaskList";
	}

}
