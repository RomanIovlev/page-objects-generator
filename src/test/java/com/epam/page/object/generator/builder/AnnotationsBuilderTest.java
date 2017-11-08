package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.squareup.javapoet.AnnotationSpec;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class AnnotationsBuilderTest {

    private Class annotationClass;
    private SearchRule searchRule = new SearchRule();
    private SearchRule searchRule1 = new SearchRule();
    private SearchRule searchRule2 = new SearchRule();
    private String elementsRequiredValue;
    private String url;
    private List<SearchRule> innerSearchRules = new ArrayList<>();

    private AnnotationsBuilder sut;

    @Before
    public void setUp() throws Exception {
        url = "https://www.google.com";
        annotationClass = Button.class;
        elementsRequiredValue = "button";
        searchRule.setRequiredAttribute("name");
        searchRule.setXpath("//input[@type='submit']");

        searchRule1.setRequiredAttribute("name");
        searchRule1.setXpath("//input[@type='submit']");

        searchRule2.setRequiredAttribute("name");
        searchRule2.setCss("css");

        innerSearchRules.add(searchRule1);
        innerSearchRules.add(searchRule2);

    }

    @Test
    public void buildCommonAnnotation_withCss() throws Exception {
        searchRule.setCss("css");
        AnnotationSpec expected = AnnotationSpec.builder(annotationClass)
            .addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue)).build();

        sut = new AnnotationsBuilder();
        Assert.assertEquals(expected,
            sut.buildCommonAnnotation(searchRule, elementsRequiredValue, annotationClass));
    }

    @Test
    public void buildCommonAnnotation_withoutCss() throws Exception {
        searchRule = XpathToCssTransformer.transformRule(searchRule);
        AnnotationSpec expected = AnnotationSpec.builder(annotationClass)
            .addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue)).build();

        sut = new AnnotationsBuilder();
        Assert.assertEquals(expected,
            sut.buildCommonAnnotation(searchRule, elementsRequiredValue, annotationClass));
    }

    @Test
    public void buildCommonAnnotation_withTextAttribute() throws Exception {
        searchRule.setRequiredAttribute("text");
        AnnotationSpec expected = AnnotationSpec.builder(annotationClass)
            .addMember("xpath", "$S", resultXpathSelector(searchRule, elementsRequiredValue))
            .build();

        sut = new AnnotationsBuilder();
        Assert.assertEquals(expected,
            sut.buildCommonAnnotation(searchRule, elementsRequiredValue, annotationClass));
    }

    @Test
    public void buildComplexAnnotation() throws Exception {
//        searchRule.setInnerSearchRules(innerSearchRules);
//        AnnotationSpec.Builder expected = AnnotationSpec.builder(annotationClass);
//        AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
//
//        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
//            if (innerSearchRule.getCss() != null) {
//                innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
//                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
//            } else {
//                innerAnnotation.addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
//                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
//            }
//
//            expected
//                .addMember(innerSearchRule.getRequiredAttribute(), "$L", innerAnnotation.build());
//        }
//
//        sut = new AnnotationsBuilder();
//        Assert.assertEquals(expected, sut.buildComplexAnnotation(searchRule, url, annotationClass));
    }

}