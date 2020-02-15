package com.homework.wemakeprice.martin.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationCode {
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // [0000] success
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    SUCCESS_REQUEST("0000", HttpStatus.OK, "success request"),

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // [9000~9999] common ex
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    INTERNAL_ERROR("9000", HttpStatus.INTERNAL_SERVER_ERROR, "unexpected exception"),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ApplicationCode(String code, HttpStatus httpStatus, String defaultMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }
}
