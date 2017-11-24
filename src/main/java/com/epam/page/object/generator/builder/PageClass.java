package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.Modifier;

public class PageClass extends JavaPoetClass {

    String url;

    public PageClass(String outputDir, String packageName, String url) {
        super(outputDir, packageName);
        this.url = url;
    }

    @Override
    public String getClassName() throws IOException {
        return firstLetterUp(splitCamelCase(getPageTitle(url)));
    }

    @Override
    public Class getSuperClass() {
        return WebPage.class;
    }

    @Override
    public JavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public List<JavaField> getFieldsList() {
        return null;
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[0];
    }
}
