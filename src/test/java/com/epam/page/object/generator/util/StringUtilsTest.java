package com.epam.page.object.generator.util;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void splitCamelCaseLessThanThreeWords() throws Exception {
        String camel = "Word1 Word2";
        String expectedResult = "word1Word2";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseMoreThanThreeWords() throws Exception {
        String camel = "Word1 Word2 Word3 Word4";
        String expectedResult = "word1Word2Word3";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseThreeWords() throws Exception {
        String camel = "Word1 Word2 Word3";
        String expectedResult = "word1Word2Word3";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseEmptyWords() throws Exception {
        String camel = " ";
        String expectedResult = "";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseWordsSplitRandomSymbols() throws Exception {
        String camel = " Word*? @, / . $% & ! Word1";
        String expectedResult = "wordWord1";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpWordWithLowerCaseFirstLetter() throws Exception {
        String word = "word1 word2";
        String expectedResult = "Word1 word2";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpWordWithUpperCaseFirstLetter() throws Exception {
        String word = "Word1 word2";
        String expectedResult = "Word1 word2";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpLowerCaseLetter() throws Exception {
        String word = "w";
        String expectedResult = "W";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpUpperCaseLetter() throws Exception {
        String word = "W";
        String expectedResult = "W";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpEmptyWord() throws Exception {
        String word = "";
        String expectedResult = "";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterDownWordWithLowerCaseFirstLetter() throws Exception {
        String word = "word1 word2";
        String expectedResult = "word1 word2";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterDownWordWithUpperCaseFirstLetter() throws Exception {
        String word = "Word1 word2";
        String expectedResult = "word1 word2";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterDownLowerCaseLetter() throws Exception {
        String word = "w";
        String expectedResult = "w";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterDownUpperCaseLetter() throws Exception {
        String word = "W";
        String expectedResult = "w";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterDownEmptyWord() throws Exception {
        String word = "";
        String expectedResult = "";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }
}