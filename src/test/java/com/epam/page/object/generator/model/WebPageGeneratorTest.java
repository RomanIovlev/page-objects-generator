package com.epam.page.object.generator.model;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

public class WebPageGeneratorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private WebPageGenerator sut;
    WebPageBuilder webPageBuilder;
    StringBuilder invalidUrls;
    List<WebPage> webPages;
    List<URI> urls;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        sut = new WebPageGenerator();
        urls = new ArrayList<URI>();


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

            List<URI> urls = new ArrayList<>();

            URI validUrl = "http://google.com";
            URI invalidUrl = "http://lalala.ga.1";
            URI invalidUr2 = "iAmNotValid.url";

            urls.add(validUrl);
            urls.add(invalidUrl);
            urls.add(invalidUr2);

            sut.generate(urls);

        }
    }
}