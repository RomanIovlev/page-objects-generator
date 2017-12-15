package com.epam.page.object.generator.testDataBuilders;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.error.NotValidPathsException;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebPageTestDataBuilder {

    private static SearchRuleExtractor extractor = new SearchRuleExtractor();
    private final static Logger logger = LoggerFactory.getLogger(WebPageTestDataBuilder.class);

    public static WebPage getJdiWebPage() {
        String jdiPathFile = "/jsonForBuilders/webPage/jdi/test.html";
        try {
            String html = Files
                .toString(new File(WebPageTestDataBuilder.class.getResource(jdiPathFile).getFile()),
                    Charsets.UTF_8);
            Document doc = Jsoup.parse(html);
            WebPage webPage = new WebPage(new URI("https://epam.github.io/JDI/metals-colors.html"),
                doc, extractor);
            XpathToCssTransformer transformer = new XpathToCssTransformer();
            SelectorUtils selectorUtils = new SelectorUtils();
            List<SearchRule> searchRules = Lists.newArrayList(
                new CommonSearchRule("text", SearchRuleType.BUTTON,
                    new Selector("css", "button[type=submit][id=calculate-button]"),
                    new ClassAndAnnotationPair(Button.class, FindBy.class), transformer,
                    selectorUtils),
                new CommonSearchRule("text", SearchRuleType.LABEL,
                    new Selector("xpath", "//label[@for='g1']"),
                    new ClassAndAnnotationPair(Label.class, FindBy.class), transformer,
                    selectorUtils),
                new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(
                    new ComplexInnerSearchRule("title", "root",
                        new Selector("css", "button[data-id=colors-dropdown]"), transformer),
                    new ComplexInnerSearchRule(null, "list",
                        new Selector("xpath", "//ul[@class='dropdown-menu inner selectpicker']"),
                        transformer)), new ClassAndAnnotationPair(Dropdown.class, JDropdown.class),
                    selectorUtils));

            webPage.addSearchRules(searchRules);

            return webPage;
        } catch (URISyntaxException e) {
            String message = "Not correct URI for the '" + jdiPathFile + "' file" + Arrays
                .toString(e.getStackTrace());
            logger.error(message);
            throw new NotValidPathsException(message);
        } catch (IOException e) {
            String message =
                "File '" + jdiPathFile + "' doesn't exist!" + Arrays.toString(e.getStackTrace());
            logger.error(message);
            throw new NotValidPathsException(message);
        }
    }
}
