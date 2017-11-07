package com.epam.page.object.generator.validators;


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

	public void setOrder(int order) {
		this.order = order;
	}
}
