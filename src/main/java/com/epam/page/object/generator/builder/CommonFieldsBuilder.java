package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;
import org.openqa.selenium.support.FindBy;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.page.object.generator.builder.StringUtils.splitCamelCase;
import static com.squareup.javapoet.AnnotationSpec.builder;

public class CommonFieldsBuilder implements IFieldsBuilder {
    public Class elementClass;
    public CommonFieldsBuilder(Class elementClass) {
        this.elementClass = elementClass;
    }
    public List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException {
        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elements = searchRule.getElements(url);

        for (String element : elements)
            abstractFields.add(FieldSpec.builder(elementClass, splitCamelCase(element))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(builder(FindBy.class)
                    .addMember("xpath", "$S", createXPathSelector(searchRule, element))
                    .build())
                .build());

        return abstractFields;
    }

    private String createXPathSelector(SearchRule searchRule, String element) {
        StringBuilder xPathSelector = new StringBuilder();
        xPathSelector.append("//").append(searchRule.tag).append("[");

        appendClassesToXPath(searchRule, xPathSelector);
        appendAttributesToXPath(searchRule, xPathSelector);

        if ("text".equals(searchRule.requiredAttribute))
            xPathSelector.append("text()");
        else
            xPathSelector.append("@").append(searchRule.requiredAttribute);

        xPathSelector.append("='").append(element).append("']");

        return xPathSelector.toString();
    }

    private void appendClassesToXPath(SearchRule searchRule, StringBuilder xPathSelector) {
        if (!searchRule.classesAreEmpty()) {
            xPathSelector.append("@class='");
            searchRule.classes.forEach(clazz -> xPathSelector.append(clazz).append(" "));
            xPathSelector.deleteCharAt(xPathSelector.lastIndexOf(" "));
            xPathSelector.append("' and ");
        }
    }

    private void appendAttributesToXPath(SearchRule searchRule, StringBuilder xPathSelector) {
        if (!searchRule.attributesAreEmpty()) {
            searchRule.attributes.forEach(elementAttribute -> xPathSelector.append("@")
                .append(elementAttribute.getAttributeName())
                .append("='").append(elementAttribute.getAttributeValue()).append("'")
                .append(" and "));
        }
    }
}