package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static com.epam.page.object.generator.utils.URLUtils.getUrlWithoutDomain;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.page.object.generator.builder.JavaPoetClass.AnnotationMember;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SiteFieldAnnotation implements JavaAnnotation {
    private String url;

    public SiteFieldAnnotation(String url) {
        this.url = url;
    }

    @Override
    public Class getAnnotationClass() {
        return JPage.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() {
        List<AnnotationMember> pageAnnotations = new ArrayList<>();
        try {
            pageAnnotations.add(new AnnotationMember("url", "$S", getUrlWithoutDomain(url)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            pageAnnotations.add(new AnnotationMember("title", "$S", getPageTitle(url)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pageAnnotations;
    }
}
