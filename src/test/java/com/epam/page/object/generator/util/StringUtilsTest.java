package com.epam.page.object.generator.util;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void splitCamelCaseLessThanThreeWords_StringUtils_Valid() throws Exception {
        String camel = "Word1 Word2";
        String expectedResult = "word1Word2";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseMoreThanThreeWords_StringUtils_Valid() throws Exception {
        String camel = "Word1 Word2 Word3 Word4";
        String expectedResult = "word1Word2Word3";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseThreeWords_StringUtils_Valid() throws Exception {
        String camel = "Word1 Word2 Word3";
        String expectedResult = "word1Word2Word3";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseEmptyWords_StringUtils_Valid() throws Exception {
        String camel = " ";
        String expectedResult = "";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void splitCamelCaseWordsSplitRandomSymbols_StringUtils_Valid() throws Exception {
        String camel = " Word*? @, / . $% & ! Word1";
        String expectedResult = "wordWord1";

        String result = StringUtils.splitCamelCase(camel);

        assertEquals(expectedResult, result);
    }

    @Test
    public void firstLetterUpWordWithLowerCaseFirstLetter_StringUtils_Valid() throws Exception {
        String word = "word1 word2";
        String expectedResult = "Word1 word2";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void capitalize_ApacheStringUtils_Valid() throws Exception {
        String word = "Word1 word2";
        String expectedResult = "Word1 word2";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void capitalizeWithLowerCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "w";
        String expectedResult = "W";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void capitalizeWithUpperCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "W";
        String expectedResult = "W";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void capitalizeWithEmptyLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "";
        String expectedResult = "";

        String result = capitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void uncapitalizeWithLowerCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "word1 word2";
        String expectedResult = "word1 word2";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void uncapitalizeWithUpperCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "Word1 word2";
        String expectedResult = "word1 word2";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void uncapitalizeWithDownLowerCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "w";
        String expectedResult = "w";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void uncapitalizeWithUpperLowerCaseLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "W";
        String expectedResult = "w";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }

    @Test
    public void uncapitalizeWithEmptyLetter_ApacheStringUtils_Valid() throws Exception {
        String word = "";
        String expectedResult = "";

        String result = uncapitalize(word);

        assertEquals(expectedResult, result);
    }
}