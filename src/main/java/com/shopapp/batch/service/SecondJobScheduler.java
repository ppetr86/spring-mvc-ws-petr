package com.shopapp.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecondJobScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("firstJob")//to resolve the bean name, because return type is the same on both first and second
    private final Job firstJob;

    @Qualifier("secondJob")
    private final Job secondJob;

    public SecondJobScheduler(final JobLauncher jobLauncher, final Job firstJob, final Job secondJob) {
        this.jobLauncher = jobLauncher;
        this.firstJob = firstJob;
        this.secondJob = secondJob;
    }

    //http://www.cronmaker.com - each one minute, do not include the last asterisk
    @Scheduled(cron = "0 0/30 * 1/1 * ?")
    public void secondJobStarter() {
        Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("currentTime", new JobParameter<>(System.currentTimeMillis(), Long.class));

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
            System.out.println("jobExecution ID = " + jobExecution.getId());
        } catch (final Exception e) {
            System.out.println("Exception while starting job");
        }
    }
}
