package com.epam.page.object.generator.model;

import com.epam.page.object.generator.adapter.classbuildable.FormClass;
import com.epam.page.object.generator.adapter.classbuildable.JavaClassBuildable;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebPage {

    private final URI uri;
    private Document document;
    private List<WebElementGroup> webElementGroups;
    private SearchRuleExtractor searchRuleExtractor;

    private final static Logger logger = LoggerFactory.getLogger(WebPage.class);

    public WebPage(URI uri, Document document,
                   SearchRuleExtractor searchRuleExtractor) {
        this.searchRuleExtractor = searchRuleExtractor;
        this.webElementGroups = new ArrayList<>();
        this.uri = uri;
        this.document = document;
    }

    public String getTitle() {
        return document.title();
    }

    public String getUrlWithoutDomain() {
        return uri.getPath();
    }

    public String getDomainName() {
        return uri.getHost();
    }

    public List<WebElementGroup> getWebElementGroups() {
        return webElementGroups;
    }

    public void addSearchRules(List<SearchRule> searchRules) {
        for (SearchRule searchRule : searchRules) {
            Elements elements = searchRuleExtractor
                .extractElementsFromElement(document, searchRule);
            if (elements.size() != 0) {
                searchRule.fillWebElementGroup(webElementGroups, elements);
            }
        }
    }

    public boolean isContainedFormSearchRule() {
        return webElementGroups.stream()
            .anyMatch(webElementGroup -> webElementGroup.getSearchRule() instanceof FormSearchRule);
    }

    public boolean hasInvalidWebElementGroup() {
        return webElementGroups.stream().anyMatch(Validatable::isInvalid);
    }

    public List<JavaClassBuildable> getFormClasses(SelectorUtils selectorUtils) {
        List<JavaClassBuildable> javaClasses = new ArrayList<>();

        for (WebElementGroup webElementGroup : webElementGroups) {
            if (webElementGroup instanceof FormWebElementGroup) {
                FormWebElementGroup elementGroup = (FormWebElementGroup) webElementGroup;
                logger.debug("Start creating FormClass for '" + elementGroup.getSearchRule().getSection() + "' form");
                javaClasses.add(new FormClass(elementGroup,
                    selectorUtils));
                logger.debug("Finish creating FormClass");
            }
        }

        return javaClasses;
    }
}
