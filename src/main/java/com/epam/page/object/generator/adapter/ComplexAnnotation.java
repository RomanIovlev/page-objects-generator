package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.openqa.selenium.support.FindBy;

public class ComplexAnnotation extends Annotation {

    public ComplexAnnotation(SearchRule searchRule, Element element,
                             XpathToCssTransformation xpathToCssTransformation) {
        super(searchRule, element, xpathToCssTransformation);
    }

    @Override
    public Class getAnnotationClass() {
        return FindBy.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException {
        List<AnnotationMember> innerAnnotations = new ArrayList<>();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String requiredValue = innerSearchRule.getRequiredValueFromFoundElement(element);
            String annotationElementName = innerSearchRule.getTitle();
            if (requiredValue != null || innerSearchRule.getTitle() != null) {
                JavaAnnotation innerAnnotation = new CommonAnnotation(innerSearchRule, element,
                    FindBy.class, xpathToCssTransformation);

                innerAnnotations
                    .add(new AnnotationMember(annotationElementName, "$L", innerAnnotation));
            }
        }

        return innerAnnotations;
    }
}
