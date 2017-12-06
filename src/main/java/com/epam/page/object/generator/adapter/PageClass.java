package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;


import com.epam.page.object.generator.adapter.searchRuleFields.CommonSearchRuleField;
import com.epam.page.object.generator.adapter.searchRuleFields.ComplexSearchRuleField;
import com.epam.page.object.generator.adapter.searchRuleFields.FormSearchRuleField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class PageClass implements IJavaClass {

    private String packageName;
    private WebPage webPage;
    private SupportedTypesContainer typesContainer;

    public PageClass(String packageName,
                     WebPage webPage,
                     SupportedTypesContainer typesContainer) {
        this.packageName = packageName;
        this.webPage = webPage;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getClassName() {
        return firstLetterUp(splitCamelCase(webPage.getTitle()));
    }

    @Override
    public Class getSuperClass() {
        return com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public List<IJavaField> getFieldsList() {
        List<IJavaField> fields = new ArrayList<>();

        for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
            SearchRule searchRule = webElementGroup.getSearchRule();
            if (searchRule instanceof CommonSearchRule) {

                List<WebElement> webElements = webElementGroup.getWebElements();
                for (WebElement webElement : webElements) {
                    fields.add(
                        new CommonSearchRuleField((CommonSearchRule) searchRule,
                            webElement.getElement(),
                            typesContainer));
                }

            } else if (searchRule instanceof ComplexSearchRule) {

                List<WebElement> webElements = webElementGroup.getWebElements();
                for (WebElement webElement : webElements) {
                    fields.add(
                        new ComplexSearchRuleField((ComplexSearchRule) searchRule,
                            webElement.getElement(),
                            typesContainer));
                }

            } else if (searchRule instanceof FormSearchRule) {
                fields.add(
                    new FormSearchRuleField((FormSearchRule) searchRule, typesContainer));
            }
        }

        return fields;
    }

    @Override
    public Modifier getModifier() {
        return PUBLIC;
    }
}
