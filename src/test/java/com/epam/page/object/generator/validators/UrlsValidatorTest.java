package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.NotValidUrlException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UrlsValidatorTest {

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
}