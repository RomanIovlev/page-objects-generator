package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.model.SearchRule;

public class TypeSupportedValidator extends AbstractValidator {

	//TODO remove BuilderContrainer from Validator
	BuildersContainer bc = new BuildersContainer();

	public TypeSupportedValidator() {
		super(1);
	}

	@Override
	public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
		return bc.getSupportedTypes().contains(searchRule.getType().toLowerCase());
	}
}
