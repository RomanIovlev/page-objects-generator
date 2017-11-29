package com.epam.page.object.generator.adapter;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import org.jsoup.select.Elements;

public class FormClass extends JavaPoetClass {

    private SearchRule searchRule;
    private WebPage webPage;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public FormClass(String outputDir,
                     String packageName,
                     SearchRule searchRule,
                     WebPage webPage,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        super(outputDir, packageName);
        this.searchRule = searchRule;
        this.webPage = webPage;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
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

        Elements parentElements = searchRule.extractElementsFromElement(webPage.getDocument());
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
