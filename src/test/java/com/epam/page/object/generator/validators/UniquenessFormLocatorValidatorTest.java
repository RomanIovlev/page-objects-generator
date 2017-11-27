package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UniquenessFormLocatorValidatorTest {

    private UniquenessFormLocatorValidator sut;

    private String webSite1 = "webSite1";

    private String webSite2 = "webSite2";

    @Mock
    private ValidationContext validationContext;

    @Mock
    private SearchRule searchRule;

    @Mock
    private Elements elementsFromWebSite1;

    @Mock
    private Elements elementsFromWebSite2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new UniquenessFormLocatorValidator();

        when(validationContext.getUrls())
            .thenReturn(Lists.newArrayList(webSite1, webSite2));
        when(searchRule.extractElementsFromWebSite(webSite1)).thenReturn(elementsFromWebSite1);
        when(searchRule.extractElementsFromWebSite(webSite2)).thenReturn(elementsFromWebSite2);
    }

    @Test
    public void isValid_True() throws Exception {
        when(elementsFromWebSite1.size()).thenReturn(1);
        when(elementsFromWebSite2.size()).thenReturn(0);

        assertTrue(sut.isValid(searchRule, validationContext));

        verify(searchRule, times(validationContext.getUrls().size()))
            .extractElementsFromWebSite(Mockito.any());
    }

    @Test
    public void isValid_FalseElementNotUniqueOnWebSite1() throws Exception {
        when(elementsFromWebSite1.size()).thenReturn(3);

        assertFalse(sut.isValid(searchRule, validationContext));

        verify(searchRule, times(1))
            .extractElementsFromWebSite(Mockito.any());
    }

    @Test
    public void isValid_FalseElementNotUniqueOnWebSite2() throws Exception {
        when(elementsFromWebSite1.size()).thenReturn(0);
        when(elementsFromWebSite2.size()).thenReturn(6);

        assertFalse(sut.isValid(searchRule, validationContext));

        verify(searchRule, times(2))
            .extractElementsFromWebSite(Mockito.any());
    }

    @Test
    public void getPriority_checkIfMoreThan51() throws Exception {
        assertTrue(sut.getPriority() > 51);
    }

}