package com.epam.page.object.generator.adapter.javaAnnotations;

public class AnnotationMember {

    private String name;
    private String format;
    private String arg;
    private IJavaAnnotation annotation;

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

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public String getArg() {
        return arg;
    }

    public IJavaAnnotation getAnnotation() {
        return annotation;
    }
}
