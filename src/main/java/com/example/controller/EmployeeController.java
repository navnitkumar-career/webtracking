package com.example.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.AddMouseClickCount;
import com.example.dao.TaskDataRepository;
import com.example.dao.TaskLogRepository;
import com.example.dao.addEmployeeRepository;
import com.example.dao.addTaskRepository;
import com.example.entities.Employee;
import com.example.entities.MouseClick;
import com.example.entities.TaskData;
import com.example.entities.TaskLog;
import com.example.entities.TaskMaster;
import com.example.service.EmailService;
import com.example.service.StartTaskTracker;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	Random random = new Random(1000);
	@Autowired
	private addEmployeeRepository addEmployeeRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	StartTaskTracker startTaskTracker;

	@Autowired
	private addTaskRepository addTaskRepository;

	@Autowired
	private TaskLogRepository taskLogRepository;

	@Autowired
	private TaskDataRepository taskDataRepository;

	@Autowired
	private AddMouseClickCount addMouseClickCount;

	@RequestMapping("/")
	public String displayLoginPage(Model model) {
		model.addAttribute("title", "Login - Work Tracking System");
		return "employee/signin";
	}

	@RequestMapping("/jwt")
	public String displayJWTLoginPage(Model model) {
		model.addAttribute("title", "Login - Work Tracking System");
		return "JWT/signin";
	}

	@RequestMapping(value = "/send-otp", method = RequestMethod.POST)
	public String sendOTP(Model model, @RequestParam("employeeEmail") String email, HttpSession session) {
		model.addAttribute("title", "forgot - Contact Manager");
		Employee employee = addEmployeeRepository.findEmployeeByEmployeeEmail(email);
		if (employee == null) {
			session.setAttribute("message", "Employee  does not exits with this email !!");
			return "employee/signin";
		} else {
			int otp = random.nextInt(999999);
			String to = email;
			String subject = "OTP From WORK TRACKING";
			String message = "<div style='border:1px solid #e2e2e2;padding:20px'> <h1><b> OTP = " + otp
					+ "</b></h1></div>";
			boolean flag = emailService.sendEmail(to, subject, message);
			if (flag) {
				session.setAttribute("otp", otp);
				session.setAttribute("email", email);
				model.addAttribute("employeeEmail", email);
				return "employee/verify_otp";
			} else {
				session.setAttribute("message", "Check your email id !!");
				return "employee/signin";
			}
		}
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") Integer otp, @RequestParam("email") String email, HttpSession session,
			Model model) {
		Integer myOtp = (int) session.getAttribute("otp");
		Employee userByUserName = addEmployeeRepository.getUserByUserName(email);
		session.setAttribute("id", userByUserName.getEmployeeId());
		if (myOtp.equals(otp)) {
			System.out.println(userByUserName.getEmployeeRole());
			if (userByUserName.getEmployeeRole().equals("Project Manager")) {
				model.addAttribute("title", "Project Manager Dashboard - Work Tracking System");
				return "redirect:index";
			} else {
				model.addAttribute("title", "Dashboard 1");
				return "redirect:dashboard";
			}
		} else {
			session.setAttribute("message", "You have entered wrong otp!!");
			return "employee/signin";
		}

	}

	@RequestMapping("/index")
	public String dispalyProjectManagerDashboard(Model model, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		model.addAttribute("title", "Project Manager Dashboard - Work Tracking System");
		return "projectManager/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dispalyEmployeeDashboard(Model model, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		model.addAttribute("title", "Employee Dashboard - Work Tracking System");
		return "employee/dashboard";
	}

	@RequestMapping("/viewTask")
	public String showTaskList(Model model, Principal principal, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		List<TaskMaster> allTaskListByEmployeeId = this.addTaskRepository.getAllTaskListByEmployeeId(id);
		System.out.println(allTaskListByEmployeeId);
		model.addAttribute("title", "Show Task List");
		model.addAttribute("allTasks", allTaskListByEmployeeId);
		return "employee/showTaskList";
	}

	@RequestMapping("/startTaskTracker/{employeeId}/{taskId}")
	public String startTaskTracker(@PathVariable("employeeId") int employeeId, @PathVariable("taskId") int taskId,
			Model model, Principal principal, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		model.addAttribute("title", "Start Task Tracker");
		boolean flag = true;
		session.setAttribute("taskId", taskId);
		String status = startTaskTracker.StartTaskTracker(employeeId, flag);
		return "redirect:/employee/viewTask";
	}

	@RequestMapping("/stopTaskTracker/{employeeId}/{taskId}")
	public String stopTaskTracker(@PathVariable("employeeId") int employeeId, @PathVariable("taskId") int taskId,
			Model model, Principal principal, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		model.addAttribute("title", "Start Task Tracker");
		long seconds = startTaskTracker.StopTaskTracker(employeeId);
		Integer taskid = (int) session.getAttribute("taskId");
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskId(taskId);
		taskLog.setDuration(seconds);
		taskLog.setEmployeeId(employeeId);
		this.taskLogRepository.save(taskLog);
		return "redirect:/employee/viewTask";
	}

	@RequestMapping("/viewDuration")
	public String showDurationWork(Model model, Principal principal, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);
		List<TaskLog> groupedResult = this.taskLogRepository.getGroupedResult();
		model.addAttribute("groupedResult", groupedResult);

		/*
		 * for(TaskLog result : groupedResult) {
		 * System.out.println("hello "+result.getTaskMaster().g); }
		 */
		return "employee/showDurationLog";
	}

	/*
	 * @PostMapping("/storeCountKeyPresses")
	 * 
	 * @ResponseBody public String storeKeyPresses(Model model, Principal
	 * principal, @RequestParam("keyPresses") Integer keyPresses) { if (keyPresses
	 * == null || keyPresses <= 0) { return "error"; }
	 * System.out.println("key count : " + keyPresses);
	 * 
	 * return "success"; }
	 */

	@RequestMapping("/countKeyPress")
	public String countKeyPress(Model model, Principal principal, HttpSession session) {
		Integer id = (int) session.getAttribute("id");
		System.out.println(id);

		List<TaskMaster> allTaskListByEmployeeId = this.addTaskRepository.getAllTaskListByEmployeeId(id);
		System.out.println(allTaskListByEmployeeId);
		model.addAttribute("title", "Show Task List");
		model.addAttribute("allTasks", allTaskListByEmployeeId);
		return "employee/countKeyPresses";
	}

	@PostMapping("/saveTaskCountData")
	public ResponseEntity<String> saveTaskData(@RequestParam String taskId, @RequestBody Map<String, Object> data,
			HttpSession session) {
		int employeeid = (int) session.getAttribute("id");
		System.out.println(employeeid);
		LocalDateTime now = LocalDateTime.now();
		try {

			List<TaskData> taskDataList = new ArrayList<>();
			Map<String, Integer> keyCounts = (Map<String, Integer>) data.get("keyCounts");

			for (Map.Entry<String, Integer> entry : keyCounts.entrySet()) {
				TaskData taskData = new TaskData();
				taskData.setStartTime(now);
				taskData.setTaskId(taskId);
				taskData.setKeyCode(entry.getKey());
				taskData.setKeyCount(entry.getValue());
				taskData.setEmployeeId(employeeid);
				taskDataList.add(taskData);
			}

			MouseClick mouseClick = new MouseClick();
			mouseClick.setEmployeeId(employeeid);
			mouseClick.setStartTime(now);
			mouseClick.setTaskId(taskId);
			mouseClick.setClickCount((int) data.get("clickCount"));
			addMouseClickCount.save(mouseClick);
			taskDataRepository.saveAll(taskDataList);
			return ResponseEntity.ok("Task data saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving task data: " + e.getMessage());
		}
	}

	@PostMapping("/saveTaskCountClicksData")
	public ResponseEntity<String> saveTaskCountClicksData(@RequestParam String taskId,
			@RequestBody MouseClick clickData, HttpSession session) {
		int employeeid = (int) session.getAttribute("id");
		System.out.println(employeeid);
		try {
			LocalDateTime now = LocalDateTime.now();
			List<MouseClick> taskDataList = new ArrayList<>();

			MouseClick mouseClick = new MouseClick();
			mouseClick.setEmployeeId(employeeid);
			mouseClick.setTaskId(taskId);
			mouseClick.setStartTime(now);
			mouseClick.setClickCount(clickData.getClickCount());
			taskDataList.add(mouseClick);

			// Save the list of TaskData objects to the database
			addMouseClickCount.saveAll(taskDataList);
			return ResponseEntity.ok("Mouse Click data saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving task data: " + e.getMessage());
		}
	}
}
