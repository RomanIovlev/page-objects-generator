package com.epam.page.object.generator.builder;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.builder.webpage.UrlWebPageBuilder;
import com.epam.page.object.generator.error.NotValidUrlException;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

public class UrlWebPageBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UrlWebPageBuilder sut;
    private List<String> urls;

    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new UrlWebPageBuilder();
        urls = new ArrayList<>();
    }

    @Test
    public void generate_ValidUrls_Success() throws Exception {
        urls.add("http://ya.ru");
        urls.add("http://google.com");
        List<WebPage> webPages = sut.generate(urls, searchRuleExtractor);

        assertTrue(webPages.size() > 0);
    }

    @Test(expected = NotValidUrlException.class)
    public void generate_NotValidUrl_Exception() throws Exception {
        urls.add("123123");
        sut.generate(urls, searchRuleExtractor);
    }


    @Test
    public void validate_CoupleOfInvalidUrls_Exception() throws Exception {
        TestThing testThing = new TestThing();

        thrown.expect(NotValidUrlException.class);
        thrown.expectMessage(containsString("http://lalala.ga.1"));
        thrown.expectMessage(containsString("iAmNotValid.url"));
        thrown.expectMessage(not(containsString("http://google.com")));

        testThing.chuck();
    }

    private class TestThing {

        void chuck() {

            List<String> urls = new ArrayList<>();

            String validUrl = "http://google.com";
            String invalidUrl = "http://lalala.ga.1";
            String invalidUr2 = "iAmNotValid.url";

            urls.add(validUrl);
            urls.add(invalidUrl);
            urls.add(invalidUr2);

            sut.generate(urls, searchRuleExtractor);

        }
    }
}