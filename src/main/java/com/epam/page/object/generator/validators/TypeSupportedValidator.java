package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.errors.TypeSupportedException;
import com.epam.page.object.generator.model.SearchRule;

public class TypeSupportedValidator extends AbstractValidator {

	//TODO remove BuilderContrainer from Validator
	BuildersContainer bc = new BuildersContainer();

	public TypeSupportedValidator() {
		super(1, new TypeSupportedException("This type of element isn't supported"));
	}

	@Override
	public boolean validate(SearchRule searchRule) {
		return bc.getSupportedTypes().contains(searchRule.getType().toLowerCase());
	}
}
