package com.epam.page.object.generator.builder;


import static org.junit.Assert.assertTrue;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import javax.lang.model.element.Modifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FieldSpecFactoryTest {

    private Class elementClass;
    private String elementsRequiredValue;
    private SearchRule searchRule;
    private FieldSpecFactory sut;

    @Mock
    FieldSpec.Builder builder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(FieldSpecFactoryTest.class);

        elementClass = Button.class;
        elementsRequiredValue = "name";

        searchRule = new SearchRule();
        searchRule.setRequiredAttribute("name");
        searchRule.setType("button");
        searchRule.setXpath("//input[@type='submit']");

        sut = new FieldSpecFactory(elementClass);
        sut.setElementsRequiredValue(elementsRequiredValue);
    }

    @Test
    public void buildTest_success() {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(elementClass);

        AnnotationSpec spec = annotationBuilder.build();

        FieldSpec fieldSpec = sut.build(spec);

        assertTrue(fieldSpec.modifiers.size() == 1);
        assertTrue(fieldSpec.hasModifier(Modifier.PUBLIC));
        assertTrue(fieldSpec.annotations.size() == 1 && fieldSpec.annotations.get(0).equals(spec));
    }

}
