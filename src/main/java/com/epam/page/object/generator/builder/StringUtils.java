package com.epam.page.object.generator.builder;

import static java.lang.Character.*;
import static java.lang.Character.toUpperCase;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class StringUtils {

    public static String splitCamelCase(String camel) {
        String trim = camel.replaceAll("[^A-Za-z0-9 ]", "").trim();
        StringBuilder result = new StringBuilder((Character.toString(trim.charAt(0))).toLowerCase());
        int spaces = 0;
        for (int i = 1; i < trim.length(); i++) {
            Character letter = trim.charAt(i);
            if (letter == ' ') {
                if (++spaces == 3) {
                    return result.toString();
                }
            } else {
                result.append(trim.charAt(i - 1) == ' '
                    ? toUpperCase(letter)
                    : toLowerCase(letter));
            }
        }
        return result.toString();
    }

    public static String firstLetterUp(String text) {
        return (text.charAt(0) + "").toUpperCase() + text.substring(1);
    }

    public static String firstLetterDown(String text) {
        return (text.charAt(0) + "").toLowerCase() + text.substring(1);
    }
}
