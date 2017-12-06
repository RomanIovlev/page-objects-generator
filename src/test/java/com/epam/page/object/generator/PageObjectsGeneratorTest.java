package com.epam.page.object.generator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.WebValidators;
import com.epam.page.object.generator.validators.oldValidators.ValidationContext;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageObjectsGeneratorTest {

    private static final String OUTPUT_DIR = "testdir";
    private static final String TEST_PACKAGE = "testPackage";

    @Mock
    private RawSearchRuleMapper parser;

    @Mock
    private JsonSchemaValidator validator;

    @Mock
    private TypeTransformer transformer;

    @Mock
    private ValidationChecker checker;

    @Mock
    private JavaFileWriter javaFileWriter;

    @Mock
    private JsonValidators jsonValidators;

    @Mock
    private WebValidators webValidators;

    @Mock
    private JavaClassBuilder javaClassBuilder;

    @Mock
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;

    @Mock
    private WebPagesBuilder builder;


    private SearchRule searchRule = new SearchRule("type", "uniqueness", "title", "css", "xpath",
        null);

    private SearchRule invalidSearchRule =
        new SearchRule("button", "req", null, null, null, null);

    private List<SearchRule> searchRules = Lists.newArrayList(searchRule);


    private List<String> urls = Collections.singletonList("http://google.com");

    private ValidationContext validationContext = new ValidationContext(searchRules, urls);

    private PageObjectsGenerator sut;

    private String exceptionMessage = "bla-bla-bla";


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

//        when(parser.getRawSearchRuleList(anyString())).thenReturn(searchRules);
//        when(jsonValidators.validate(anyList())).thenReturn(searchRules);
//        when(jsonValidators.getValidationContext()).thenReturn(validationContext);

        sut = new PageObjectsGenerator(parser, validator, transformer, checker, jsonValidators, webValidators,
            javaClassBuilder, webElementGroupFieldBuilder, javaFileWriter, builder);
    }

//    @Test
//    public void generatePageObjects_filesGeneratedWithoutErrors()
//        throws Exception {
//
//        sut.generatePageObjects();
//
//        verify(jsonValidators).visit(searchRules, urls);
//        verify(javaFileWriter).writeFiles(eq(OUTPUT_DIR), eq(TEST_PACKAGE), anyListOf(WebPage.class));
//    }
//
//    @Test(expected = ValidationException.class)
//    public void generatePageObjects_ErrorWhenJSONValidationFails()
//        throws Exception {
//        doThrow(new ValidationException("some message")).when(jsonValidators)
//            .visit(searchRules, urls);
//        sut.generatePageObjects();
//        verifyZeroInteractions(javaFileWriter);
//    }
//
//    @Test(expected = ValidationException.class)
//    public void generatePageObjects_ErrorWhenWebValidationFailsWithTrueForceGenerateFile()
//        throws Exception {
//        validationContext.addValidationResult(
//            new ValidationResult(false, exceptionMessage, invalidSearchRule));
//        sut.setForceGenerateFile(true);
//
//        sut.generatePageObjects();
//    }
//
//    @Test(expected = ValidationException.class)
//    public void generatePageObjects_ErrorWhenWebValidationFailsWithFalseForceGenerateFile()
//        throws Exception {
//        validationContext.addValidationResult(
//            new ValidationResult(false, exceptionMessage, invalidSearchRule));
//        sut.setForceGenerateFile(false);
//
//        sut.generatePageObjects();
//        verifyZeroInteractions(javaFileWriter);
//    }

}