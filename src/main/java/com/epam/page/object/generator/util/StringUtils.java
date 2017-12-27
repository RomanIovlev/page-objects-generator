package com.epam.page.object.generator.util;


/**
 * This is an util class that responsible for transformation of original sentence to camelcase
 * variable.
 */
public class StringUtils {

    /**
     * Constant that indicates maximum amount of words in sentence.
     */
    private static final int MAX_NUMBER_OF_WORDS_IN_NAME = 3;

    private StringUtils() {
    }

    /**
     * Convert original sentence into variable in camelcase, also clean from any random symbols.
     *
     * @param inputString represents original sentence which will be converted to camelcase.
     * @return string converted to camelcase.
     */
    public static String splitCamelCase(String inputString) {
        String trim = inputString.replaceAll("[^\\p{L}0-9 ]", "").trim();

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