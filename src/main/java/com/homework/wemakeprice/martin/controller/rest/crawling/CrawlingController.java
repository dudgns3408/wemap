package com.homework.wemakeprice.martin.controller.rest.crawling;

import com.homework.wemakeprice.martin.controller.rest.crawling.dto.CrawlingResDto;
import com.homework.wemakeprice.martin.enums.ExtractionType;
import com.homework.wemakeprice.martin.service.CrawlingService;
import com.homework.wemakeprice.martin.utils.HTMLExtractUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("/api/crawling")
@RequiredArgsConstructor
@Slf4j
public class CrawlingController {
    private final CrawlingService crawlingService;

    @GetMapping("url")
    public ResponseEntity<CrawlingResDto> crawlingUrl(
            @RequestParam @NotEmpty String destinationUrl
            , @RequestParam ExtractionType extractionType
            , @RequestParam @Min(1) int printBundleUnit)
    {
        String crawlingHtml = crawlingService.getHtml(destinationUrl);

        return ResponseEntity
                .ok()
                .body(crawlingService.extractionHtml(
                        extractionType == ExtractionType.EXCEPT_TAG
                                ? HTMLExtractUtils.removeTag(crawlingHtml)
                                : crawlingHtml
                        , printBundleUnit)
                );
    }
}
