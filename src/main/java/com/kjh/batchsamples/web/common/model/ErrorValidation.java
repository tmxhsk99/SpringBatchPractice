package com.kjh.batchsamples.web.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorValidation {
    /**
     * 에러 타입
     * - 에러에 대한 자세한 상황 설명
     * - 예) "required", "invalid"
     */
    private String type;
    /**
     * 메시지
     * - 에러에 대한 간략한 설명
     */
    private String message;
    /**
     * 자세한 내용
     * 하위 레벨에 추가할 수 있는 상세한 내용 (선택)  - 클라이언트에게 전달 하지 않는다.
     */
    private Map<String, Object> detail;

    @Builder
    public ErrorValidation(String type, String message, Map<String, Object> detail) {
        this.type = type;
        this.message = message;
        this.detail = detail;
    }
}
