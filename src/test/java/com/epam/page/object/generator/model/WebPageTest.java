package com.epam.page.object.generator.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.searchRules.SearchRule;
import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WebPageTest {

    private WebPage sut;

    @Mock
    private Elements elementsFromWebSite1;

    @Mock
    private Elements elementsFromWebSite2;

    @Mock
    private
    Document document;

    @Mock
    private
    SearchRule searchRule1;

    @Mock
    private
    SearchRule searchRule2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new WebPage(null, document);
    }

//    @Test
//    public void addSearchRulesForCurrentWebPage() throws Exception {
//        when(searchRule1.extractElementsFromElement(document)).thenReturn(elementsFromWebSite1);
//        when(searchRule2.extractElementsFromElement(document)).thenReturn(elementsFromWebSite2);
//
//        when(elementsFromWebSite1.size()).thenReturn(0);
//        when(elementsFromWebSite2.size()).thenReturn(1);
//
////        sut.addSearchRules(Arrays.asList(searchRule1,searchRule2));
//
////        assertTrue(sut.getSearchRules().size()==1);
////        assertTrue(sut.getSearchRules().size()==1);
//    }

}