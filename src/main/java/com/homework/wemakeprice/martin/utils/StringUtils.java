package com.homework.wemakeprice.martin.utils;

public class StringUtils {
    public static String removeLineSeparator(String str) {
        return str.replaceAll(System.lineSeparator(), "");
    }

    public static String removeSpace(String str) {
        return str.replaceAll("\\p{Z}", "");
    }

    public static String removeTab(String str) {
        return str.replaceAll("\\s", "");
    }
}
