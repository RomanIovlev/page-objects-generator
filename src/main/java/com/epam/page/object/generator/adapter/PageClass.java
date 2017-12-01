package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;


import com.epam.page.object.generator.adapter.searchRuleFields.CommonSearchRuleField;
import com.epam.page.object.generator.adapter.searchRuleFields.ComplexSearchRuleField;
import com.epam.page.object.generator.adapter.searchRuleFields.FormSearchRuleField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class PageClass implements JavaClass {

    private String packageName;
    private WebPage webPage;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public PageClass(String packageName,
                     WebPage webPage,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        this.packageName = packageName;
        this.webPage = webPage;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getClassName() {
        return firstLetterUp(splitCamelCase(webPage.getTitle()));
    }

    @Override
    public Class getSuperClass() {
        return com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
    }

    @Override
    public JavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public List<JavaField> getFieldsList() {
        List<JavaField> fields = new ArrayList<>();

        for (SearchRule searchRule : webPage.getSearchRules()) {
            Element element = webPage.extractElement(searchRule);
            if (searchRule instanceof CommonSearchRule){
                fields.add(new CommonSearchRuleField((CommonSearchRule) searchRule, element, typesContainer));
            } else if (searchRule instanceof ComplexSearchRule){
                fields.add(new ComplexSearchRuleField((ComplexSearchRule) searchRule, element, typesContainer));
            }
            else if (searchRule instanceof FormSearchRule){
                fields.add(new FormSearchRuleField((FormSearchRule) searchRule, element, packageName, typesContainer));
            }

//
//            Elements elements = webPage.getDocument().getAllElements();
//
//            if (elements != null && elements.size() == 1) {
//                if (searchRule instanceof CommonSearchRule) {
//                    fields.add(
//                        new CommonSearchRuleField((CommonSearchRule) searchRule,
//                            elements.first(),
//                            getPackageName(),
//                            typesContainer));
//                } else if(searchRule instanceof ComplexSearchRule){
//                    new ComplexSearchRuleField()
//                }
//
//                fields.add(new SearchRuleField(searchRule, elements.first(), getPackageName(),
//                    typesContainer,
//                    xpathToCssTransformation));
//            }
        }

        return fields;
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
