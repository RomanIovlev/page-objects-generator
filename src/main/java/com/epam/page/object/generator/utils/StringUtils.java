package com.epam.page.object.generator.utils;

public class StringUtils {

    public static final int MAX_NUMBER_OF_WORDS_IN_NAME = 3;

    private StringUtils() {

	}

	public static String splitCamelCase(String camel) {
        String trim = camel.replaceAll("[^A-Za-z0-9 ]", "").trim();
        StringBuilder result = new StringBuilder((trim.charAt(0) + "").toLowerCase());
        int spaces = 0;

        for (int i = 1; i < trim.length(); i++) {
            String letter = trim.charAt(i) + "";

            if (letter.equals(" ")) {
                if (++spaces == MAX_NUMBER_OF_WORDS_IN_NAME) {

                    return result.toString();
                }
            } else {
                result.append(trim.charAt(i - 1) == ' '
                    ? letter.toUpperCase()
                    : letter.toLowerCase());
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