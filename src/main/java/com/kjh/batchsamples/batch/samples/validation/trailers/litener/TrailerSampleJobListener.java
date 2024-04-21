package com.kjh.batchsamples.batch.samples.validation.trailers.litener;

import com.kjh.batchsamples.batch.samples.validation.trailers.TrailerSampleJobConfig;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

@Component
public class TrailerSampleJobListener implements JobExecutionListener {
    /**
     * Job 실행 전 처리
     * @param jobExecution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    /**
     * Job 실행 후 처리
     * @param jobExecution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        stepExecutions.forEach(stepExecution -> {
            Integer itemsWritten = (Integer) stepExecution.getExecutionContext().get("itemsWritten");
            Long totalAmount = (Long) stepExecution.getExecutionContext().get("totalAmount");

            if(itemsWritten != null && totalAmount != null) {
                String fileName = TrailerSampleJobConfig.createTrxBatchFileName();
                // 생성된 파일의 마지막 레코드를 생성한다.
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                    writer.write("\nTrailer Record: Items Written = " + itemsWritten + ", Total Amount = " + totalAmount);
                } catch (IOException e) {
                    throw new RuntimeException("Trailer 파일 생성 중 오류 발생", e);
                }

            }

        });
    }
}
