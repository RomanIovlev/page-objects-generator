package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.URLUtils.getDomainName;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class SiteClass extends JavaPoetClass {

    private List<String> urls;

    public SiteClass(List<String> urls, String outputDir, String packageName) {
        super(outputDir, packageName);
        this.urls = urls;
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
        try {
            return new SiteAnnotation(getDomainName(urls));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<JavaField> getFieldsList() {
        return urls.stream().map(url -> new SiteField(getPackageName(), url))
            .collect(Collectors.toList());
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
