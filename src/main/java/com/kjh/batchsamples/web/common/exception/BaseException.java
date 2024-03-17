package com.kjh.batchsamples.web.common.exception;

import com.kjh.batchsamples.web.common.model.ErrorValidation;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 예외 클래스 상속 위한 추상 클래스
 * {
 * "errorCode": "E0001",
 * "message": "요청값이 올바르지 않습니다.",
 * "validations": {
 * "field": "name",
 * "message": "값을 입력해 주세요",
 * }
 * }
 */

@Getter
public abstract class BaseException extends RuntimeException {
    private final String errorCode;

    private Map<String, ErrorValidation> validations;

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public void addValidations(String field, ErrorValidation detail) {
        this.validations.put(field, detail);
    }

    public void addValidations(String field, String message) {

    }
    public String getStackTraceMsg() {
        StackTraceElement[] stackTrace = this.getStackTrace();
        StringBuffer sb = new StringBuffer();

        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 해당 에러에 자세한 정보는 제거한 형태인 Map 형태로 반환 한다.
     * @return
     */
    public Map<String,String> validationsToExtras() {
        Map<String, String> extraMap = new HashMap<>();
        for (String key : validations.keySet()) {
            ErrorValidation errorValidation = validations.get(key);
            extraMap.put(errorValidation.getType(), errorValidation.getMessage());
        }
        return extraMap;
    }

    public abstract int getStatusCode();


    public String getLogMessage() {
        return String.format("errorCode=%s, message=%s, stackTrace=%s", errorCode, getMessage(), getStackTraceMsg());

    }
}
