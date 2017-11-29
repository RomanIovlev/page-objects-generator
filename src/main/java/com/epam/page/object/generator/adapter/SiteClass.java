package com.epam.page.object.generator.adapter;

import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.model.WebPage;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class SiteClass implements JavaClass {

    private List<WebPage> webPages;
    private String packageName;

    public SiteClass(String packageName, List<WebPage> webPages) {
        this.packageName = packageName;
        this.webPages = webPages;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getClassName() {
        return "Site";
    }

    @Override
    public Class getSuperClass() {
        return com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;
    }

    @Override
    public JavaAnnotation getAnnotation() {
        return new SiteAnnotation(webPages.get(0).getDomainName());
    }

    @Override
    public List<JavaField> getFieldsList() {
        return webPages.stream().map(webPage -> new SiteField(getPackageName(), webPage))
            .collect(Collectors.toList());
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
