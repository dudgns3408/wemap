package com.homework.wemakeprice.martin.utils;

public class HTMLExtractUtils {
    public static String removeTag(String html) {
        return html.replaceAll("\\<.*?\\>", "");
    }
}
