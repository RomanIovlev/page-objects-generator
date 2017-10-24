package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.builder.StringUtils.splitCamelCase;
import static com.squareup.javapoet.AnnotationSpec.builder;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;
import com.epam.page.object.generator.model.ElementAttribute;
import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.openqa.selenium.support.FindBy;

public class CommonFieldsBuilder implements IFieldsBuilder {

    public Class elementClass;

    static Map<String, Class> annotationMap = new HashMap<>();

    {
        annotationMap.put("button", FindBy.class);
        annotationMap.put("checkbox", FindBy.class);
        annotationMap.put("datepicker", FindBy.class);
        annotationMap.put("fileinput", FindBy.class);
        annotationMap.put("image", FindBy.class);
        annotationMap.put("input", FindBy.class);
        annotationMap.put("label", FindBy.class);
        annotationMap.put("link", FindBy.class);
        annotationMap.put("text", FindBy.class);
        annotationMap.put("textarea", FindBy.class);
        annotationMap.put("textfield", FindBy.class);

        annotationMap.put("menu", JMenu.class);
        annotationMap.put("radiobuttons", FindBy.class);
        annotationMap.put("selector", FindBy.class);
        annotationMap.put("tabs", FindBy.class);
        annotationMap.put("textlist", FindBy.class);
        annotationMap.put("table", JTable.class);
        annotationMap.put("checklist", FindBy.class);
        annotationMap.put("combobox", JComboBox.class);
        annotationMap.put("dropdown", JDropdown.class);
        annotationMap.put("droplist", JDropList.class);
        annotationMap.put("elements", FindBy.class);
    }

    public CommonFieldsBuilder(Class elementClass) {
        this.elementClass = elementClass;
    }

    public List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException {
        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elementsRequiredValues = searchRule
            .extractRequiredValuesFromFoundElements(url);

        for (String elementsRequiredValue : elementsRequiredValues) {

            FieldSpec.Builder field = FieldSpec
                .builder(elementClass, splitCamelCase(elementsRequiredValue))
                .addModifiers(Modifier.PUBLIC);

            field.addAnnotation(buildAnnotation(searchRule, elementsRequiredValue));

            abstractFields.add(field.build());
        }

        return abstractFields;
    }

    private AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue) {
        if (searchRule.innerSearchRules == null) {
            return buildCommonAnnotation(searchRule, elementsRequiredValue);
        } else {
            return buildComplexAnnotation(searchRule);
        }
    }

    private AnnotationSpec buildCommonAnnotation(SearchRule searchRule,
        String elementsRequiredValue) {
        return builder(annotationMap.get(searchRule.type))
            .addMember("xpath", "$S", createXPathSelector(searchRule, elementsRequiredValue))
            .build();
    }

    private AnnotationSpec buildComplexAnnotation(SearchRule searchRule) {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec
            .builder(annotationMap.get(searchRule.type.toLowerCase()));
//
//        for (SearchRule innerSearchRule : searchRule.innerSearchRules) {
//            AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
//
//            if (innerSearchRule.tag != null) {
//                innerAnnotation.addMember("tagName", "$S", innerSearchRule.tag);
//                annotationBuilder
//                    .addMember(innerSearchRule.requiredAttribute, "$L", innerAnnotation.build());
//            }
//
//            for (String clazz : innerSearchRule.classes) {
//                innerAnnotation
//                    .addMember("class", "$S", clazz);
//                annotationBuilder
//                    .addMember(innerSearchRule.requiredAttribute, "$L", innerAnnotation.build());
//            }
//
//            for (ElementAttribute attribute : innerSearchRule.attributes) {
//                innerAnnotation
//                    .addMember(attribute.getAttributeName(), "$S", attribute.getAttributeValue());
//                annotationBuilder
//                    .addMember(innerSearchRule.requiredAttribute, "$L", innerAnnotation.build());
//            }
//        }

        return annotationBuilder.build();
    }

    private String createXPathSelector(SearchRule searchRule, String element) {
        StringBuilder xPathSelector = new StringBuilder();
//        xPathSelector.append("//").append(searchRule.tag).append("[");

        appendClassesToXPath(searchRule, xPathSelector);
        appendAttributesToXPath(searchRule, xPathSelector);

        if ("text".equals(searchRule.requiredAttribute)) {
            xPathSelector.append("text()");
        } else {
            xPathSelector.append("@").append(searchRule.requiredAttribute);
        }

        xPathSelector.append("='").append(element).append("']");

        return xPathSelector.toString();
    }

    private void appendClassesToXPath(SearchRule searchRule, StringBuilder xPathSelector) {
//        if (!searchRule.classesAreEmpty()) {
//            xPathSelector.append("@class='");
//            searchRule.classes.forEach(clazz -> xPathSelector.append(clazz).append(" "));
//            xPathSelector.deleteCharAt(xPathSelector.lastIndexOf(" "));
//            xPathSelector.append("' and ");
//        }
    }

    private void appendAttributesToXPath(SearchRule searchRule, StringBuilder xPathSelector) {
//        if (!searchRule.attributesAreEmpty()) {
//            searchRule.attributes.forEach(elementAttribute -> xPathSelector.append("@")
//                .append(elementAttribute.getAttributeName())
//                .append("='").append(elementAttribute.getAttributeValue()).append("'")
//                .append(" and "));
//        }
    }

    public static Set<String> getSupportedTypes() {
        return annotationMap.keySet();
    }
}