package com.kjh.batchsamples.util;

import com.kjh.batchsamples.web.common.model.ApiResult;

import java.util.Map;

public class MessageUtil {

    /**
     * 응답값이 없는 성공 응답 처리
     *
     * @return
     */
    public static ApiResult createSuccessResult() {
        Map<String, String> data = Map.of("createdAt", CommonUtil.getCurDay_yyyyMMddHHmmss()
        );

        return new ApiResult(data);
    }


    /**
     * 응답값이 존재하는 응답 처리
     *
     * @param data
     * @return
     */
    public static ApiResult createSuccessResult(Map<String, Object> data) {
        data.put("createdAt", CommonUtil.getCurDay_yyyyMMddHHmmss());
        return new ApiResult(data);
    }

    /**
     * Exception을 Error 포맷으로 바꾼다.
     *
     * @param message
     * @return
     */
    public static String parseErrorMsgFormat(Exception e) {
        return String.format("ErrorMessage : %s \n ErrorStackTrace : %s", e.getMessage(), getStackTraceToString(e));
    }

    private static String getStackTraceToString(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        // stackTrace를 문자열로 변환
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
