package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.JavaPoetClass.AnnotationMember;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import java.io.IOException;
import java.util.List;

public interface JavaAnnotation {

    Class getAnnotationClass();

    List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException;
}
