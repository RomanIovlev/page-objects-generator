package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

public class ValidationContextTest {

    private SearchRule ruleWithCss =
        new SearchRule("type", "req", null, "css", null, null);
    private SearchRule ruleWithXpath =
        new SearchRule("type", "req", null, null, "//input", null);
    private SearchRule ruleNoLocator =
        new SearchRule("type", "req", null, null, null, null);

    private ValidationContext sut;

    @Before
    public void setUp() throws Exception {
        sut = new ValidationContext(
            Lists.newArrayList(
                ruleWithCss,
                ruleWithXpath,
                ruleNoLocator),
            null);
    }

    @Test
    public void getAllSearchRules() {

        assertEquals(3, sut.getAllSearchRules().size());
    }

    @Test
    public void getValidRules() {
        sut.addValidationResult(
            new ValidationResult(true, new LocatorExistenceValidator(), ruleWithCss));
        sut.addValidationResult(
            new ValidationResult(true, new LocatorExistenceValidator(), ruleWithXpath));
        sut.addValidationResult(
            new ValidationResult(false, new LocatorExistenceValidator(), ruleNoLocator));

        assertEquals(sut.getValidRules().size(), 2);
        assertTrue(sut.getValidRules().contains(ruleWithCss));
        assertTrue(sut.getValidRules().contains(ruleWithXpath));
    }
}