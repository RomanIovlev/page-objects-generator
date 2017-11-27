package com.epam.page.object.generator.adapter;


import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.page.object.generator.model.WebPage;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class SiteClass extends JavaPoetClass {

    private final List<WebPage> webPages;

    public SiteClass(String outputDir, String packageName, List<WebPage> webPages) {
        super(outputDir, packageName);
        this.webPages = webPages;
    }

    @Override
    public String getClassName() {
        return "Site";
    }

    @Override
    public Class getSuperClass() {
        return WebSite.class;
    }

    @Override
    public JavaAnnotation getAnnotation() {
        return new SiteAnnotation(webPages.get(0).getDomainName());
    }

    @Override
    public List<JavaField> getFieldsList() {
        return webPages.stream().map(wp -> new SiteField(getPackageName(), wp))
            .collect(Collectors.toList());
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
