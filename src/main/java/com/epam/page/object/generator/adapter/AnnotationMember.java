package com.epam.page.object.generator.adapter;

public class AnnotationMember {

    private String name;
    private String format;
    private String arg;
    private JavaAnnotation annotation;

    public AnnotationMember(String name, String format, String arg) {
        this.name = name;
        this.format = format;
        this.arg = arg;
    }

    public AnnotationMember(String name, String format, JavaAnnotation annotation) {
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

    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "AnnotationMember{" +
            "name='" + name + '\'' +
            ", format='" + format + '\'' +
            ", arg='" + arg + '\'' +
            ", annotation=" + annotation +
            '}';
    }
}
