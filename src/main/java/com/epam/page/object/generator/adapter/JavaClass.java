package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * JavaClass is a struct class that contains necessary attributes for generation of .java files.
 */
public class JavaClass {

    private String packageName;
    private String className;
    private Class superClass;
    private JavaAnnotation annotation;
    private List<JavaField> fields;
    private Modifier modifier;

    /**
     * @param packageName name of package where generated .java file will be placed
     * @param className is name this class.
     * @param superClass is a parent {@link JavaClass} not obligatory
     * @param annotation is {@link JavaAnnotation} that belongs this class.
     * @param fields List of {@link JavaField}
     * @param modifier
     */
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

    /**
     *
     * @return package name.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * @return name of this class.
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     * @return super {@link JavaClass}
     */
    public Class getSuperClass() {
        return superClass;
    }

    /**
     *
     * @return {@link JavaAnnotation}
     */
    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    /**
     *
     * @return {@link JavaField}
     */
    public List<JavaField> getFieldsList() {
        return fields;
    }

    /**
     *
     * @return class modifier.
     */
    public Modifier getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return "JavaClass{" +
            "packageName='" + packageName + '\'' +
            ", className='" + className + '\'' +
            ", superClass=" + superClass +
            '}';
    }
}
