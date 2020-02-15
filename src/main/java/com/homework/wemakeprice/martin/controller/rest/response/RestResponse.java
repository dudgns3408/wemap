package com.homework.wemakeprice.martin.controller.rest.response;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {
    private String code;
    private String message;
    private Object result;
}
