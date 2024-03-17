package com.kjh.batchsamples.web.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResult<T> {
    private String code;
    private T data;

    public ApiResult(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResult(T data) {
        this.code = SuccessCode.S0000.getCode();
        this.data = data;
    }
}
