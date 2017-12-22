package com.epam.page.object.generator.databuilder;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.error.NotValidPathsException;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
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
import java.util.List;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebPageTestDataBuilder {

    private static SearchRuleExtractor extractor = new SearchRuleExtractor();
    private static XpathToCssTransformer transformer = new XpathToCssTransformer();
    private static SelectorUtils selectorUtils = new SelectorUtils();
    private final static Logger logger = LoggerFactory.getLogger(WebPageTestDataBuilder.class);

    public static WebPage getJdiWebPage() {
        String jdiPathFile = "/jsonForBuilders/webPage/jdi/test.html";
        String uri = "https://epam.github.io/JDI/metals-colors.html";
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
        return getWebPage(jdiPathFile, uri, searchRules);
    }

    public static WebPage getJdiContactFormWebPage() {
        String jdiPathFile = "/jsonForBuilders/webPage/jdi/contactForm.html";
        String uri = "https://epam.github.io/JDI/contacts.html";
        List<SearchRule> searchRules = Lists.newArrayList(
            new FormSearchRule("ContactForm", SearchRuleType.FORM,
                new Selector("xpath", "//form[@class='form']"), Lists.newArrayList(
                new FormInnerSearchRule("id", SearchRuleType.INPUT,
                    new Selector("xpath", "//input[@id=Name]"),
                    new ClassAndAnnotationPair(Input.class, FindBy.class),
                    WebPageTestDataBuilder.transformer, extractor),
                new FormInnerSearchRule("id", SearchRuleType.INPUT,
                    new Selector("xpath", "//input[@id=LastName]"),
                    new ClassAndAnnotationPair(Input.class, FindBy.class),
                    WebPageTestDataBuilder.transformer, extractor),
                new FormInnerSearchRule("text", SearchRuleType.BUTTON,
                    new Selector("xpath",
                        "//button[@type='submit'][@class='uui-button dark-blue']"),
                    new ClassAndAnnotationPair(Button.class, FindBy.class),
                    WebPageTestDataBuilder.transformer, extractor)),
                new ClassAndAnnotationPair(Form.class, FindBy.class)));
        return getWebPage(jdiPathFile, uri, searchRules);
    }

    private static WebPage getWebPage(String pathFile, String uri, List<SearchRule> searchRules) {
        try {
            String html = Files
                .toString(new File(WebPageTestDataBuilder.class.getResource(pathFile).getFile()),
                    Charsets.UTF_8);
            Document doc = Jsoup.parse(html);
            WebPage webPage = new WebPage(new URI(uri), doc, extractor);
            webPage.addSearchRules(searchRules);

            return webPage;
        } catch (URISyntaxException e) {
            throw new NotValidPathsException("Not correct URI for the '" + pathFile + "' file");
        } catch (IOException | NullPointerException e) {
            throw new NotValidPathsException("File '" + pathFile + "' doesn't exist!");
        }
    }
}