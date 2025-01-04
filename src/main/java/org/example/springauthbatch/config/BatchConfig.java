package org.example.springauthbatch.config;

import org.example.springauthbatch.task.SessionTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository                 jobRepository;
    private final PlatformTransactionManager    platformTransactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job sessionJob() {
        Job job = new JobBuilder("sessionJob", jobRepository).start(sessionStep()).build();

        return (job);
    }

    @Bean
    public Step sessionStep() {
        Step    step = new StepBuilder("sessionStep", jobRepository).tasklet(sessionTask(), platformTransactionManager).build();

        return (step);
    }

    @Bean
    public Tasklet  sessionTask() {
        Tasklet task = ((contribution, chunkContext) -> {
            System.out.println("Batch job is running...");
            return (null);
        }) ;

        return (task);
    }
}
