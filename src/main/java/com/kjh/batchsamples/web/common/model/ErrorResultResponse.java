package com.kjh.batchsamples.web.common.model;

import com.kjh.batchsamples.web.common.exception.BaseException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ErrorResultResponse {
    private String errorCode;
    private String message;
    private Map<String,String> extras;

    @Builder
    public ErrorResultResponse(String errorCode, String message, Map<String,String> extras) {
        this.message = message;
        this.errorCode = errorCode;
        this.extras = extras;
    }

    /**
     * BaseException을 통한 에러 응답 생성
     * @param e
     */
    public ErrorResultResponse(BaseException e){
        this.errorCode = e.getErrorCode();
        this.message = e.getMessage();
        this.extras = e.validationsToExtras();
    }
    public void addExtra(String key, String value) {
        extras.put(key, value);
    }



}
