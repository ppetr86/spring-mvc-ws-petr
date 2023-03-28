package com.shopapp.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class FirstStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("before step " + stepExecution.getStepName());
        System.out.println("before step " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("before step " + stepExecution.getJobExecution());
        stepExecution.getExecutionContext().put("stepExecution","stepExecution");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("after step " + stepExecution.getStepName());
        System.out.println("after step " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("after step " + stepExecution.getExecutionContext());
        return null;
    }
}
