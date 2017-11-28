package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.jsoup.select.Elements;
public class PageClass extends JavaPoetClass {

    private WebPage webPage;
    private List<SearchRule> searchRules;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public PageClass(String outputDir, String packageName, WebPage webPage,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        super(outputDir, packageName);
        this.webPage = webPage;
        this.searchRules = webPage.getValidSearchRulesOfCurrentWebPage();
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public String getClassName() throws IOException {
        return firstLetterUp(splitCamelCase(webPage.getTitle()));
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
            Elements elements = searchRule.extractElementsFromElement(webPage.getDocument());
            if (
//                (elements != null) &&
                    (
                elements.size() == 1)) {
                fields.add(new SearchRuleField(searchRule, elements.first(), getPackageName(),
                    typesContainer,
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
