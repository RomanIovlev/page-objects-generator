package com.epam.page.object.generator.model;

/**
 * Forms pairs of class from JDI as basis of POG web element class and its annotations
 */
public class ClassAndAnnotationPair {

    /**
     * Class for specific web element
     */
    private Class<?> elementClass;
    /**
     * Corresponding annotation
     */
    private Class<?> elementAnnotation;

    public ClassAndAnnotationPair(Class<?> elementClass, Class<?> elementAnnotation) {
        this.elementClass = elementClass;
        this.elementAnnotation = elementAnnotation;
    }

    public Class<?> getElementClass() {
        return elementClass;
    }

    public Class<?> getElementAnnotation() {
        return elementAnnotation;
    }

}