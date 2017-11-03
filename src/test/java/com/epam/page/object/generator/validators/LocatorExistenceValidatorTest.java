package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LocatorExistenceValidatorTest {
	SearchRule ruleWithCss = new SearchRule("type", "req", "css", null, null);
	SearchRule ruleNoLocator = new SearchRule("type", "req", null, null, null);
	private LocatorExistenceValidator sut;
	private ValidationContext context;
	private List<SearchRule> rules = Lists.newArrayList();


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		rules.add(ruleWithCss);
		context = new ValidationContext(rules);
		sut = new LocatorExistenceValidator();
	}

	@Test
	public void validate_allPassIfOnlyCssIsSet() throws Exception {
		sut.validate(context);
		assertEquals(context.getValidRules().get(0), ruleWithCss);
	}

	@Test
	public void validate_shouldAddSearchRuleToFailedIfNoLocatorSet() throws Exception {
		context = new ValidationContext(Lists.newArrayList(ruleNoLocator));
		sut.validate(context);
		assertEquals(context.getNotValidRules().get(0), ruleNoLocator);
	}
}