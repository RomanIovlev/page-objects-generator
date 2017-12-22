package com.epam.page.object.generator.container;

import static org.junit.Assert.*;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import java.util.Map;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class SupportedTypesContainerTest {

    private SupportedTypesContainer container = new SupportedTypesContainer();

    @Test
    public void addSupportedType() {
        Map<String, ClassAndAnnotationPair> supportedTypesMap = container.getSupportedTypesMap();
        assertEquals(24, supportedTypesMap.size());

        container.addSupportedType("test", new ClassAndAnnotationPair(Form.class, FindBy.class));
        assertEquals(25, supportedTypesMap.size());
    }

}