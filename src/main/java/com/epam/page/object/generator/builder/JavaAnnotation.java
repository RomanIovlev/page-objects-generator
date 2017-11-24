package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.builder.JavaPoetClass.AnnotationMember;
import java.util.List;

public interface JavaAnnotation {
    Class getAnnotationClass();
    List<AnnotationMember> getAnnotationMembers();
}
