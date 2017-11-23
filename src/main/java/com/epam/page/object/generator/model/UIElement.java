package com.epam.page.object.generator.model;

public class UIElement {
    private Class uiClass;
    private Class uiAnnotation;

    public UIElement(Class uiClass, Class uiAnnotation) {
        this.uiClass = uiClass;
        this.uiAnnotation = uiAnnotation;
    }

    public Class getUIClass() {
        return uiClass;
    }
    public Class getUIAnnotation() {
        return uiAnnotation;
    }

}