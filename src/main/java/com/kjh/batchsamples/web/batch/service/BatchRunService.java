package com.kjh.batchsamples.web.batch.service;

import com.kjh.batchsamples.web.batch.model.BatchRunReq;
import com.kjh.batchsamples.web.common.exception.BatchJoExecuteException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 배치를 실행시키기 위한 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class BatchRunService {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    /**
     * 배치를 수동으로 실행한다.
     *
     * @param batchRunReq
     */
    public void run(BatchRunReq batchRunReq) throws BatchJoExecuteException {

        // JObParameter 파싱 처리
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();
        Map<String, String> batchParameters = batchRunReq.getJobParameters();
        for (String key : batchParameters.keySet()) {
            parametersBuilder.addString(key, batchParameters.get(key));
        }
        try {
            Job job = jobRegistry.getJob(batchRunReq.getJobName());
            jobLauncher.run(job, parametersBuilder.toJobParameters());
        } catch (NoSuchJobException e) {
            throw createBatchExecuteException(e, batchRunReq, "수동 실행할 Job이 존재하지 않습니다");
        } catch (JobInstanceAlreadyCompleteException e) {
            throw createBatchExecuteException(e, batchRunReq, "동일한 파라미터로 이미 완료된 Job 인스턴스를 다시 실행할 수 없습니다");
        } catch (JobExecutionAlreadyRunningException e) {
            throw createBatchExecuteException(e, batchRunReq, "동일한 Job 인스턴스가 이미 실행 중 입니다");
        } catch (JobParametersInvalidException e) {
            throw createBatchExecuteException(e, batchRunReq, "제공된 JobParameters가 유효하지 않습니다");
        } catch (JobRestartException e) {
            throw createBatchExecuteException(e, batchRunReq, "Job 재시작 시 JobParameters가 유효하지 않습니다");
        }

        // JobLauncher를 통해 Job을 실행시킨다.
    }

    /**
     * BatchJoExecuteException 생성 편의 함수
     * @param e
     * @param batchRunReq
     * @param message
     * @return
     */
    private BatchJoExecuteException createBatchExecuteException(Exception e, BatchRunReq batchRunReq, String message) {
        return BatchJoExecuteException.builder()
                .message(
                        String.format(
                                "Execute JobName [%s] : %s | ErrorMessage %s",
                                batchRunReq.getJobName(),
                                message, e.getMessage())
                )
                .stackTrace(e.getStackTrace())
                .build();
    }
}
