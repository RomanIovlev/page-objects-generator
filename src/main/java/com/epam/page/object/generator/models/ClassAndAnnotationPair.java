package com.epam.page.object.generator.models;

public class ClassAndAnnotationPair {

    private Class elementClass;
    private Class elementAnnotation;

    public ClassAndAnnotationPair(Class elementClass, Class elementAnnotation) {
        this.elementClass = elementClass;
        this.elementAnnotation = elementAnnotation;
    }

    public Class getElementClass() {
        return elementClass;
    }

    public Class getElementAnnotation() {
        return elementAnnotation;
    }

}