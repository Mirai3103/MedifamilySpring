package sgu.j2ee.medifamily.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
public class VaccinationJob implements Job {
	@Autowired
	private VaccinationService reminderService;
	@Autowired
	private MediaRecordService mediaRecordService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("VaccinationJob started");
		reminderService.sendReminders();
		mediaRecordService.sendFollowupReminders();
	}
}
