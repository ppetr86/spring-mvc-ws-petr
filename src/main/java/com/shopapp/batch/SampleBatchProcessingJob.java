package com.shopapp.batch;

import com.shopapp.batch.listener.FirstJobListener;
import com.shopapp.batch.listener.FirstStepListener;
import com.shopapp.batch.processor.FirstItemProcessor;
import com.shopapp.batch.processor.SecondItemProcessor;
import com.shopapp.batch.reader.FirstItemReader;
import com.shopapp.batch.reader.SecondItemReader;
import com.shopapp.batch.writer.FirstItemWriter;
import com.shopapp.batch.writer.SecondItemWriter;
import com.shopapp.data.entitydto.CurrencyEntityDtoIn;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

@Service
@AllArgsConstructor
@Profile("dev")
public class SampleBatchProcessingJob {

    private SecondTasklet secondTasklet;
    private FirstJobListener firstJobListener;
    private FirstStepListener firstStepListener;

    private FirstItemReader firstItemReader;
    private FirstItemProcessor firstItemProcessor;
    private FirstItemWriter firstItemWriter;

    private SecondItemReader secondItemReader;
    private SecondItemProcessor secondItemProcessor;
    private SecondItemWriter secondItemWriter;

    @Bean
    private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstChunkStep", jobRepository)
                .<Integer, Long>chunk(3, transactionManager)
                .reader(firstItemReader) //item reader must be provided in chunk oriented step
                .processor(firstItemProcessor) // processor is not mandatory in chunk oriented step
                .writer(firstItemWriter) //item writer must be provided in chunk oriented steps
                .build();
    }

    //all jobs are run with
    @Bean
    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("firstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep(jobRepository, transactionManager))
                .next(secondStep(jobRepository, transactionManager))
                .listener(firstJobListener)
                .build();
    }

    @Bean
    private Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet(firstTask(), transactionManager).allowStartIfComplete(true)
                .listener(firstStepListener)
                .build();
    }

    private Tasklet firstTask() {
        return (contribution, chunkContext) -> {
            System.out.println("this is first tasklet");
            return RepeatStatus.FINISHED;
        };
    }

    public FlatFileItemReader<CurrencyEntityDtoIn> flatFileItemReader() {
        var reader = new FlatFileItemReader<CurrencyEntityDtoIn>();
        reader.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\inputFiles\\currencies.csv")));

        reader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("name", "symbol", "code");
                        setDelimiter(",");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(CurrencyEntityDtoIn.class);
                    }
                });

            }
        });

        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    private Step secondChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("secondChunkStep", jobRepository)
                .<CurrencyEntityDtoIn, CurrencyEntityDtoIn>chunk(1, transactionManager)
                .reader(flatFileItemReader())
                .writer(secondItemWriter)
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

    @Bean
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

    @Bean
    public Job thirdJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("thirdJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(secondChunkStep(jobRepository, transactionManager))
                .build();
    }
}
