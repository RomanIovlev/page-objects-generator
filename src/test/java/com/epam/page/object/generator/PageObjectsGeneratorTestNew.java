package com.epam.page.object.generator;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.SearchRuleValidator;
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
	private SearchRuleValidator validator;

	private SearchRule searchRule = new SearchRule();

	private List<SearchRule> searchRules = Lists.newArrayList(searchRule);

	private String outputDir = "";

	private List<String> urls = Lists.newArrayList("https://www.google.com");

	private PageObjectsGenerator sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(parser.getRulesFromJSON()).thenReturn(searchRules);

		sut = new PageObjectsGenerator(parser, validator, javaPoetAdapter, outputDir, urls, TEST_PACKAGE);
	}

	@Test
	public void generatePageObjects_filesGeneratedWithoutErrors()
			throws Exception {
		sut.generatePageObjects();

		verify(validator).validate();

		verify(javaPoetAdapter).writeFile(TEST_PACKAGE, outputDir, searchRules, urls);
	}

	@Test(expected = ValidationException.class)
	public void generatePageObjects_ErrorWhenValidationFailsAndNoForceGenerateFlagSet()
			throws Exception {
		doThrow(new ValidationException("some message")).when(validator).validate();
		sut.generatePageObjects();
		verifyZeroInteractions(javaPoetAdapter);
	}

}