package com.shopapp.batch.controller;

import com.shopapp.batch.model.JobParamsRequestDtoIn;
import com.shopapp.batch.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Service
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public ResponseEntity<String> startJob(@PathVariable String jobName,
                                           @RequestBody Set<JobParamsRequestDtoIn> jobParamsRequest) {
        jobService.startJob(jobName, jobParamsRequest);
        return ResponseEntity.ok("Job Started");
    }

    @GetMapping("/stop/{jobExecutionId}")
    public ResponseEntity<String> stopJob(@PathVariable long jobExecutionId) {
        try {
            jobOperator.stop(jobExecutionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Job Stopped");
    }

    @GetMapping()
    public String test() {
        return "test";
    }
}
