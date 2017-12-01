package com.epam.page.object.generator.adapter.searchRuleFields;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.FormSearchRuleAnnotation;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class FormSearchRuleField implements JavaField {

    private FormSearchRule searchRule;
    private Element element;
    private String packageName;
    private SupportedTypesContainer typesContainer;

    public FormSearchRuleField(FormSearchRule searchRule, Element element, String packageName,
                               SupportedTypesContainer typesContainer) {
        this.searchRule = searchRule;
        this.element = element;
        this.packageName = packageName;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getFieldClassName() {
        return packageName.substring(0, packageName.length() - ".page".length())
            + ".form" + "." + searchRule.getSection();
    }

    @Override
    public JavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap().get(searchRule.getType())
            .getElementAnnotation();
        return new FormSearchRuleAnnotation(searchRule, fieldAnnotationClass);
    }

    @Override
    public String getFieldName() {
        return firstLetterDown(splitCamelCase(searchRule.getSection()));
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
