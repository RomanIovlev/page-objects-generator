package com.epam.page.object.generator.adapter;

import static javax.lang.model.element.Modifier.*;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.adapter.searchRuleFields.FormInnerSearchRuleField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class FormClass implements IJavaClass {

    private String packageName;
    private WebPage webPage;
    private FormSearchRule searchRule;
    private SupportedTypesContainer typesContainer;

    public FormClass(String packageName,
                     WebPage webPage,
                     FormSearchRule searchRule,
                     SupportedTypesContainer typesContainer) {
        this.packageName = packageName;
        this.webPage = webPage;
        this.searchRule = searchRule;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getClassName() {
        return searchRule.getSection();
    }

    @Override
    public Class getSuperClass() {
        return searchRule.getTypeName().equals(SearchRuleType.FORM.getName()) ? Form.class
            : Section.class;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public List<IJavaField> getFieldsList() {
        List<IJavaField> fields = new ArrayList<>();

        Element parentElement = webPage.extractElement(searchRule);

        for (FormInnerSearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            fields.addAll(
                SearchRuleExtractor.extractElementsFromElement(parentElement, innerSearchRule)
                    .stream()
                    .filter(element -> !innerSearchRule.getRequiredValue(element).isEmpty())
                    .map(element -> new FormInnerSearchRuleField(innerSearchRule, element,
                        typesContainer)).collect(Collectors.toList()));
        }

        return fields;
    }

    @Override
    public Modifier getModifier() {
        return PUBLIC;
    }
}
