package com.epam.page.object.generator.adapter;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.page.object.generator.model.WebPage;
import java.util.ArrayList;
import java.util.List;

public class SiteFieldAnnotation implements IJavaAnnotation {

    private WebPage webPage;

    public SiteFieldAnnotation(WebPage webPage) {
        this.webPage = webPage;
    }

    @Override
    public Class getAnnotationClass() {
        return JPage.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() {
        List<AnnotationMember> pageAnnotations = new ArrayList<>();
        pageAnnotations
            .add(new AnnotationMember("webPage", "$S", webPage.getUrlWithoutDomain()));

        pageAnnotations.add(new AnnotationMember("title", "$S", webPage.getTitle()));

        return pageAnnotations;
    }
}
