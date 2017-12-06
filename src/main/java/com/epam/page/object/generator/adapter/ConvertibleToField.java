package com.epam.page.object.generator.adapter;

public interface ConvertibleToField {

    IJavaField accept(JavaFieldVisitor javaFieldVisitor);
}
