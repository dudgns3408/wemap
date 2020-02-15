package com.homework.wemakeprice.martin.service;

import com.homework.wemakeprice.martin.controller.rest.crawling.dto.CrawlingResDto;
import com.homework.wemakeprice.martin.utils.HTMLExtractUtils;
import com.homework.wemakeprice.martin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class CrawlingService {
    private final String HTTP_PROTOCAL_PREFIX = "http";

    public CrawlingResDto extractionHtml(String html, int printBundleUnit) {
        html = StringUtils.removeLineSeparator(html);
        html = StringUtils.removeSpace(html);
        html = StringUtils.removeTab(html);

        StringBuilder numberLetter = new StringBuilder()
                , lowercaseLetter = new StringBuilder()
                , uppercaseLetter = new StringBuilder();


        for (int i = 0; i < html.length(); i++) {
            if (Character.isDigit(html.charAt(i))) {
                numberLetter.append(html.charAt(i));
            } else if (Character.getType(html.charAt(i)) == Character.LOWERCASE_LETTER) {
                lowercaseLetter.append(html.charAt(i));
            } else if (Character.getType(html.charAt(i)) == Character.UPPERCASE_LETTER) {
                uppercaseLetter.append(html.charAt(i));
            }
        }

        char[] numArr = numberLetter.toString().toCharArray();
        char[] lowercaseArr = lowercaseLetter.toString().toCharArray();
        char[] uppercaseArr = uppercaseLetter.toString().toCharArray();

        Arrays.sort(numArr);
        Arrays.sort(lowercaseArr);
        Arrays.sort(uppercaseArr);

        StringBuilder customSortedStrBuilder = new StringBuilder();

        int lowercaseIndex = 0;
        int uppercaseIndex = 0;
        int numIndex = 0;
        int index = 0;

        while ( numIndex < numArr.length -1 || lowercaseIndex < lowercaseArr.length - 1 || uppercaseIndex < uppercaseArr.length - 1 ) {
            if ( index % 2 == 0 ) {
                if ( uppercaseIndex < uppercaseArr.length && lowercaseIndex < lowercaseArr.length ) {

                    if (Character.toLowerCase(uppercaseArr[uppercaseIndex]) <= lowercaseArr[lowercaseIndex]) {
                        customSortedStrBuilder.append(uppercaseArr[uppercaseIndex]);
                        uppercaseIndex++;
                    } else {
                        customSortedStrBuilder.append(lowercaseArr[lowercaseIndex]);
                        lowercaseIndex++;
                    }
                } else if ( !(uppercaseIndex < uppercaseArr.length) && lowercaseIndex < lowercaseArr.length) {
                    customSortedStrBuilder.append(lowercaseArr[lowercaseIndex]);
                    lowercaseIndex++;
                } else if ( uppercaseIndex < uppercaseArr.length && !(lowercaseIndex < lowercaseArr.length)) {
                    customSortedStrBuilder.append(uppercaseArr[uppercaseIndex]);
                    uppercaseIndex++;
                }
            } else {
                if ( numIndex < numArr.length ) {
                    customSortedStrBuilder.append(numArr[numIndex]);
                    numIndex++;
                }
            }
            index++;
        }

        int totalLength = customSortedStrBuilder.length();
        int remainder = totalLength % printBundleUnit;

        return CrawlingResDto.builder()
                .remainder(customSortedStrBuilder.substring(totalLength - remainder))
                .share(customSortedStrBuilder.substring(0, totalLength - remainder))
                .build();
    }

    public String getHtml(String destinationUrl) {
        String html = null;

        destinationUrl = destinationUrl.contains(HTTP_PROTOCAL_PREFIX)
                ? destinationUrl : "https://" + destinationUrl;
        try {
            html = Jsoup.connect(destinationUrl).get().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }
}
