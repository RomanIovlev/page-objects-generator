package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.squareup.javapoet.ClassName;
import java.io.IOException;
import javax.lang.model.element.Modifier;

public class SiteField implements JavaField {

    private String packageName;
    private String url;
    private String titleName;

    public SiteField(String packageName, String url) {
        this.packageName = packageName;
        this.url = url;

        try {
            titleName = splitCamelCase(getPageTitle(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class getFieldClass() {
        return null;
    }

    @Override
    public ClassName getFieldClassName() {
        return getPageClassName(packageName, firstLetterUp(titleName));
    }

    @Override
    public JavaAnnotation getAnnotation() {
        return new SiteFieldAnnotation(url);
    }

    @Override
    public String getFieldName() {
        return firstLetterDown(titleName);
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC, STATIC};
    }

    private ClassName getPageClassName(String packageName, String pageClassName) {
        return ClassName
            .get(packageName.substring(0, packageName.length() - 5) + ".page", pageClassName);
    }
}
