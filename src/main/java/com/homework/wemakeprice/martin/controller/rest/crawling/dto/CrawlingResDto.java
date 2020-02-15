package com.homework.wemakeprice.martin.controller.rest.crawling.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CrawlingResDto {
    private String share;
    private String remainder;
}
