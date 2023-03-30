package com.shopapp.batch;

import com.shopapp.batch.listener.FirstJobListener;
import com.shopapp.batch.listener.FirstStepListener;
import com.shopapp.batch.processor.Processors;
import com.shopapp.batch.reader.FirstItemReader;
import com.shopapp.batch.reader.Readers;
import com.shopapp.batch.writer.Writers;
import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import com.shopapp.data.entitydto.out.CurrencyEntityDtoOut;
import com.shopapp.data.entitydto.out.UserDtoOut;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@AllArgsConstructor
@Profile("dev")
@DependsOn()
public class SampleBatchProcessingJob {

    private FirstTaskLet firstTaskLet;
    private SecondTasklet secondTasklet;
    private FirstJobListener firstJobListener;
    private FirstStepListener firstStepListener;

    private Processors processors;

    private FirstItemReader firstItemReader;

    private Readers readers;
    private Writers writers;

    @Bean
    private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstChunkStep", jobRepository)
                .<Integer, Long>chunk(3, transactionManager)
                .reader(firstItemReader) //item reader must be provided in chunk oriented step
                .processor(processors.getIntegerLongItemProcessor()) // processor is not mandatory in chunk oriented step
                .writer(writers.longItemWriter()) //item writer must be provided in chunk oriented steps
                .build();
    }

    //all jobs are run with
    @Bean
    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("firstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                /*.start(firstStep(jobRepository, transactionManager))
                .next(secondStep(jobRepository, transactionManager))*/
                //.start(jdbcChunkStep(jobRepository, transactionManager))
                //.start(secondJdBcChunkStep(jobRepository,transactionManager))
                .start(secondJdBc2ChunkStep(jobRepository, transactionManager))
                //.start(restApiChunkStep(jobRepository, transactionManager))
                .listener(firstJobListener)
                .build();
    }

    @Bean
    private Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet(firstTaskLet, transactionManager).allowStartIfComplete(true)
                .listener(firstStepListener)
                .build();
    }

    private Step jdbcChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jdbcChunkStep", jobRepository)
                .<CurrencyEntity, CurrencyEntity>chunk(1, transactionManager)
                .reader(readers.jdbcCursorItemReader())
                .writer(writers.idBasedEntityItemWriter())
                .build();
    }

    private Step restApiChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("restApiChunkStep", jobRepository)
                .<UserDtoOut, UserDtoOut>chunk(1, transactionManager)
                .reader(readers.itemReaderAdapter())
                .writer(writers.abstractIdBasedDtoOutItemWriter())
                .build();
    }

    private Step secondChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("secondChunkStep", jobRepository)
                .<CurrencyEntityDtoIn, CurrencyEntityDtoOut>chunk(1, transactionManager)
                //.reader(readers.flatFileItemReader())
                //.reader(readers.jsonItemReader())
                .reader(readers.staxEventItemReader())
                .processor(processors.getCurrencyEntityDtoInCurrencyEntityDtoOutItemProcessor()) // processor is not mandatory in chunk oriented step
                //.reader(readers.jdbcCursorItemReader())
                //.writer(dtoInItemWriter)
                .writer(writers.flatFileItemWriter())
                //.writer(writers.jsonFileItemWriter())
                .build();
    }

    private Step secondJdBc2ChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("secondChunkStep", jobRepository)
                .<CurrencyEntityDtoIn, CurrencyEntity>chunk(1, transactionManager)
                .reader(readers.flatFileItemReader())
                .processor(processors.getCurrencyEntityDtoInCurrencyEntityItemProcessor()) // processor is not mandatory in chunk oriented step
                .writer(writers.jdbcBatchItemWriter())
                .build();
    }

    private Step secondJdBcChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("secondChunkStep", jobRepository)
                .<CurrencyEntity, CurrencyEntityDtoOut>chunk(1, transactionManager)
                .reader(readers.jdbcCursorItemReader())
                .processor(processors.getCurrencyEntityCurrencyEntityDtoOutProcessor()) // processor is not mandatory in chunk oriented step
                .writer(writers.flatFileItemWriter())
                .build();
    }

    @Bean
    public Job secondJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("secondJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep(jobRepository, transactionManager))
                .next(secondStep(jobRepository, transactionManager))
                .build();
    }

    private Step secondStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("secondStep", jobRepository)
                .tasklet(secondTasklet, transactionManager).allowStartIfComplete(true)
                .build();
    }

    private Tasklet secondTask() {
        return (contribution, chunkContext) -> {
            System.out.println("This is second tasklet step");
            return RepeatStatus.FINISHED;
        };
    }

}
