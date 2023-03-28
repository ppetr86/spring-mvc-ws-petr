package com.shopapp.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class FirstJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before job " + jobExecution.getJobInstance().getJobName());
        System.out.println("Before job params " + jobExecution.getJobParameters());
        System.out.println("Job Exec context" + jobExecution.getExecutionContext());
        jobExecution.getExecutionContext().put("test key", "test value");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After job " + jobExecution.getJobInstance().getJobName());
        System.out.println("After job Job Exec context" + jobExecution.getExecutionContext());
    }
}
