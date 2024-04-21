package com.kjh.batchsamples.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.batchsamples.util.DummyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 더미 API 호출 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class DummyApiTransService {

    private final ObjectMapper objectMapper;

    /**
     * toss 거래 대사 API 호출 대용 가짜 API 호출
     * @return
     * @throws JsonProcessingException
     */
    public List<Map<String,Object>> transTossTrxApi() throws JsonProcessingException {
        // 가짜 API를  통신하여 응답을 받아온다.
        List<Map<String,Object>> resultList = objectMapper.readValue(DummyUtil.DUMMY_TRX_RESULT, List.class);
        return resultList;
    }
}
