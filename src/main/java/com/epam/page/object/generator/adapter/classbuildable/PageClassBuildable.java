package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.annotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.fileds.IJavaField;
import com.epam.page.object.generator.adapter.classes.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.models.WebPage;
import com.epam.page.object.generator.models.webgroup.WebElementGroup;
import java.util.ArrayList;
import java.util.List;

public class PageClassBuildable implements JavaClassBuildable {

    private WebPage webPage;
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;

    public PageClassBuildable(WebPage webPage,
                              WebElementGroupFieldBuilder webElementGroupFieldBuilder) {
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
    public List<IJavaField> buildFields(String packageName) {
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
    public IJavaAnnotation buildAnnotation() {
        return null;
    }
}
