package com.kjh.batchsamples.util;

import com.kjh.batchsamples.web.common.model.ApiResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class MessageUtil {

    /**
     * 응답값이 없는 성공 응답 처리
     * @return
     */
    public static ApiResult createSuccessResult() {
        Map<String, String> data = Map.of("createdAt", CommonUtil.getCurDay_yyyyMMddHHmmss()
        );

        return new ApiResult(data);
    }


    /**
     * 응답값이 존재하는 응답 처리
     * @param data
     * @return
     */
    public static ApiResult createSuccessResult(Map<String,Object> data) {
        data.put("createdAt", CommonUtil.getCurDay_yyyyMMddHHmmss());
        return new ApiResult(data);
    }
}
