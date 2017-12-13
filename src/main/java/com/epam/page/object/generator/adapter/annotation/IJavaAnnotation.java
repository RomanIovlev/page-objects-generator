package com.epam.page.object.generator.adapter.annotation;

import java.util.List;

public interface IJavaAnnotation {

    Class getAnnotationClass();

    List<AnnotationMember> getAnnotationMembers();
}
