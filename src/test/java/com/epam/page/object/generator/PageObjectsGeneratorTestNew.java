package com.epam.page.object.generator;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.google.common.collect.Lists;
import com.squareup.javapoet.TypeSpec;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class PageObjectsGeneratorTestNew {
	public static final String TEST_PACKAGE = "testPackage";
	@Mock
	JSONIntoRuleParser parser;
	@Mock
	JavaFileWriter fileWriter;
	@Mock
	SiteFieldSpecBuilder siteFieldSpecBuilder;
	SearchRule searchRule = new SearchRule();
	@Mock
	SearchRuleValidator validator;
	TypeSpec siteTypeSpec = TypeSpec.classBuilder("PageObjectsGeneratorTestNew").build();
	List<SearchRule> searchRules = Lists.newArrayList(searchRule);
	List<String> urls = Lists.newArrayList("some_url");
	private PageObjectsGenerator sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(parser.getRulesFromJSON()).thenReturn(searchRules);
		when(siteFieldSpecBuilder.build(urls, searchRules)).thenReturn(siteTypeSpec);
		sut = new PageObjectsGenerator(parser, fileWriter,
				siteFieldSpecBuilder, validator, urls, TEST_PACKAGE);
	}

	@Test
	public void generatePageObjects_filesGeneratedWithoutErrors()
			throws Exception {
		sut.generatePageObjects();
		verify(validator).validate(searchRules);
		verify(fileWriter).write(TEST_PACKAGE, siteTypeSpec);
	}

	@Test(expected = ValidationException.class)
	public void generatePageObjects_ErrorWhenValidationFailsAndNoForceGenerateFlagSet()
			throws Exception {
		doThrow(new ValidationException("some message")).when(validator).validate(anyList());
		verifyZeroInteractions(fileWriter);
	}
}