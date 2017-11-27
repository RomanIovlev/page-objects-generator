package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.jsoup.select.Elements;

public class PageClass extends JavaPoetClass {

    private String url;
    private List<SearchRule> searchRules;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public PageClass(String outputDir, String packageName, String url, List<SearchRule> searchRules,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        super(outputDir, packageName);
        this.url = url;
        this.searchRules = searchRules;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
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
    public List<JavaField> getFieldsList() throws IOException {
        List<JavaField> fields = new ArrayList<>();

        for (SearchRule searchRule : searchRules) {
            Elements elements = searchRule.extractElementsFromWebSite(url);
            if ((elements != null) && (
                elements.size() == 1)) {
                fields.add(new FormField(searchRule, elements.first(), typesContainer,
                    xpathToCssTransformation));
            }
        }

        return fields;
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
