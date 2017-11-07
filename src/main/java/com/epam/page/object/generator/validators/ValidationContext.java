package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import java.util.List;

public class ValidationContext {
	private List<SearchRule> rulesToValidate;
	private List<SearchRule> validRules;
	private List<SearchRule> notValidRules;

	public ValidationContext(List<SearchRule> toValidate) {
		this.rulesToValidate = toValidate;
		this.validRules= Lists.newArrayList();
		this.notValidRules=Lists.newArrayList();
	}

	public List<SearchRule> getRulesToValidate() {
		return rulesToValidate;
	}

	public List<SearchRule> getValidRules() {
		return validRules;
	}

	public void addRuleToValid(SearchRule searchRule) {
		validRules.add(searchRule);
	}

	public List<SearchRule> getNotValidRules() {
		return notValidRules;
	}

	public void addRuleToInvalid(SearchRule searchRule) {
		notValidRules.add(searchRule);
	}
}
