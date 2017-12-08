package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import static org.junit.Assert.*;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.validators.ValidationResult;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class RootExistenceValidatorTest {

    private RootExistenceValidator sut = new RootExistenceValidator();
    private XpathToCssTransformer transformer = new XpathToCssTransformer();

    private ComplexInnerSearchRule rootRule = new ComplexInnerSearchRule("text",
        "root", new Selector("css", ".myClass"), transformer);
    private ComplexInnerSearchRule listRule = new ComplexInnerSearchRule(null,
        "list", new Selector("xpath", "//div"), transformer);

    private ComplexSearchRule validSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        Lists.newArrayList(rootRule, listRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    private ComplexSearchRule invalidSearchRuleWithoutRootTitle = new ComplexSearchRule(
        SearchRuleType.DROPDOWN, Lists.newArrayList(listRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    @Test
    public void visit_SuccessTest(){
        ValidationResult actualValidationResult = sut.visit(validSearchRule);
        assertTrue(actualValidationResult.isValid());
    }

    @Test
    public void visit_FailedSearchRuleWithoutRootTitle(){
        ValidationResult actualValidationResult = sut.visit(invalidSearchRuleWithoutRootTitle);
        assertFalse(actualValidationResult.isValid());
    }
}