package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.AddMouseClickCount;
import com.example.dao.AddTimeIntervalRepository;
import com.example.dao.AdminRepository;
import com.example.dao.AssignProjectRepository;
import com.example.dao.TaskDataRepository;
import com.example.dao.TaskLogRepository;
import com.example.dao.addEmployeeRepository;
import com.example.dao.addProjectRepository;
import com.example.dao.addTaskRepository;
import com.example.entities.Admin;
import com.example.entities.AssignProject;
import com.example.entities.Employee;
import com.example.entities.MouseClick;
import com.example.entities.Project;
import com.example.entities.TaskData;
import com.example.entities.TaskLog;
import com.example.entities.TaskMaster;
import com.example.entities.TimeInterval;
import com.example.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private addEmployeeRepository addEmployeeRepository;

	@Autowired
	private addProjectRepository addProjectRepository;

	@Autowired
	private AssignProjectRepository assignProjectRepository;

	@Autowired
	private addTaskRepository addTaskRepository;

	@Autowired
	private AddTimeIntervalRepository addTimeIntervalRepository;

	@Autowired
	private TaskLogRepository taskLogRepository;

	@Autowired
	private TaskDataRepository taskDataRepository;
	
	@Autowired
	private AddMouseClickCount addMouseClickCount;

	@RequestMapping("/")
	public String displayLoginPage(Model model) {
		model.addAttribute("title", "Login - Work Tracking System");
		return "signin";
	}

	@RequestMapping("/Login")
	public String customeLogin(Model model) {
		model.addAttribute("title", "Login - Work Tracking System");
		return "signin";
	}

	@RequestMapping("/index")
	public String displayDashboard(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Dashboard - Work Tracking System");
		
		TaskLog groupedTodaysTimeTrackedResult = this.taskLogRepository.getGroupedAllTimeTrackedResult();
		TaskData groupedAllKeyCountResult = taskDataRepository.getGroupedAllKeyCountResult();
		MouseClick groupedAllMouseClickCountResult = addMouseClickCount.getGroupedAllKeyCountResult();
		List<TaskLog> groupedResult = this.taskLogRepository.getGroupedTaskWiseResult();
		List<TaskLog> groupedEmployeeWiseResult = this.taskLogRepository.getGroupedEmployeeWiseResult();
		
		model.addAttribute("groupedEmployeeWiseResult", groupedEmployeeWiseResult);
		model.addAttribute("groupedResult", groupedResult);
		model.addAttribute("groupedTodaysTimeTrackedResult", groupedTodaysTimeTrackedResult);
		model.addAttribute("groupedAllKeyCountResult", groupedAllKeyCountResult);
		model.addAttribute("groupedAllMouseClickCountResult", groupedAllMouseClickCountResult);
		return "admin/dashboard";
	}

	@GetMapping("/profile")
	public String profilePage(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Profile Page");
		return "admin/profilePage";
	}

	@RequestMapping("/addEmployee")
	public String addEmployee(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Add Employee");
		return "admin/addEmployee";
	}

	@PostMapping("/addEmployeeProcess")
	public String addEmployeeProcess(@Valid @ModelAttribute Employee employee, Principal principal, HttpSession session,
			Model model, BindingResult result) {
		// System.out.println(contact);
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		try {
			if (result.hasErrors()) {
				System.out.println(result);
				return "admin/addEmployee";
			} else {
				String role = employee.getEmployeeRole();
				if (role.equalsIgnoreCase("Project Manager")) {
					employee.setStatus("Not Assign");
					this.addEmployeeRepository.save(employee);
				} else {
					this.addEmployeeRepository.save(employee);
				}
				session.setAttribute("message", new Message("Employee added Successfully", "success"));
			}
		} catch (Exception exception) {
			System.out.println("Exception is : " + exception.getMessage());
			exception.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/addEmployee";
	}

	@GetMapping("/showEmployee/{page}")
	public String showEmployee(@PathVariable("page") Integer page, Model model, Principal principal) {

		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Show Employee");

		List<Employee> allEmployee = this.addEmployeeRepository.getAllEmployee();
		Pageable pageable = PageRequest.of(page, 3);
		Page<Employee> user = this.addEmployeeRepository.getUser(pageable);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", user.getTotalPages());
		model.addAttribute("employee", user);

		return "admin/showEmployee";
	}

	@RequestMapping("/updateEmployeeForm/{employeeId}")
	public String showEmployeeDetails(@PathVariable("employeeId") Integer employeeId, Model model,
			Principal principal) {
		String userName = principal.getName();
		System.out.println("username is : " + userName);
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Show Employee Detail");

		Employee findByIdEmployee = this.addEmployeeRepository.findById(employeeId).get();
		model.addAttribute("employee", findByIdEmployee);
		return "admin/showEmployeeDetails";
	}

	@RequestMapping(value = "/employeeUpdateProcess", method = RequestMethod.POST)
	public String employeeUpdateProcess(@ModelAttribute Employee employee, Model model, HttpSession session,
			Principal principal) {
		try {
			Employee oldEmployeeDetails = this.addEmployeeRepository.findById(employee.getEmployeeId()).get();
			Employee empDetail = this.addEmployeeRepository.getUserByUserName(principal.getName());
			this.addEmployeeRepository.save(employee);
			session.setAttribute("message", new Message("your Employee upadated", "success"));

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return "redirect:/updateEmployeeForm/" + employee.getEmployeeId();
	}

	@GetMapping("/deleteEmployee/{employeeId}")
	public String deleteEmployee(@PathVariable("employeeId") Integer employeeId, Model model, HttpSession session,
			Principal principal) {
		Optional<Employee> contactOptional = this.addEmployeeRepository.findById(employeeId);
		Employee employee = contactOptional.get();
		this.addEmployeeRepository.delete(employee);
		System.out.println("DELETED");
		session.setAttribute("message", new Message("Employee Deleted successfully", "success"));
		return "redirect:/showEmployee/0";
	}

	@RequestMapping("/addProject")
	public String addProject(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Add Project");
		List<Project> allProject = this.addProjectRepository.getAllProject();

		model.addAttribute("project", allProject);
		return "admin/addProject";
	}

	@PostMapping(value = "/addProjectProcess")
	public String addProjectProcess(@ModelAttribute Project project, Principal principal, HttpSession session,
			Model model, BindingResult result, @RequestParam("projectDocument") MultipartFile file) {
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		try {
			if (result.hasErrors()) {
				System.out.println(result);
				System.out.println("error");
				return "admin/addEmployee";
			} else {
				if (file.isEmpty()) {
					System.out.println("File is Empty");
					project.setProjectfile("3.jpeg");
				} else {
					project.setProjectfile(file.getOriginalFilename());
					File UPLOAD_DIR = new ClassPathResource("static/").getFile();
					Path filePath = Paths
							.get(UPLOAD_DIR.getAbsolutePath() + File.separator + file.getOriginalFilename());
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("file upload successfully");
					project.setStatus("Not Assign");
					this.addProjectRepository.save(project);
					session.setAttribute("message", new Message("Project added Successfully", "success"));
				}
			}
		} catch (Exception exception) {
			System.out.println("Exception is : " + exception.getMessage());
			exception.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/addProject";
	}

	@GetMapping("/viewProject")
	public String viewProject(Model model, Principal principal) {

		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Show Projects");
		List<Project> allProject = this.addProjectRepository.getAllNotAssignProject();
		System.out.println("hello" + allProject);
		model.addAttribute("project", allProject);

		return "admin/showProject";
	}

	@RequestMapping("/assignProject/{projectId}")
	public String showAssignProjectForm(@PathVariable("projectId") Integer projectId, Model model,
			Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Add Project");
		Project project = this.addProjectRepository.findById(projectId).get();
		List<Employee> employees = this.addEmployeeRepository.getNotAssignProjectManager();
		model.addAttribute("assignProjectDetail", project);
		model.addAttribute("assignProjectManager", employees);
		System.out.println("Assign Project Details : " + project);

		return "admin/showAssignProjectForm";
	}

	@PostMapping("/assignProjectProcess")
	public String assignProjectProcess(@ModelAttribute AssignProject assignProject,
			@RequestParam("projectId") int projectId, HttpSession session, Model model, BindingResult result,
			Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Assign Project");

		System.out.println("**********" + assignProject);
		try {
			if (result.hasErrors()) {
				System.out.println(result);
				System.out.println("error");
				session.setAttribute("message", new Message("Something went wrong", "danger"));
				return "admin/showAssignProjectForm";
			} else {

				Project findById = addProjectRepository.findById(projectId);
				findById.setStatus("Assign");
				addProjectRepository.save(findById);

				Employee findByEmployeeId = addEmployeeRepository.findByEmployeeId(assignProject.getEmployeeId());
				findByEmployeeId.setStatus("Assign");
				addEmployeeRepository.save(findByEmployeeId);

				this.assignProjectRepository.save(assignProject);

				session.setAttribute("message", new Message("Project Assign Successfully", "success"));

				List<Project> allProject = this.addProjectRepository.getAllNotAssignProject();
				model.addAttribute("project", allProject);

			}
		} catch (Exception exception) {
			System.out.println("Exception is : " + exception.getMessage());
			exception.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/showProject";
	}

	@RequestMapping("/addTask")
	public String addTask(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		List<Project> allProject = this.addProjectRepository.getAllProject();

		model.addAttribute("allProjects", allProject);

		model.addAttribute("title", "Add Task");
		return "admin/addTask";
	}

	@PostMapping(value = "/addTaskProcess")
	public String addTaskProcess(@ModelAttribute TaskMaster taskMaster, @RequestParam("id") int projectId,
			Principal principal, HttpSession session, Model model, BindingResult result,
			@RequestParam("taskFile") MultipartFile file) {
		// System.out.println(contact);
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		Project project = this.addProjectRepository.findById(projectId);
		model.addAttribute("admin", userByAdminName);
		// System.out.println();
		try {
			if (result.hasErrors()) {
				System.out.println(result);
				System.out.println("error");
				return "admin/addTask";
			} else {
				if (file.isEmpty()) {
					System.out.println("File is Empty");
					taskMaster.setDocumentationPDF("3.jpeg");
				} else {
					taskMaster.setDocumentationPDF(file.getOriginalFilename());
					File UPLOAD_DIR = new ClassPathResource("static/images/").getFile();
					Path filePath = Paths
							.get(UPLOAD_DIR.getAbsolutePath() + File.separator + file.getOriginalFilename());
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("file upload successfully");
					taskMaster.setStatus("Pending");
					taskMaster.setAssign_status("Not Assign");

					project.getTasks().add(taskMaster);
					taskMaster.setProject(project);

					this.addTaskRepository.save(taskMaster);

					session.setAttribute("message", new Message("Task added Successfully", "success"));
				}
			}
		} catch (Exception exception) {
			System.out.println("Exception is : " + exception.getMessage());
			exception.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/addTask";
	}

	@RequestMapping("/showTask/{page}")
	public String showTask(@PathVariable("page") Integer page, Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Show Task");

		List<TaskMaster> allTaskList = this.addTaskRepository.getAllTaskList();

		Pageable pageable = PageRequest.of(page, 5);
		Page<TaskMaster> findContactsByUser = this.addTaskRepository.findAllTaskList(pageable);
		model.addAttribute("currentPage", page);
		model.addAttribute("allTask", findContactsByUser);
		model.addAttribute("totalPages", findContactsByUser.getTotalPages());

		return "admin/showTask";
	}

	@RequestMapping("/setTimeForm")
	public String addTimeInterval(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "set Time");
		return "admin/setTimeForm";
	}

	@PostMapping("/setTimeProcess")
	public String addTimeIntervalProcess(@ModelAttribute TimeInterval timeInterval, Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		this.addTimeIntervalRepository.save(timeInterval);
		return "admin/setTimeForm";
	}

	@RequestMapping("/showScreenshot")
	public String screenshotPage(Model model, Principal principal) throws IOException {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		File screenshotsDir = new File("E:\\\\TimeTracker\\\\2023\\");
		File[] idDirs = screenshotsDir.listFiles(File::isDirectory);
		model.addAttribute("idDirs", idDirs);
		return "admin/showScreenshotPage";

	}

	@RequestMapping("/taskWiseDurationTracked")
	public String taskWiseDurationTracked(Model model, Principal principal) throws IOException {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		List<TaskLog> groupedResult = this.taskLogRepository.getGroupedResult();
		model.addAttribute("groupedResult", groupedResult);
		return "admin/taskWiseDurationTrackedReport";

	}

	@RequestMapping("/employeeWiseDurationTracked")
	public String employeeWiseDurationTracked(Model model, Principal principal) throws IOException {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		List<TaskLog> groupedResult = this.taskLogRepository.getGroupedEmployeeWiseResult();
		model.addAttribute("groupedResult", groupedResult);
		return "admin/employeeWiseDurationTrackedReport";

	}

	@RequestMapping("/showTaskWiseDuration/{employee_Id}")
	public String showTaskWiseDuration(Model model, @PathVariable("employee_Id") int employeeId, Principal principal)
			throws IOException {
		System.out.println(employeeId);
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		List<TaskLog> groupedResult = this.taskLogRepository.getGroupedEmployeeTaskWiseResult(employeeId);
		System.out.println(groupedResult);
		model.addAttribute("groupedResult", groupedResult);
		return "admin/employeeTaskWiseDurationTrackedReport";

	}

	@RequestMapping("/taskDetails/taskId/{taskId}")
	public String showTaskDetails(Model model, @PathVariable("taskId") int taskId, Principal principal)
			throws IOException {
		System.out.println(taskId);
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);

		TaskMaster findById = addTaskRepository.findById(taskId);
		model.addAttribute("taskdetails", findById);
		return "admin/taskDetails";

	}

	@RequestMapping("/showTaskList/{page}")
	public String showTaskList(@PathVariable("page") Integer page, Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		model.addAttribute("title", "Show Task");

		Pageable pageable = PageRequest.of(page, 5);
		Page<TaskMaster> findContactsByUser = this.addTaskRepository.findAllTaskList(pageable);
		model.addAttribute("currentPage", page);
		model.addAttribute("allTask", findContactsByUser);
		model.addAttribute("totalPages", findContactsByUser.getTotalPages());

		return "admin/showTaskList";
	}

	@RequestMapping("/showCountKeyPresses/taskId/{taskId}")
	public String showCountKeyPresses(Model model, @PathVariable("taskId") int taskId, Principal principal)
			throws IOException {
		System.out.println(taskId);
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);

		
		List<TaskData> groupedTaskIdWiseCountKeyPresses = taskDataRepository.getGroupedCountKeyPresses(taskId);
		
		model.addAttribute("taskKeyCountdetails", groupedTaskIdWiseCountKeyPresses);
		return "admin/keyCountDetails";
	}
	
	@RequestMapping("/employeeDetails/employeeId/{employeeId}")
	public String showemployeeDetails(Model model, @PathVariable("employeeId") int employeeId, Principal principal)
			throws IOException {
		System.out.println(employeeId);
		String userName = principal.getName();
		Admin userByUserName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByUserName);
		Employee employee = addEmployeeRepository.findByEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "admin/showEmployeeDetails";
	}
	
	@RequestMapping("/todaysData")
	public String todayDataDashboard(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Dashboard");	
		System.out.println("Hello");
		TaskLog groupedTodaysTimeTrackedResult = this.taskLogRepository.getGroupedTodaysTimeTrackedResult();
		TaskData  groupedTodayKeyCountResult= taskDataRepository.getGroupedTodayKeyCountResult();
		MouseClick groupedTodayMouseClickCountResult = addMouseClickCount.getGroupedTodayKeyCountResult();
		List<TaskLog> groupedTaskWiseTodayResult = this.taskLogRepository.getGroupedTaskWiseTodayResult();
		List<TaskLog> groupedEmployeeWiseTodayResult = this.taskLogRepository.getGroupedEmployeeWiseTodayResult();
			
		model.addAttribute("groupedTodaysTimeTrackedResult", groupedTodaysTimeTrackedResult);
		model.addAttribute("groupedTodayKeyCountResult", groupedTodayKeyCountResult);
		model.addAttribute("groupedTodayMouseClickCountResult", groupedTodayMouseClickCountResult);
		model.addAttribute("groupedTaskWiseTodayResult", groupedTaskWiseTodayResult);		
		model.addAttribute("groupedEmployeeWiseTodayResult", groupedEmployeeWiseTodayResult);	
		return "admin/dashboardTodayData";
	}
	
	@RequestMapping("/yesterdayData")
	public String yesterdayDataDashboard(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Dashboard");	
		System.out.println("Hello");
		
		TaskLog getGroupedYesterdayTimeTrackedResult = this.taskLogRepository.getGroupedYesterdayTimeTrackedResult();
		TaskData  getGroupedYesterdayKeyCountResult= taskDataRepository.getGroupedYesterdayKeyCountResult();
		MouseClick getGroupedYesterdayMouseClickCountResult = addMouseClickCount.getGroupedYesterdayKeyCountResult();
		List<TaskLog> getGroupedTaskWiseYesterdayResult = this.taskLogRepository.getGroupedTaskWiseYesterdayResult();
		List<TaskLog> getGroupedEmployeeWiseYesterdayResult = this.taskLogRepository.getGroupedEmployeeWiseYesterdayResult();
		
		model.addAttribute("getGroupedYesterdayTimeTrackedResult", getGroupedYesterdayTimeTrackedResult);
		model.addAttribute("getGroupedYesterdayKeyCountResult", getGroupedYesterdayKeyCountResult);
		model.addAttribute("getGroupedYesterdayMouseClickCountResult", getGroupedYesterdayMouseClickCountResult);
		model.addAttribute("getGroupedTaskWiseYesterdayResult", getGroupedTaskWiseYesterdayResult);		
		model.addAttribute("getGroupedEmployeeWiseYesterdayResult", getGroupedEmployeeWiseYesterdayResult);	
		return "admin/dashboardYesterdayData";
	}
	
	@RequestMapping("/lastSevenDaysData")
	public String lastSevenDaysDashboard(Model model, Principal principal) {
		String userName = principal.getName();
		Admin userByAdminName = this.adminRepository.getUserByUserName(userName);
		model.addAttribute("admin", userByAdminName);
		model.addAttribute("title", "Dashboard");	
		System.out.println("Hello");
		
		TaskLog getGroupedLastSevenDaysTimeTrackedResult = this.taskLogRepository.getGroupedLastSevenDaysTimeTrackedResult();
		TaskData  getGroupedLastSevenDaysKeyCountResult= taskDataRepository.getGroupedLastSevenDaysKeyCountResult();
		MouseClick getGroupedLastSevenDaysMouseClickCountResult = addMouseClickCount.getGroupedLastSevenDaysKeyCountResult();
		List<TaskLog> getGroupedTaskWiseYesterdayResult = this.taskLogRepository.getGroupedTaskWiseLastSevenDaysResult();
		List<TaskLog> getGroupedEmployeeWiseLastSevenDaysResult = this.taskLogRepository.getGroupedEmployeeWiseLastSevenDaysResult();
		
		model.addAttribute("getGroupedLastSevenDaysTimeTrackedResult", getGroupedLastSevenDaysTimeTrackedResult);
		model.addAttribute("getGroupedLastSevenDaysKeyCountResult", getGroupedLastSevenDaysKeyCountResult);
		model.addAttribute("getGroupedLastSevenDaysMouseClickCountResult", getGroupedLastSevenDaysMouseClickCountResult);
		model.addAttribute("getGroupedTaskWiseYesterdayResult", getGroupedTaskWiseYesterdayResult);		
		model.addAttribute("getGroupedEmployeeWiseLastSevenDaysResult", getGroupedEmployeeWiseLastSevenDaysResult);	
		return "admin/lastSevenDaysDashboard";
	}
}
