package com.epam.page.object.generator;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import com.google.common.collect.Lists;
import com.squareup.javapoet.TypeSpec;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageObjectsGeneratorTestNew {

	private static final String TEST_PACKAGE = "testPackage";

	@Mock
	private JSONIntoRuleParser parser;

	@Mock
	private JavaFileWriter fileWriter;

	@Mock
	private SiteFieldSpecBuilder siteFieldSpecBuilder;

	@Mock
	private SearchRuleValidator validator;

	private SearchRule searchRule = new SearchRule();
	private TypeSpec siteTypeSpec = TypeSpec.classBuilder("PageObjectsGeneratorTestNew").build();
	private List<SearchRule> searchRules = Lists.newArrayList(searchRule);
	private List<String> urls = Lists.newArrayList("some_url");

	private PageObjectsGenerator sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(parser.getRulesFromJSON()).thenReturn(searchRules);
		when(siteFieldSpecBuilder.build(urls, searchRules)).thenReturn(siteTypeSpec);

		sut = new PageObjectsGenerator(parser, fileWriter, siteFieldSpecBuilder, validator, urls, TEST_PACKAGE);
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
		sut.generatePageObjects();
		verifyZeroInteractions(fileWriter);
	}

}