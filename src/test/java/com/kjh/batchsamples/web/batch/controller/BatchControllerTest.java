package com.kjh.batchsamples.web.batch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.batchsamples.util.CommonUtil;
import com.kjh.batchsamples.web.batch.model.BatchRunReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

/**
 * 배치 수동 실행 테스트 클래스
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class BatchControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("helloWorldJob 배치를 실행 한다.")
    void batchHandWriteRun() throws Exception {
        Map<String, String> jobParameters = getDefaultJobParameters();

        BatchRunReq testBatchReq = BatchRunReq.builder()
                .jobName("helloWorldJob")
                .jobParameters(jobParameters)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/batch/run")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testBatchReq)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("TrailerSampleJob 배치 수동 실행 한다.")
    void executeTrailerSampleJob() throws Exception {
        // 기존 경로의 생성된 데이터를 삭제 한다.
        String curDay = CommonUtil.getCurDay_yyyyMMdd();
        CommonUtil.deleteFile("D:/project/SpringBatchPractice/file/trailer/trailer_sample_"+curDay+".txt");

        // jobParameters 및 JobName 설정
        Map<String, String> jobParameters = getDefaultJobParameters();

        // 추가적인 jobPrameters 설정
        // 요청 mid
        jobParameters.put("mid", "tosspayments");
        // 조회 시작 거래일자
        jobParameters.put("startTrxDt", "20230102");
        // 조회 종료 거래일자
        jobParameters.put("endTrxDt", "20230102");

        BatchRunReq testBatchReq = BatchRunReq.builder()
                .jobName("trailerSampleJob")
                .jobParameters(jobParameters)
                .build();

        // 배치를 실행 한다.
        mockMvc.perform(MockMvcRequestBuilders.post("/batch/run")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testBatchReq)))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("csvItemCountJob 배치 수동 실행 한다.")
    void executeCsvItemCountJob() throws Exception {
        // jobParameters 및 JobName 설정
        Map<String, String> jobParameters = getDefaultJobParameters();

        BatchRunReq testBatchReq = BatchRunReq.builder()
                .jobName("csvItemCountJob")
                .jobParameters(jobParameters)
                .build();

        // 배치를 실행 한다.
        mockMvc.perform(MockMvcRequestBuilders.post("/batch/run")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testBatchReq)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 기본 JobParameters 설정
     * Batch 파라미터 설정을 위한 기본값을 설정한다.
     * @return
     */
    private Map<String, String> getDefaultJobParameters() {
        Map<String, String> jobParameters = new HashMap<>();
        jobParameters.put("runDt", CommonUtil.getCurDay_yyyyMMddHHmmss());
        return jobParameters;
    }

}