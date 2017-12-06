package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.page.object.generator.model.WebPage;
import javax.lang.model.element.Modifier;

public class SiteField implements IJavaField {

    private WebPage webPage;
    private String titleName;

    public SiteField(WebPage webPage) {
        this.webPage = webPage;
        titleName = splitCamelCase(webPage.getTitle());
    }

    @Override
    public String getFieldClassName() {
        return firstLetterUp(titleName);

    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return new SiteFieldAnnotation(webPage);
    }

    @Override
    public String getFieldName() {
        return firstLetterDown(titleName);
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC, STATIC};
    }
}
