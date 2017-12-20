package com.epam.page.object.generator.util;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

public class StringUtils {

    /**
     * Constant that indicates maximum amount of words in sentence.
     */
    private static final int MAX_NUMBER_OF_WORDS_IN_NAME = 3;

    private StringUtils() {
    }

    /**
     * Convert original sentence into variable in camelcase, also clean from any random symbols.
     * @param camel
     * @return camelcase variable
     */
    public static String splitCamelCase(String camel) {
        String trim = camel.replaceAll("[^\\p{L}0-9 ]", "").trim();

        if (trim.length() < 1) {
            return trim;
        }

        trim = trim.replaceAll("\\s+", " ");
        StringBuilder result = new StringBuilder((trim.charAt(0) + "").toLowerCase());
        int spaces = 0;

        for (int i = 1; i < trim.length(); i++) {
            Character letter = trim.charAt(i);

            if (letter.equals(' ')) {
                if (++spaces == MAX_NUMBER_OF_WORDS_IN_NAME) {

                    return result.toString();
                }
            } else {
                result.append(trim.charAt(i - 1) == ' '
                    ? Character.toUpperCase(letter)
                    : Character.toLowerCase(letter));
            }
        }

        return result.toString();
    }

    /**
     * Switch first word of first letter to uppercase.
     * @param text
     * @return sentence where first word of first letter is in uppercase.
     */
    public static String firstLetterUp(String text) {
        return text.length() > 0
            ? capitalize(text)
            : text;
    }

    /**
     * Switch first word of first letter to lowercase.
     * @param text
     * @return sentence where first word of first letter is in lowercase.
     */
    public static String firstLetterDown(String text) {
        return text.length() > 0
            ? uncapitalize(text)
            : text;
    }

}