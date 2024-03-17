package com.kjh.batchsamples.web.common.controller;

import com.kjh.batchsamples.web.common.exception.BaseException;
import com.kjh.batchsamples.web.common.model.ErrorCode;
import com.kjh.batchsamples.web.common.model.ErrorResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResultResponse InvalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResultResponse response = ErrorResultResponse.builder()
                .errorCode(ErrorCode.E0001.getCode())
                .message("잘못된 요청 입니다.")
                .build();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            response.addExtra(fieldError.getField(),fieldError.getDefaultMessage());
        }
        log.error("InvalidRequestHandler : {}", response);
        return response;
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<ErrorResultResponse> BaseExceptionHandler(BaseException e) {
        int statusCode = e.getStatusCode();

        ErrorResultResponse body = new ErrorResultResponse(e);

        log.error(e.getLogMessage());
        log.error("RETURN Client LOG Body : {}", body);

        ResponseEntity<ErrorResultResponse> response = ResponseEntity.status(statusCode).body(body);

        return response;
    }

}
