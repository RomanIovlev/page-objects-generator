package com.epam.page.object.generator.adapter;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class FormClass implements JavaClass {

    private String packageName;
    private WebPage webPage;
    private FormSearchRule searchRule;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public FormClass(String packageName,
                     WebPage webPage,
                     FormSearchRule searchRule,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        this.packageName = packageName;
        this.webPage = webPage;
        this.searchRule = searchRule;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
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
        return searchRule.getType().equals(SearchRuleType.FORM.getName()) ? Form.class
            : Section.class;

    }

    @Override
    public JavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public List<JavaField> getFieldsList() {
        List<JavaField> fields = new ArrayList<>();

        Element parentElement = webPage.extractElement(searchRule);

        for (FormInnerSearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            fields.addAll(
                SearchRuleExtractor.extractElementsFromElement(parentElement, innerSearchRule)
                    .stream()
                    .filter(element -> !innerSearchRule.getRequiredValue(element).isEmpty())
                    .map(element -> new SearchRuleField(innerSearchRule, element, getPackageName(),
                        typesContainer,
                        xpathToCssTransformation)).collect(Collectors.toList()));
        }

        return fields;
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{Modifier.PUBLIC};
    }
}
