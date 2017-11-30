package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;

public class CommonAnnotation extends Annotation {

    private Class fieldAnnotationClass;

    public CommonAnnotation(CommonSearchRule searchRule, Element element,
                            Class fieldAnnotationClass,
                            XpathToCssTransformation xpathToCssTransformation) {
        super(searchRule, element, xpathToCssTransformation);
        this.fieldAnnotationClass = fieldAnnotationClass;
    }


    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws XpathToCssTransformerException {
        return Collections.singletonList(getAnnotationMemberFromRule(
            searchRule, element));
    }
}
