package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.builder.StringUtils.splitCamelCase;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;
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

public class FieldsBuilder implements IFieldsBuilder {

    private Class elementClass;

    private static Map<String, Class> annotationMap = new HashMap<>();

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

    public FieldsBuilder(Class elementClass) {
        this.elementClass = elementClass;
    }

    @Override
    public List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException {
        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elementsRequiredValues = searchRule.extractRequiredValuesFromFoundElements(url);

        for (String elementsRequiredValue : elementsRequiredValues) {
            FieldSpec.Builder field = FieldSpec
                .builder(elementClass, splitCamelCase(elementsRequiredValue))
                .addModifiers(Modifier.PUBLIC);

            field.addAnnotation(buildAnnotation(searchRule, elementsRequiredValue, url));

            abstractFields.add(field.build());
        }

        return abstractFields;
    }

    private AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue, String url) throws IOException {
        if (searchRule.getInnerSearchRules() == null) {
            return buildCommonAnnotation(searchRule, elementsRequiredValue);
        } else {
            return buildComplexAnnotation(searchRule, url);
        }
    }

    private AnnotationSpec buildCommonAnnotation(SearchRule searchRule, String elementsRequiredValue) {
		if (searchRule.getCss() != null) {
			return AnnotationSpec.builder(annotationMap.get(searchRule.getType().toLowerCase()))
				.addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue))
				.build();
		} else {
			return AnnotationSpec.builder(annotationMap.get(searchRule.getType().toLowerCase()))
				.addMember("xpath", "$S", resultXpathSelector(searchRule, elementsRequiredValue))
				.build();
		}
    }

    private String resultCssSelector(SearchRule searchRule, String elementsRequiredValue) {
		return searchRule.getCss() + "[" + searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
	}

	private String resultXpathSelector(SearchRule searchRule, String elementsRequiredValue) {
		String xpathWithoutCloseBracket= searchRule.getXpath().replace("]", "");

    	if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
			return xpathWithoutCloseBracket + " and @"
				+ searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
		} else {
			return xpathWithoutCloseBracket + " and text()='" + elementsRequiredValue + "']";
		}
	}

	private AnnotationSpec buildComplexAnnotation(SearchRule searchRule, String url) throws IOException {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(annotationMap.get(searchRule.getType().toLowerCase()));
		String searchAttribute = searchRule.getRequiredAttribute();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
			String annotationElementName = innerSearchRule.getRequiredAttribute();

			innerSearchRule.setRequiredAttribute(searchAttribute);

            if (innerSearchRule.getCss() != null) {
                innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
					innerSearchRule.extractRequiredValuesFromFoundElements(url).get(0)));
            } else {
				innerAnnotation.addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
					innerSearchRule.extractRequiredValuesFromFoundElements(url).get(0)));
			}

			annotationBuilder.addMember(annotationElementName, "$L", innerAnnotation.build());
		}

        return annotationBuilder.build();
    }

    public static Set<String> getSupportedTypes() {
        return annotationMap.keySet();
    }

}