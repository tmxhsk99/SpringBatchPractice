package com.kjh.batchsamples.web.batch.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 배치 수동 실행 요청 모델
 */
@Getter
@Setter
@ToString
public class BatchRunReq {
    private String jobName;
    private Map<String,String> jobParameters;

    @Builder
    public BatchRunReq(String jobName, Map<String, String> jobParameters) {
        this.jobName = jobName;
        this.jobParameters = jobParameters;
    }
}
