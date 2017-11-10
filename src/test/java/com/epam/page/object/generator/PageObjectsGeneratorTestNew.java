package com.epam.page.object.generator;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.LocatorExistenceValidator;
import com.epam.page.object.generator.validators.ValidationContext;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import com.epam.page.object.generator.adapter.JavaPoetAdapter;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageObjectsGeneratorTestNew {

    private static final String TEST_PACKAGE = "testPackage";

    @Mock
    private JsonRuleMapper parser;

    @Mock
    private JavaPoetAdapter javaPoetAdapter;

    @Mock
    private ValidatorsStarter validator;

    private SearchRule searchRule = new SearchRule();
    private SearchRule invalidSearchRule =
        new SearchRule("button", "req", null, null, null, null);

    private List<SearchRule> searchRules = Lists.newArrayList(searchRule);

    private String outputDir = "";

    private List<String> urls = Lists.newArrayList("https://www.google.com");

    private ValidationContext validationContext = new ValidationContext(searchRules, urls);

    private PageObjectsGenerator sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(parser.getRulesFromJSON()).thenReturn(searchRules);
        when(validator.validate(anyList(), anyList())).thenReturn(searchRules);
        when(validator.getValidationContext()).thenReturn(validationContext);

        sut = new PageObjectsGenerator(parser, validator, javaPoetAdapter, outputDir, urls,
            TEST_PACKAGE);
    }

    @Test
    public void generatePageObjects_filesGeneratedWithoutErrors()
        throws Exception {

        sut.generatePageObjects();

        verify(validator).validate(searchRules, urls);
        verify(javaPoetAdapter).writeFile(TEST_PACKAGE, outputDir, searchRules, urls);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenJSONValidationFails()
        throws Exception {
        doThrow(new ValidationException("some message")).when(validator)
            .validate(searchRules, urls);
        sut.generatePageObjects();
        verifyZeroInteractions(javaPoetAdapter);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenWebValidationFailsWithTrueForceGenerateFile()
        throws Exception {
        validationContext.addValidationResult(
            new ValidationResult(false, new LocatorExistenceValidator(), invalidSearchRule));
        sut.setForceGenerateFile(true);

        sut.generatePageObjects();
        verify(javaPoetAdapter).writeFile(TEST_PACKAGE, outputDir, searchRules, urls);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenWebValidationFailsWithFlaseForceGenerateFile()
        throws Exception {
        validationContext.addValidationResult(
            new ValidationResult(false, new LocatorExistenceValidator(), invalidSearchRule));
        sut.setForceGenerateFile(false);

        sut.generatePageObjects();
        verifyZeroInteractions(javaPoetAdapter);
    }

}