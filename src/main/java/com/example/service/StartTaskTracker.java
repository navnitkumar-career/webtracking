package com.example.service;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.AddTimeIntervalRepository;
import com.example.entities.TimeInterval;

@Service
public class StartTaskTracker {

	private Timer captureTimer;
	private Timer cancelTimer;
	private LocalDateTime startTime;

	public long second;
	
	@Autowired
	private AddTimeIntervalRepository addTimeIntervalRepository;
	
	public long fetchTime()
	{
		DateTimeFormatter currentDateFormate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime getDate = LocalDateTime.now();
		String Date = currentDateFormate.format(getDate);
		TimeInterval timeByDate = addTimeIntervalRepository.getTimeByDate(Date);
		int duration = timeByDate.getTime();
		
		second = duration * 60000L;
		return second;
	}
	public String  StartTaskTracker(int employeId, Boolean flag) {

		second = fetchTime();
		System.out.println(second);
		if (captureTimer != null) {
			System.out.println("Already capturing...");
			return "Already capturing";
		}
		captureTimer = new Timer();
		DateTimeFormatter yearFormate = DateTimeFormatter.ofPattern("YYYY");
		LocalDateTime getYear = LocalDateTime.now();
		String year = yearFormate.format(getYear);

		DateTimeFormatter currentDateFormate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime getDate = LocalDateTime.now();
		String currentDate = currentDateFormate.format(getDate);

		startTime = LocalDateTime.now();
		captureTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Running: " + new java.util.Date());

				try {
					Robot r = new Robot();
					String fileDirectory = "E://TimeTracker//" + year + "//" + employeId + "//" + currentDate;
					
					File directory = new File(fileDirectory);
					if (!directory.exists()) {
						directory.mkdirs();
					}
					String screenshotName = String.valueOf(new java.util.Date()).replace(":", "-");
					String path = screenshotName + ".jpg";
					File file = new File(directory, path);
					Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					BufferedImage Image = r.createScreenCapture(capture);
					ImageIO.write(Image, "jpg", file);

					System.out.println("file Path " + file.getAbsolutePath());
					System.out.println("Screenshot saved");

				} catch (AWTException e) {

					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}, 0, second);

		cancelTimer = new Timer();
		cancelTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (captureTimer != null) {
					captureTimer.cancel();
					captureTimer = null;
					System.out.println("Capture stopped.");
				}
			}
		}, 21600000);
		
		return "capturing_started";
	}

	public long StopTaskTracker(int employeId) {

		if (captureTimer == null) {
			System.out.println("Not capturing...");
			return 0;
		}
		captureTimer.cancel();
		captureTimer = null;
		if (cancelTimer != null) {
			cancelTimer.cancel();
			cancelTimer = null;
		}
		System.out.println("Capture stopped.");
		LocalDateTime stopTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, stopTime);
        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        startTime = null;
        System.out.println(duration); 
        //long ConvertTimeToseconds = minutes*60000L;
        //long ConvertHourToseconds = hours*3600000L;
        //long totalSecond = ConvertTimeToseconds+ConvertHourToseconds+seconds;
        System.out.println(minutes + " / " + hours + " / " + seconds);
        return  seconds;

	}

}
