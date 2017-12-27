package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import java.util.List;

/**
 * Implementing this interface allows to get {@link JavaClass} which can be generated to .java
 * source file.<br/>
 *
 * After creating a new class you need to add build() method in {@link JavaClassBuilder} with you
 * class as a parameter.
 *
 * @see SiteClass
 * @see PageClass
 * @see FormClass
 */
public interface JavaClassBuildable {

    /**
     * Create {@link JavaClass} which will be used for generating .java source file.
     *
     * @param javaClassBuilder {@link JavaClassBuilder} which describes how to build {@link
     * JavaClass} from {@link JavaClassBuildable}
     * @return {@link JavaClass}
     */
    JavaClass accept(JavaClassBuilder javaClassBuilder);

    /**
     * This method describes how to build list of {@link JavaField} for {@link JavaClass}.
     *
     * @param packageName name of the package where will be must placed all .java source files
     * @return list of {@link JavaField}
     */
    List<JavaField> buildFields(String packageName);

    /**
     * This method describes how to build {@link JavaAnnotation} for {@link JavaClass}.
     *
     * @return {@link JavaClass}
     */
    JavaAnnotation buildAnnotation();
}