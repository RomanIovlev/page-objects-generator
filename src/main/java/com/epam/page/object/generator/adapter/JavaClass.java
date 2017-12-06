package com.epam.page.object.generator.adapter;

import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaClass implements IJavaClass {

    private String packageName;
    private String className;
    private Class superClass;
    private IJavaAnnotation annotation;
    private List<IJavaField> fields;
    private Modifier modifier;

    public JavaClass(String packageName, String className, Class superClass,
                     IJavaAnnotation annotation,
                     List<IJavaField> fields, Modifier modifier) {
        this.packageName = packageName;
        this.className = className;
        this.superClass = superClass;
        this.annotation = annotation;
        this.fields = fields;
        this.modifier = modifier;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public Class getSuperClass() {
        return superClass;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return annotation;
    }

    @Override
    public List<IJavaField> getFieldsList() {
        return fields;
    }

    @Override
    public Modifier getModifier() {
        return modifier;
    }
}
