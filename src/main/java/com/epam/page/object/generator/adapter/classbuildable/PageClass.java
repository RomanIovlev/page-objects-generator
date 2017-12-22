package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageClass implements JavaClassBuildable {

    private WebPage webPage;
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;

    private final static Logger logger = LoggerFactory.getLogger(PageClass.class);

    public PageClass(WebPage webPage,
                     WebElementGroupFieldBuilder webElementGroupFieldBuilder) {
        this.webPage = webPage;
        this.webElementGroupFieldBuilder = webElementGroupFieldBuilder;
    }

    public String getTitle() {
        return webPage.getTitle();
    }

    @Override
    public JavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.build(this);
    }

    @Override
    public List<JavaField> buildFields(String packageName) {
        List<JavaField> fields = new ArrayList<>();

        for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
            if (webElementGroup.isInvalid()) {
                continue;
            }
            fields.addAll(webElementGroup.accept(webElementGroupFieldBuilder, packageName));
        }

        return fields;
    }

    @Override
    public JavaAnnotation buildAnnotation() {
        return null;
    }

    @Override
    public String toString() {
        return "PageClass with title " + webPage.getTitle();
    }
}
