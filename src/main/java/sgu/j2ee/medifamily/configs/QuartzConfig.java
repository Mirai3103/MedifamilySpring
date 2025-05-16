package sgu.j2ee.medifamily.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.services.VaccinationJob;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
	private final DataSource dataSource;

	@Bean
	public JobDetail reminderJobDetail() {
		return JobBuilder.newJob(VaccinationJob.class)
				.withIdentity("vaccinationJob")
				.storeDurably()
				.build();
	}

	@Bean
	public Trigger reminderTrigger() {
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(7, 0);

		return TriggerBuilder.newTrigger()
				.forJob(reminderJobDetail())
				.withIdentity("vaccinationReminderTrigger")
				.withSchedule(scheduleBuilder)
				.build();
		// return TriggerBuilder.newTrigger()
		// .forJob(reminderJobDetail())
		// .withIdentity("vaccinationJobTrigger")
		// .startAt(Date.from(Instant.now().plusSeconds(10))) // Delay 10s
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()
		// .withRepeatCount(0)) // chạy duy nhất 1 lần
		// .build();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobDetail reminderJobDetail,
			Trigger reminderTrigger,
			AutowiringSpringBeanJobFactory jobFactory) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		Properties properties = new Properties();
		properties.setProperty("org.quartz.scheduler.instanceName", "MY_INSTANCE_NAME");
		properties.setProperty("org.quartz.scheduler.instanceId", "INSTANCE_ID_01");
		properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setQuartzProperties(properties);

		factory.setJobFactory(jobFactory);
		factory.setJobDetails(reminderJobDetail);
		factory.setTriggers(reminderTrigger);
		factory.setDataSource(dataSource);
		return factory;
	}

	@Bean
	public AutowiringSpringBeanJobFactory jobFactory() {
		return new AutowiringSpringBeanJobFactory();
	}
}
