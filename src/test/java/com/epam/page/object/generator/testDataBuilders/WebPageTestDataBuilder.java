package com.epam.page.object.generator.testDataBuilders;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.models.ClassAndAnnotationPair;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.WebPage;
import com.epam.page.object.generator.models.searchRules.CommonSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.support.FindBy;

public class WebPageTestDataBuilder {

    private static SearchRuleExtractor extractor = new SearchRuleExtractor();

    public static WebPage getWebPage(String filePath) {
        String html = null;
        try {
            html = Files
                .toString(new File(WebPageTestDataBuilder.class.getResource(filePath).getFile()),
                    Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);

        return new WebPage(null, doc, extractor);
    }

    public static WebPage getJdiWebPage() {
        String html = null;
        try {
            html = Files
                .toString(new File(WebPageTestDataBuilder.class
                        .getResource("/jsonForBuilders/webPage/jdi/test.html").getFile()),
                    Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);

        WebPage webPage = new WebPage(null, doc, extractor);

        XpathToCssTransformer transformer = new XpathToCssTransformer();
        SelectorUtils selectorUtils = new SelectorUtils();
        List<SearchRule> searchRules = Lists.newArrayList(
            new CommonSearchRule("text", SearchRuleType.BUTTON,
                new Selector("css", "button[type=submit][id=calculate-button]"),
                new ClassAndAnnotationPair(Button.class, FindBy.class), transformer,
                selectorUtils),
            new CommonSearchRule("text", SearchRuleType.LABEL,
                new Selector("xpath", "//label[@for='g1']"),
                new ClassAndAnnotationPair(Label.class, FindBy.class), transformer, selectorUtils),
            new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(
                new ComplexInnerSearchRule("title", "root",
                    new Selector("css", "button[data-id=colors-dropdown]"), transformer),
                new ComplexInnerSearchRule(null, "list",
                    new Selector("xpath", "//ul[@class='dropdown-menu inner selectpicker']"),
                    transformer)), new ClassAndAnnotationPair(Dropdown.class, JDropdown.class),
                selectorUtils));

        webPage.addSearchRules(searchRules);

        return webPage;
    }
}
