package com.epam.page.object.generator.model;

import com.epam.page.object.generator.adapter.FormClass;
import com.epam.page.object.generator.adapter.JavaClassBuildable;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPage {

    private final URI uri;
    private Document document;
    private List<WebElementGroup> webElementGroups;

    protected WebPage(URI uri, Document document) {
        this.webElementGroups = new ArrayList<>();
        this.uri = uri;
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public String getTitle() {
        return document.title();
    }

    public URI getUri() {
        return uri;
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
            Elements elements = extractElements(searchRule);
            if (elements.size() != 0) {
                if (searchRule instanceof CommonSearchRule) {
                    webElementGroups.add(new CommonWebElementGroup((CommonSearchRule) searchRule,
                        searchRule.getWebElements(elements)));
                }
                else if(searchRule instanceof ComplexSearchRule){
                    webElementGroups.add(new ComplexWebElementGroup((ComplexSearchRule) searchRule,
                        searchRule.getWebElements(elements)));
                }
                else if(searchRule instanceof FormSearchRule){
                    webElementGroups.add(new FormWebElementGroup((FormSearchRule) searchRule,
                        searchRule.getWebElements(elements)));
                }
            }
        }
    }

    public boolean isContainedFormSearchRule() {
        return webElementGroups.stream()
            .anyMatch(webElementGroup -> webElementGroup.getSearchRule() instanceof FormSearchRule);
    }

    public Elements extractElements(SearchRule searchRule) {
        return SearchRuleExtractor.extractElementsFromElement(document, searchRule);
    }

    public Element extractElement(SearchRule searchRule) {
        return SearchRuleExtractor.extractElement(document, searchRule);
    }

    public boolean hasInvalidWebElementGroup(){
        return webElementGroups.stream().anyMatch(Validatable::isInvalid);
    }

    public List<JavaClassBuildable> getFormClasses(){
        List<JavaClassBuildable> javaClasses = new ArrayList<>();

        for (WebElementGroup webElementGroup : webElementGroups) {
            if(webElementGroup instanceof FormWebElementGroup){
                javaClasses.add(new FormClass((FormWebElementGroup) webElementGroup));
            }
        }

        return javaClasses;
    }

}
