package com.shopapp.batch.controller;

import com.shopapp.batch.model.JobParamsRequestDtoIn;
import com.shopapp.batch.service.JobService;
import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import com.shopapp.data.entitydto.out.CurrencyEntityDtoOut;
import com.shopapp.service.CurrencyDao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobOperator jobOperator;
    private final CurrencyDao currencyDao;

    @PostMapping("/currencies")
    public ResponseEntity<List<CurrencyEntityDtoOut>> createCurrency(@RequestBody @Valid List<CurrencyEntityDtoIn> request) {
        var entities = request.stream().map(CurrencyEntity::new).collect(Collectors.toSet());
        var persisted = currencyDao.saveAll(entities);
        var dtosOut = persisted.stream().map(CurrencyEntityDtoOut::new).toList();
        return new ResponseEntity<>(dtosOut, HttpStatusCode.valueOf(201));
    }

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
