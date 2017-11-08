package com.epam.page.object.generator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.builder.PageFieldSpecBuilder;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import com.google.common.collect.Lists;
import com.squareup.javapoet.TypeSpec;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PageObjectsGeneratorTestNew {

	private static final String TEST_PACKAGE = "testPackage";

	@Mock
	private JsonRuleMapper parser;

	@Mock
	private JavaFileWriter fileWriter;

	@Mock
	private PageFieldSpecBuilder pageFieldSpecBuilder;

	@Mock
	private SiteFieldSpecBuilder siteFieldSpecBuilder;

	@Mock
	private SearchRuleValidator validator;

	private SearchRule searchRule = new SearchRule();
	private Map<String, TypeSpec> urlSpecMap;
	private TypeSpec siteTypeSpec = TypeSpec.classBuilder("PageObjectsGeneratorTestNew").build();
	private List<SearchRule> searchRules = Lists.newArrayList(searchRule);
	private List<String> urls = Lists.newArrayList("https://www.google.com");

	private PageObjectsGenerator sut;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		urlSpecMap = new TreeMap<>();

		when(parser.getRulesFromJSON()).thenReturn(searchRules);

		for (String url: urls) {
			TypeSpec pageTypeSpec = TypeSpec.classBuilder("PageObjectsGeneratorTestNew").build();

			urlSpecMap.put(url, pageTypeSpec);

			when(pageFieldSpecBuilder.build(searchRules, url)).thenReturn(pageTypeSpec);
		}

		when(siteFieldSpecBuilder.build(urls)).thenReturn(siteTypeSpec);

		sut = new PageObjectsGenerator(parser, fileWriter, pageFieldSpecBuilder, siteFieldSpecBuilder, validator, urls, TEST_PACKAGE);
	}

	@Test
	public void generatePageObjects_filesGeneratedWithoutErrors()
			throws Exception {
		sut.generatePageObjects();

		verify(validator).validate();

		for (String url: urls) {
			verify(fileWriter).write(TEST_PACKAGE + ".page", urlSpecMap.get(url));
		}

		verify(fileWriter).write(TEST_PACKAGE + ".site", siteTypeSpec);
	}

	@Test(expected = ValidationException.class)
	public void generatePageObjects_ErrorWhenValidationFailsAndNoForceGenerateFlagSet()
			throws Exception {
		doThrow(new ValidationException("some message")).when(validator).validate();
		sut.generatePageObjects();
		verifyZeroInteractions(fileWriter);
	}

}