package com.epam.page.object.generator;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.ValidationContext;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import com.epam.page.object.generator.writer.JavaFileWriter;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageObjectsGeneratorTest {

    private static final String TEST_PACKAGE = "testPackage";

    @Mock
    private JsonRuleMapper parser;

    @Mock
    private JavaFileWriter javaFileWriter;

    @Mock
    private ValidatorsStarter validatorsStarter;

    private SearchRule searchRule = new SearchRule();
    private SearchRule invalidSearchRule =
        new SearchRule("button", "req", null, null, null, null);

    private List<SearchRule> searchRules = Lists.newArrayList(searchRule);

    private String outputDir = "";

    private List<String> urls = Lists.newArrayList("https://www.google.com");

    private ValidationContext validationContext = new ValidationContext(searchRules, urls);

    private PageObjectsGenerator sut;

    private String exceptionMessage = "bla-bla-bla";



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(parser.getRulesFromJSON()).thenReturn(searchRules);
        when(validatorsStarter.validate(anyList(), anyList())).thenReturn(searchRules);
        when(validatorsStarter.getValidationContext()).thenReturn(validationContext);

        sut = new PageObjectsGenerator(parser, validatorsStarter, javaFileWriter, outputDir, urls,
            TEST_PACKAGE);
    }

    @Test
    public void generatePageObjects_filesGeneratedWithoutErrors()
        throws Exception {

        sut.generatePageObjects();

        verify(validatorsStarter).validate(searchRules, urls);
        verify(javaFileWriter).writeFiles(outputDir, TEST_PACKAGE, searchRules, urls);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenJSONValidationFails()
        throws Exception {
        doThrow(new ValidationException("some message")).when(validatorsStarter)
            .validate(searchRules, urls);
        sut.generatePageObjects();
        verifyZeroInteractions(javaFileWriter);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenWebValidationFailsWithTrueForceGenerateFile()
        throws Exception {
        validationContext.addValidationResult(
            new ValidationResult(false, exceptionMessage, invalidSearchRule));
        sut.setForceGenerateFile(true);

        sut.generatePageObjects();
        verify(javaFileWriter).writeFiles(TEST_PACKAGE, outputDir, searchRules, urls);
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_ErrorWhenWebValidationFailsWithFalseForceGenerateFile()
        throws Exception {
        validationContext.addValidationResult(
            new ValidationResult(false, exceptionMessage, invalidSearchRule));
        sut.setForceGenerateFile(false);

        sut.generatePageObjects();
        verifyZeroInteractions(javaFileWriter);
    }

}