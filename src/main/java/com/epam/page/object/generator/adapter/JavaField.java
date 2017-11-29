package com.epam.page.object.generator.adapter;

import java.io.IOException;
import javax.lang.model.element.Modifier;

public interface JavaField {

    String getFieldClassName();

    JavaAnnotation getAnnotation();

    String getFieldName() throws IOException;

    Modifier[] getModifiers();
}
