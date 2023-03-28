package com.shopapp.batch.service;

import com.shopapp.batch.model.JobParamsRequestDtoIn;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class JobService {
    private final JobLauncher jobLauncher;

    @Qualifier("firstJob")//to resolve the bean name, because return type is the same on both first and second
    private final Job firstJob;

    @Qualifier("secondJob")
    private final Job secondJob;

    public JobService(final JobLauncher jobLauncher, final Job firstJob, final Job secondJob) {
        this.jobLauncher = jobLauncher;
        this.firstJob = firstJob;
        this.secondJob = secondJob;
    }

    @Async
    public void startJob(String jobName, Set<JobParamsRequestDtoIn> jobParamsRequest) {
        Map<String, JobParameter<?>> params = new HashMap<>();
        jobParamsRequest.forEach(each ->
                params.put(each.getParamKey(), new JobParameter<>(each.getParamValue(), String.class)));

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = null;
            if ("firstJob".equals(jobName)) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else if ("secondJob".equals(jobName)) {
                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }
            assert jobExecution != null;
            System.out.println("jobExecution ID = " + jobExecution.getId());
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Exception while starting job");
        }
    }
}
