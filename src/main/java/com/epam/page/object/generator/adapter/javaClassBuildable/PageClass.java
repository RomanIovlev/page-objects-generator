package com.epam.page.object.generator.adapter.javaClassBuildable;

import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import java.util.ArrayList;
import java.util.List;

public class PageClass implements JavaClassBuildable {

    private WebPage webPage;
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;

    public PageClass(WebPage webPage, WebElementGroupFieldBuilder webElementGroupFieldBuilder) {
        this.webPage = webPage;
        this.webElementGroupFieldBuilder = webElementGroupFieldBuilder;
    }

    public String getTitle() {
        return webPage.getTitle();
    }

    @Override
    public IJavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }

    @Override
    public List<IJavaField> getFields(String packageName) {
        List<IJavaField> fields = new ArrayList<>();

        for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
            if (webElementGroup.isInvalid()) {
                continue;
            }
            fields.addAll(webElementGroup.accept(webElementGroupFieldBuilder, packageName));
        }

        return fields;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return null;
    }
}
