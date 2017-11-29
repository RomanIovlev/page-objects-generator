package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class SearchRuleField implements JavaField {

    private SearchRule searchRule;
    private Element element;
    private String packageName;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public SearchRuleField(SearchRule searchRule,
                           Element element,
                           String packageName,
                           SupportedTypesContainer typesContainer,
                           XpathToCssTransformation xpathToCssTransformation) {
        this.searchRule = searchRule;
        this.element = element;
        this.packageName = packageName;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public String getFieldClassName() {
        if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            return packageName.substring(0, packageName.length() - ".page".length())
                + ".form" + "." + searchRule.getSection();
        }
        return typesContainer
            .getSupportedTypesMap().get(searchRule.getType()).getElementClass().getName();
    }

    @Override
    public JavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap().get(searchRule.getType())
            .getElementAnnotation();
        if (SearchRuleTypeGroups.isCommonType(searchRule)) {
            return new CommonAnnotation(searchRule, element,
                fieldAnnotationClass, xpathToCssTransformation);
        } else if (SearchRuleTypeGroups.isComplexType(searchRule)) {
            return new ComplexAnnotation(searchRule, element, xpathToCssTransformation);
        } else if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            return new FormOrSectionAnnotation(searchRule,
                fieldAnnotationClass);
        } else {
            //This type of search rule does not supported
            throw new UnsupportedOperationException(searchRule.getType()
                + " search rule type does not supported");
        }
    }

    @Override
    public String getFieldName() throws IOException {
        if (SearchRuleTypeGroups.isCommonType(searchRule)) {
            return firstLetterDown(
                splitCamelCase(searchRule.getRequiredValueFromFoundElement(element)));
        }
        if (SearchRuleTypeGroups.isComplexType(searchRule)) {
            if (searchRule.getRootInnerRule().isPresent()) {
                return firstLetterDown(splitCamelCase(searchRule.getRootInnerRule().get()
                    .getRequiredValueFromFoundElement(element)));
            }
            return firstLetterDown(splitCamelCase(searchRule.getType()));
        }
        if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            return firstLetterDown(splitCamelCase(searchRule.getSection()));
        }
        //This type of search rule does not supported
        throw new UnsupportedOperationException(searchRule.getType()
            + " search rule type does not supported");
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
