package com.homework.wemakeprice.martin.service;

import com.homework.wemakeprice.martin.controller.rest.crawling.dto.CrawlingResDto;
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

        // todo :: 가독성 및 재사용성때문에 함수로 만들었으나, 이로인해 1/3정도의 성능저하가 있음. 속도 문제있을경우 루프한번에 처리하도록 수정 가능
        String numberLetters = extractionNumber(html)
                , lowercaseLetters = extractionLetter(html, Character.LOWERCASE_LETTER)
                , uppercaseLetters = extractionLetter(html, Character.UPPERCASE_LETTER);

        char[] numArr = numberLetters.toCharArray()
                , lowercaseArr = lowercaseLetters.toCharArray()
                , uppercaseArr = uppercaseLetters.toCharArray();

        Arrays.sort(numArr);
        Arrays.sort(lowercaseArr);
        Arrays.sort(uppercaseArr);

        StringBuilder customSortedStrBuilder = new StringBuilder();

        int lowercaseIndex = 0
                , uppercaseIndex = 0
                , numIndex = 0
                , index = 0;

        boolean isRemainNum, isRemainLowercase, isRemainUppercase;

        while ( isRemain(numArr, numIndex)
                || isRemain(lowercaseArr, lowercaseIndex)
                || isRemain(uppercaseArr, uppercaseIndex))
        {
            isRemainNum = isRemain(numArr, numIndex);
            isRemainUppercase = isRemain(uppercaseArr, uppercaseIndex);
            isRemainLowercase = isRemain(lowercaseArr, lowercaseIndex);

            if (isNumberOrder(isRemainNum, index)) {
                append(customSortedStrBuilder, numArr[numIndex++]);
            } else {
                if (!isRemainUppercase && isRemainLowercase) {
                    append(customSortedStrBuilder, lowercaseArr[lowercaseIndex++]);
                    continue;
                }

                if (!isRemainLowercase && isRemainUppercase) {
                    append(customSortedStrBuilder, uppercaseArr[uppercaseIndex++]);
                    continue;
                }

                if (compareLetter(uppercaseArr[uppercaseIndex], lowercaseArr[lowercaseIndex])) {
                    append(customSortedStrBuilder, uppercaseArr[uppercaseIndex++]);
                } else {
                    append(customSortedStrBuilder, lowercaseArr[lowercaseIndex++]);
                }
            }

            index++;
        }

        int totalLength = customSortedStrBuilder.length()
                , boundaryValue = totalLength - totalLength % printBundleUnit;

        return CrawlingResDto.builder()
                .remainder(customSortedStrBuilder.substring(boundaryValue))
                .share(customSortedStrBuilder.substring(0, boundaryValue))
                .build();
    }

    private boolean compareLetter(char c1, char c2) {
        return Character.toLowerCase(c1) <= c2;
    }

    private void append(StringBuilder stringBuilder, char c) {
        stringBuilder.append(c);
    }

    private boolean isNumberOrder(boolean isRemainNum, int index) {
        return isRemainNum && index % 2 == 1;
    }

    private boolean isRemain(char[] arr, int index) {
        return index < arr.length - 1;
    }

    private String extractionLetter(String html, byte type) {
        StringBuilder stringBuilder = new StringBuilder();

        char c;
        for (int i = 0; i < html.length(); i++) {
            c = html.charAt(i);
            if (Character.getType(c) == type) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    private String extractionNumber(String html) {
        StringBuilder stringBuilder = new StringBuilder();

        char c;
        for (int i = 0; i < html.length(); i++) {
            c = html.charAt(i);
            if (Character.isDigit(c)) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
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
