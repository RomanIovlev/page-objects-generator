package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.JavaPoetClass.AnnotationMember;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;

public class CommonAnnotation extends Annotation {

    private SearchRule searchRule;
    private Element element;
    private Class fieldAnnotationClass;
    private XpathToCssTransformation xpathToCssTransformation;

    public CommonAnnotation(SearchRule searchRule, Element element,
                            XpathToCssTransformation xpathToCssTransformation) {
        super(searchRule, element, xpathToCssTransformation);
    }

    public CommonAnnotation(SearchRule searchRule, Element element,
                            XpathToCssTransformation xpathToCssTransformation, Class fieldAnnotationClass) {
        super(searchRule, element, xpathToCssTransformation);
        this.fieldAnnotationClass = fieldAnnotationClass;
    }


    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException {
        String requiredValue = searchRule.getRequiredValueFromFoundElement(element);
        if ((requiredValue != null) && (!requiredValue.isEmpty())) {
            return Collections.singletonList(getAnnotationMemberFromRule(
                searchRule, element));
        }
        return null;
    }
}
