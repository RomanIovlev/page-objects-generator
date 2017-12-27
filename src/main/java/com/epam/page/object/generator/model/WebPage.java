package com.epam.page.object.generator.model;

import com.epam.page.object.generator.adapter.classbuildable.FormClass;
import com.epam.page.object.generator.adapter.classbuildable.JavaClassBuildable;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.model.webelement.WebElement;
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

/**
 * Represents web-page (which POG is parsing) with it's URI and list of {@link WebElementGroup}
 * elements.
 */

public class WebPage {

    private final URI uri;

    private Document document;

    /**
     * List of {@link WebElementGroup} elements. Every {@link WebElementGroup} represents list of
     * {@link WebElement}, which was found with one of the {@link SearchRule}. Every {@link
     * SearchRule} corresponds to the {@link WebElementGroup}.
     */
    private List<WebElementGroup> webElementGroups;

    /**
     * Extract page elements using {@link SearchRule} objects.
     */
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

    /**
     * This method tries to extract {@link Elements} by each {@link SearchRule} and if elements were
     * found on this web-page, then add them into this {@link WebPage} as a new {@link
     * WebElementGroup}.
     *
     * @param searchRules list of {@link SearchRule} which is used by method to parse current
     * web-page.
     */
    public void addSearchRules(List<SearchRule> searchRules) {
        for (SearchRule searchRule : searchRules) {
            Elements elements = searchRuleExtractor
                .extractElementsFromElement(document, searchRule);
            if (elements.size() != 0) {
                searchRule.fillWebElementGroup(webElementGroups, elements);
            }
        }
    }

    /**
     * Checks if web-page contains forms. If it's not - no need to create separated form classes.
     */
    public boolean isContainedFormSearchRule() {
        return webElementGroups.stream()
            .anyMatch(webElementGroup -> webElementGroup.getSearchRule() instanceof FormSearchRule);
    }

    /**
     * Validates all {@link WebElementGroup} of current {@link WebPage}.
     */
    public boolean hasInvalidWebElementGroup() {
        return webElementGroups.stream().anyMatch(Validatable::isInvalid);
    }

    /**
     * Generates list of {@link JavaClassBuildable} for every {@link FormWebElementGroup} found on a
     * page.
     *
     * @param selectorUtils {@link SelectorUtils} object to parse selector of webElementGroup search
     * rule
     */
    public List<JavaClassBuildable> getFormClasses(SelectorUtils selectorUtils) {
        List<JavaClassBuildable> javaClasses = new ArrayList<>();

        for (WebElementGroup webElementGroup : webElementGroups) {
            if (webElementGroup instanceof FormWebElementGroup) {
                FormWebElementGroup elementGroup = (FormWebElementGroup) webElementGroup;
                logger.debug(
                    "Start creating FormClass for '" + elementGroup.getSearchRule().getSection()
                        + "' form");
                javaClasses.add(new FormClass(elementGroup,
                    selectorUtils));
                logger.debug("Finish creating FormClass");
            }
        }
        return javaClasses;
    }
}
