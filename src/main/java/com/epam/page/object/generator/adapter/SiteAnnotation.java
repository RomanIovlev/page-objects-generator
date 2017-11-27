package com.epam.page.object.generator.adapter;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import java.util.Collections;
import java.util.List;

public class SiteAnnotation implements JavaAnnotation {

    private String domainUrl;

    public SiteAnnotation(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    @Override
    public Class getAnnotationClass() {
        return JSite.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() {
        return Collections.singletonList(new AnnotationMember("value", "$S", domainUrl));
    }
}
