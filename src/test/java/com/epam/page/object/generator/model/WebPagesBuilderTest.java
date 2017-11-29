package com.epam.page.object.generator.model;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

public class WebPagesBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private WebPagesBuilder sut;
    WebPagesBuilder webPagesBuilder;
    StringBuilder invalidUrls;
    List<WebPage> webPages;
    List<String> urls;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        sut = new WebPagesBuilder();
        urls = new ArrayList<>();


    }

    @Test
    public void generate_validUrls() throws Exception {

        urls.add("http://ya.ru");
        urls.add("http://google.com");
        sut.generate(urls);

    }

    @Test(expected = NotValidUrlException.class)
    public void validate_NotValidUrl() throws Exception {

        urls.add("C:/documents/santa_barbara");
        sut.generate(urls);
    }


    @Test
    public void validateCoupleOfInvalidUrls() throws Exception {

        TestThing testThing = new TestThing();

        thrown.expect(NotValidUrlException.class);
        thrown.expectMessage(containsString("http://lalala.ga.1"));
        thrown.expectMessage(containsString("iAmNotValid.url"));
        thrown.expectMessage(not(containsString("http://google.com")));

        testThing.chuck();
    }

    private class TestThing {

        public void chuck() {

            List<String> urls = new ArrayList<>();

            String validUrl = "http://google.com";
            String invalidUrl = "http://lalala.ga.1";
            String invalidUr2 = "iAmNotValid.url";

            urls.add(validUrl);
            urls.add(invalidUrl);
            urls.add(invalidUr2);

            sut.generate(urls);

        }
    }
}