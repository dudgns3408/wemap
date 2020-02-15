package com.homework.wemakeprice.martin.exception;

import com.homework.wemakeprice.martin.controller.rest.response.RestResponse;
import com.homework.wemakeprice.martin.enums.ApplicationCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handler(Exception e) {
        ApplicationCode code = ApplicationCode.INTERNAL_ERROR;
        log.error("unexpected exception : {}", ExceptionUtils.getRootCauseMessage(e));
        return getResponseEntity(code, ExceptionUtils.getRootCauseMessage(e));
    }

    private ResponseEntity getResponseEntity(ApplicationCode code, String message) {
        return new ResponseEntity<>(
                RestResponse.builder()
                        .code(code.getCode())
                        .message(message)
                        .build()
                , code.getHttpStatus());
    }
}
