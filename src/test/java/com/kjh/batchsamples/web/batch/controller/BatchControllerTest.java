package com.kjh.batchsamples.web.batch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.batchsamples.util.CommonUtil;
import com.kjh.batchsamples.web.batch.model.BatchRunReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 배치 수동실행 테스트 클래스
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
    @DisplayName("배치 수동 실행 테스트")
    void batchHandWriteRun() throws Exception {
        Map<String, String> jobParameters = new HashMap<>();
        jobParameters.put("RunDT", CommonUtil.getCurDay_yyyyMMddHHmmss());

        BatchRunReq testBatchReq = BatchRunReq.builder()
                .jobName("helloWorldJob")
                .jobParameters(jobParameters)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/batch/run")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testBatchReq)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }



}