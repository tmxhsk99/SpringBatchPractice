package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

/**
 * Job 이 시작전 혹은 Job이 종료된 이후 작업을 처리하는 Listener
 */
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
            int chunkTotalAge = stepExecution.getExecutionContext().getInt("chunkTotalAge", 0);
            totAge += chunkTotalAge;
        }

        Double readCountDouble = Double.valueOf(readCount);
        Double totAgeDouble = Double.valueOf(totAge);

        log.info("전체 처리한 데이터 수: {}", readCount);
        log.info("평균 나이 : {}", totAgeDouble / readCountDouble);
    }
}
