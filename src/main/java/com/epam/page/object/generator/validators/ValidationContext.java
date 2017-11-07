package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import java.util.List;

public class ValidationContext {

	private List<SearchRule> rulesToValidate;
	private List<SearchRule> validRules;
	private List<SearchRule> notValidRules;
	private List<String> urls;

	public ValidationContext(List<SearchRule> toValidate, List<String> urls) {
		this.rulesToValidate = toValidate;
		this.validRules= Lists.newArrayList();
		this.notValidRules=Lists.newArrayList();
		this.urls = urls;
	}

	public List<SearchRule> getRulesToValidate() {
		return rulesToValidate;
	}

	public List<SearchRule> getValidRules() {
		return validRules;
	}

	public List<SearchRule> getNotValidRules() {
		return notValidRules;
	}

	public void addRuleToValid(SearchRule searchRule) {
		validRules.add(searchRule);
	}

	public void addRuleToInvalid(SearchRule searchRule) {
		notValidRules.add(searchRule);
	}
}
