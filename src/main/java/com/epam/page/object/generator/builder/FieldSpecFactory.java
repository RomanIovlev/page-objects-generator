package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;

public class FieldSpecFactory {

    private Class elementClass;

    private String elementsRequiredValue;

    public FieldSpecFactory(Class elementClass) {
        this.elementClass = elementClass;
    }

    public FieldSpec build(AnnotationSpec annotationSpec){
        FieldSpec.Builder field = FieldSpec
            .builder(elementClass, splitCamelCase(elementsRequiredValue))
            .addModifiers(Modifier.PUBLIC);

        field.addAnnotation(annotationSpec);

        return field.build();
    }

	public void setElementsRequiredValue(String elementsRequiredValue) {
		this.elementsRequiredValue = elementsRequiredValue;
	}

}