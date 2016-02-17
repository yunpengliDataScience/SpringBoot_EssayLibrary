package com.library.essay.quartz.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.library.essay.quartz.jobs.QuartzScheduledJob;

public class MyQuartzListener extends QuartzInitializerListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		ServletContext ctx = sce.getServletContext();
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx
				.getAttribute(QUARTZ_FACTORY_KEY);
		try {
			Scheduler scheduler = factory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(QuartzScheduledJob.class).build();
			// Fires every 5 secend. For generated expressions, go to
			// http://www.cronmaker.com
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("simple")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
					.startNow().build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (Exception e) {
			ctx.log("There was an error scheduling the job.", e);
		}
	}

}