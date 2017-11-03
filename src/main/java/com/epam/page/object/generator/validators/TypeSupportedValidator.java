package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import java.util.Set;

public class TypeSupportedValidator implements Validator {
	private int order;

	public TypeSupportedValidator(int order) {
		this.order = order;
	}

	@Override
	public void validate(ValidationContext context) {

	}

	@Override
	public int getOrder() {
		return order;
	}
}
