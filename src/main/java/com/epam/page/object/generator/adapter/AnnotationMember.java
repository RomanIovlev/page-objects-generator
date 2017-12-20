package com.epam.page.object.generator.adapter;

/**
 * AnnotationMember is a struct class that describes internal state of {@link JavaAnnotation}
 */
public class AnnotationMember {

    private String name;
    private String format;
    private String arg;
    private JavaAnnotation annotation;

    /**
     * @param name of attribute of Annotation
     * @param format for this constructor indicates that this annotation don't have any other inside.
     * @param arg value of Annotation
     */
    public AnnotationMember(String name, String format, String arg) {
        this.name = name;
        this.format = format;
        this.arg = arg;
    }

    /**
     * @param name of attribute of Annotation
     * @param format for this constructor indicates that this annotation has some other inside.
     * @param annotation nested annotation.
     */
    public AnnotationMember(String name, String format, JavaAnnotation annotation) {
        this.name = name;
        this.format = format;
        this.annotation = annotation;
    }

    /**
     *
     * @return name of annotation's attribute
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return format of Annotation
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @return value of Annotation
     */
    public String getArg() {
        return arg;
    }

    /**
     *
     * @return nested Annotation
     * aware can be null
     */
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
