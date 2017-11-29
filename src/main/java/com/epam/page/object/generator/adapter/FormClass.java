package com.epam.page.object.generator.adapter;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.jsoup.select.Elements;

public class FormClass implements JavaClass {

    private String packageName;
    private String url;
    private SearchRule searchRule;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public FormClass(String packageName,
                     String url,
                     SearchRule searchRule,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        this.packageName = packageName;
        this.url = url;
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
    public List<JavaField> getFieldsList() throws IOException {
        List<JavaField> fields = new ArrayList<>();

        Elements parentElements = searchRule.extractElementsFromWebSite(url);
        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            fields.addAll(
                innerSearchRule.extractElementsFromElement(parentElements.first()).stream().filter(
                    element -> {
                        try {
                            return !innerSearchRule.getRequiredValueFromFoundElement(element)
                                .isEmpty();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).map(
                    element -> new SearchRuleField(innerSearchRule, element, getPackageName(),
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
