package com.kjh.batchsamples.web.common.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import static com.kjh.batchsamples.web.common.model.ErrorCode.E0002;

/**
 * 실행하려는 Batch의 Job이 존재하지 않을 때 발생하는 예외
 */
public class BatchJoExecuteException extends BaseException{

    public BatchJoExecuteException() {
        super(E0002.getCode(),E0002.getDefaultMessage());
    }
    @Builder
    public BatchJoExecuteException(String message,StackTraceElement[] stackTrace) {
        super(E0002.getCode(),message);
        this.setStackTrace(stackTrace);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
