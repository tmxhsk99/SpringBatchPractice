package com.kjh.batchsamples.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;

    private final Job helloWorldJob;

    // 매 분마다 실행
    @Scheduled(cron = "0 * * * * ?")
    public void helloWorldJobExecute() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        jobParametersBuilder.addString("JobStartTimeId", String.valueOf(System.currentTimeMillis()));

        jobLauncher.run(helloWorldJob, jobParametersBuilder.toJobParameters());
    }
}
