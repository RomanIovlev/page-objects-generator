package com.epam.page.object.generator.validators;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UrlsValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UrlsValidator sut;

    @Mock
    private ValidationContext validationContext;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new UrlsValidator();
    }

    @Test
    public void validate_ValidUrl() throws Exception {
        when(validationContext.getUrls())
            .thenReturn(Collections.singletonList("http://google.com"));

        sut.validate(validationContext);
    }

    @Test(expected = NotValidUrlException.class)
    public void validate_NotValidUrl() throws Exception {
        when(validationContext.getUrls())
            .thenReturn(Collections.singletonList("ht4tp://google.com"));

        sut.validate(validationContext);
    }

    @Test(expected = NotValidUrlException.class)
    public void validate_UnknownHost() throws Exception {
        when(validationContext.getUrls())
            .thenReturn(Collections.singletonList("http://googl45e.com"));

        sut.validate(validationContext);
    }

    @Test
    public void getPriority_CheckIfEquals51() throws Exception {
        assertEquals(51, sut.getPriority());
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
            ValidationContext vc = new ValidationContext(null, urls);
            sut.validate(vc);
        }
    }

}