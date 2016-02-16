package com.library.essay.schedule.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.services.EssayService;

@Component
public class SpringScheduledTask {

	private static final Logger logger = Logger.getLogger(SpringScheduledTask.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private EssayService essayService;

	@Scheduled(cron = "0/15 * * * * ?")
	public void executeTask() {

		List<Essay> essays = essayService.getEssays();

		System.out.println("Spring scheduled task is being executed at " + dateFormat.format(new Date()));
		logger.info("Spring scheduled task is being executed at " + dateFormat.format(new Date()));

		System.out.println("There are " + essays.size() + " essays.");
	}
}
