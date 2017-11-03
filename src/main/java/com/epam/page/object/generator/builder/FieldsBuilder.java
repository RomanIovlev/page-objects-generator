package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldsBuilder implements IFieldsBuilder {

    private FieldSpecFactory fieldSpecFactory;
    private FieldAnnotationFactory fieldAnnotationFactory;

    public FieldsBuilder(FieldSpecFactory fieldSpecFactory, FieldAnnotationFactory fieldAnnotationFactory) {
		this.fieldSpecFactory = fieldSpecFactory;
        this.fieldAnnotationFactory = fieldAnnotationFactory;
    }

    @Override
    public List<FieldSpec> buildField(SearchRule searchRule, String url ) throws IOException {
        List<FieldSpec> fields = new ArrayList<>();
        List<String> elementsRequiredValues = searchRule.getRequiredValueFromFoundElement(url);

        for (String elementsRequiredValue : elementsRequiredValues) {
            fieldSpecFactory.setElementsRequiredValue(elementsRequiredValue);
            fields.add(fieldSpecFactory.build(fieldAnnotationFactory.buildAnnotation(searchRule,elementsRequiredValue,url)));
        }

        return fields;
    }

}