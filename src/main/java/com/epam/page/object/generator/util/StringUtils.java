package com.epam.page.object.generator.util;

public class StringUtils {

    private static final int MAX_NUMBER_OF_WORDS_IN_NAME = 3;

    private StringUtils() {
    }

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
}