package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.openqa.selenium.support.FindBy;

public class FieldsBuilder implements IFieldsBuilder {

    private Class elementClass;
	private Class annotationClass;

    public FieldsBuilder(Class elementClass, Class annotationClass) {
        this.elementClass = elementClass;
		this.annotationClass = annotationClass;
    }

    @Override
    public List<FieldSpec> buildField(SearchRule searchRule, String url) throws IOException {
        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elementsRequiredValues = searchRule.getRequiredValueFromFoundElement(url);

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
			return AnnotationSpec.builder(annotationClass)
				.addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue))
				.build();
		} else {
			return AnnotationSpec.builder(annotationClass)
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
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(annotationClass);
		String searchAttribute = searchRule.getRequiredAttribute();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
			String annotationElementName = innerSearchRule.getRequiredAttribute();

			innerSearchRule.setRequiredAttribute(searchAttribute);

            if (innerSearchRule.getCss() != null) {
                innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
					innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
            } else {
				innerAnnotation.addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
					innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
			}

			annotationBuilder.addMember(annotationElementName, "$L", innerAnnotation.build());
		}

        return annotationBuilder.build();
    }

}