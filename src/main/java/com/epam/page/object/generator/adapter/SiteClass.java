package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.URLUtils.getDomainName;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class SiteClass implements JavaClass {

    private List<String> urls;
    private String packageName;

    public SiteClass(String packageName, List<String> urls) {
        this.packageName = packageName;
        this.urls = urls;
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
