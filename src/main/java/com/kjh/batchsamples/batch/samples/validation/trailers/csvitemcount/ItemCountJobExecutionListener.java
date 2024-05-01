package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class ItemCountJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long readCount = jobExecution.getStepExecutions().stream()
                .mapToLong(stepExecution -> stepExecution.getReadCount())
                .sum();

        int totAge = 0;

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            int age = stepExecution.getExecutionContext().getInt("age", 0);
            totAge += age;
        }


        log.info("Total read count: {}", readCount);
        log.info("Avg age : {}", totAge / readCount);
    }
}
