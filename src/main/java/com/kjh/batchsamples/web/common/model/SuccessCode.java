package com.kjh.batchsamples.web.common.model;

/**
 * 에러코드
 */
public enum SuccessCode {
    S0000("S0000", "요청 처리 성공"),
    ;

    private String code;
    private String defaultMessage;

    SuccessCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
