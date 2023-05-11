const tasks = {}; // To store tracked tasks
let clickCount = 0;

const startTracking = (taskId) => {
	alert("start" + taskId);
	tasks[taskId] = {
		startTime: Date.now(),
		keyCounts: {},
		clickCount: 0
	};

	document.getElementById('startButton').disabled = true;
	document.getElementById('stopButton').disabled = false;



	document.addEventListener('keydown', function(event) {
		if (tasks[taskId]) { // Check if the task ID is being tracked
			if (event.key in tasks[taskId].keyCounts) {
				tasks[taskId].keyCounts[event.key]++;
			} else {
				tasks[taskId].keyCounts[event.key] = 1;
			}
		}
	});

	document.addEventListener('click', function(event) {
		if (tasks[taskId]) { // Check if the task ID is being tracked
			tasks[taskId].clickCount++;
			clickCount++;
		}
	});



};

const stopTracking = (taskId) => {
	alert("stop");
	document.getElementById('startButton').disabled = false;
	document.getElementById('stopButton').disabled = true;

	if (tasks[taskId]) { // Check if the task ID was being tracked
		const { startTime, keyCounts } = tasks[taskId];
		const duration = Date.now() - startTime;

		alert(`Task ID: ${taskId}`);
		alert(`Duration: ${duration}ms`);
		alert("Key counts:");
		Object.keys(keyCounts).forEach(function(key) {
			alert(key + " count: " + keyCounts[key])
		});

		alert(`Mouse click count for Task ID ${taskId}: ${clickCount}`);
		
		fetch('/employee/saveTaskCountData?taskId=' + taskId, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({keyCounts:keyCounts,clickCount:clickCount})
		})
			.then(response => response.text())
			.then(data => console.log(data))
			.catch(error => console.error(error));


		delete tasks[taskId];
	}
};

/*document.addEventListener("click", function() {
  if (isTaskStarted) {
	clickCount++;
  }
});

startButton.addEventListener("click", function() {
  taskStartTime = Date.now();
  console.log("Task started at " + taskStartTime);
  isTaskStarted = true;
});

endButton.addEventListener("click", function() {
  if(isTaskStarted) {
	taskEndTime = Date.now();
	console.log("Task ended at " + taskEndTime);
	alert("Total clicks during task: " + clickCount);
	isTaskStarted = false;
  }
});*/
