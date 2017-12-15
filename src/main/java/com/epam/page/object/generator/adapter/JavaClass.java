package com.epam.page.object.generator.adapter;

import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaClass {

    private String packageName;
    private String className;
    private Class superClass;
    private JavaAnnotation annotation;
    private List<JavaField> fields;
    private Modifier modifier;

    public JavaClass(String packageName, String className, Class superClass,
                     JavaAnnotation annotation,
                     List<JavaField> fields, Modifier modifier) {
        this.packageName = packageName;
        this.className = className;
        this.superClass = superClass;
        this.annotation = annotation;
        this.fields = fields;
        this.modifier = modifier;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public Class getSuperClass() {
        return superClass;
    }

    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    public List<JavaField> getFieldsList() {
        return fields;
    }

    public Modifier getModifier() {
        return modifier;
    }
}