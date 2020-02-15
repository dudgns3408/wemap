package com.homework.wemakeprice.martin.advice;

import com.homework.wemakeprice.martin.controller.rest.response.RestResponse;
import com.homework.wemakeprice.martin.enums.ApplicationCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o
            , MethodParameter methodParameter
            , MediaType mediaType
            , Class<? extends HttpMessageConverter<?>> aClass
            , ServerHttpRequest serverHttpRequest
            , ServerHttpResponse serverHttpResponse) {

        ApplicationCode code = ApplicationCode.SUCCESS_REQUEST;

        serverHttpResponse.setStatusCode(code.getHttpStatus());

        return new RestResponse(code.getCode(), code.getDefaultMessage(), o);
    }
}
