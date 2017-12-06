package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import java.util.List;

public interface IJavaAnnotation {

    Class getAnnotationClass();

    List<AnnotationMember> getAnnotationMembers()
        throws XpathToCssTransformerException;

    class AnnotationMember {

        String name;
        String format;
        String arg;
        IJavaAnnotation annotation;

        public AnnotationMember(String name, String format, String arg) {
            this.name = name;
            this.format = format;
            this.arg = arg;
        }

        public AnnotationMember(String name, String format, IJavaAnnotation annotation) {
            this.name = name;
            this.format = format;
            this.annotation = annotation;
        }

    }
}
