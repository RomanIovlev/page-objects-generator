package com.epam.page.object.generator.builder;

import static java.lang.reflect.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FormClass extends JavaPoetClass {
    private SearchRule searchRule;
    private String url;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public FormClass(String outputDir,
                     String packageName,
                     SearchRule searchRule,
                     String url,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        super(outputDir, packageName);
        this.searchRule = searchRule;
        this.url = url;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public String getClassName() {
        return searchRule.getSection();
    }

    @Override
    public Class getSuperClass() {
        return searchRule.getType().equals(SearchRuleType.FORM.getName()) ? Form.class : Section.class;

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
            Elements elements = innerSearchRule.extractElementsFromElement(parentElements.first());
            for (Element element : elements) {
                if (!innerSearchRule.getRequiredValueFromFoundElement(element).isEmpty()) {
                    fields.add(new FormField(innerSearchRule, element, typesContainer, xpathToCssTransformation));
                }
            }
        }

        return fields;
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{Modifier.PUBLIC};
    }
}
