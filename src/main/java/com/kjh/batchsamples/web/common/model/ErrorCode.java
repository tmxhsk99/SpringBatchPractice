package com.kjh.batchsamples.web.common.model;

/**
 * 에러코드
 */
public enum ErrorCode {
    E0001("E0001", "요청값이 올바르지 않습니다."),
    E0002("E0002", "배치 실행시 에러 발생."),
    ;

    private String code;
    private String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
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
