package com.library.essay.quartz.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzScheduledJob implements Job {

	private static final Logger logger = Logger.getLogger(QuartzScheduledJob.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		System.out.println("Quartz scheduled task is being executed at " + dateFormat.format(new Date()));
		logger.info("Quartz scheduled task is being executed at " + dateFormat.format(new Date()));
	}

}