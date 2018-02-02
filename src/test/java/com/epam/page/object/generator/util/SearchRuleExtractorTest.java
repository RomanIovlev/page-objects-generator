package com.epam.page.object.generator.util;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

public class SearchRuleExtractorTest {

    @Mock
    private Element element;

    @Mock
    private Elements elements;

    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();

    private SearchRule searchRuleWithCss = new CommonSearchRule(
        "text",
        SearchRuleType.BUTTON,
        new Selector("css", ".myClass"),
        new ClassAndAnnotationPair(Button.class, FindBy.class),
        transformer,
        selectorUtils
    );

    private SearchRule searchRuleWithXpath = new CommonSearchRule(
        "text",
        SearchRuleType.BUTTON,
        new Selector("xpath", "//input[@type='submit']"),
        new ClassAndAnnotationPair(Button.class, FindBy.class),
        transformer,
        selectorUtils
    );

    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void extractElementsFromElement_SearchRuleWithXpath_NotNull() {
        String html = "<input value='Поиск в Google' aria-label='Поиск в Google' name='btnK' type='submit' jsaction='sf.chk'/>";
        Document doc = Jsoup.parse(html);
        Element buttonElement = doc.select("input[type=submit]").first();

        Elements elements = searchRuleExtractor
            .extractElementsFromElement(buttonElement, searchRuleWithXpath);

        assertNotNull(elements);
    }

    @Test
    public void extractElementsFromElement_SearchRuleWithCss_StatusOk() {
        when(element.select(anyString())).thenReturn(elements);

        searchRuleExtractor.extractElementsFromElement(element, searchRuleWithCss);

        verify(element).select(eq(searchRuleWithCss.getSelector().getValue()));
    }

}