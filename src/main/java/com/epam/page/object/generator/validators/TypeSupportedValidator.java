package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.errors.TypeSupportedException;

public class TypeSupportedValidator implements Validator {

	private int order;
	private RuntimeException ex = new TypeSupportedException();

	public TypeSupportedValidator(int order) {
		this.order = order;
	}

	@Override
	public RuntimeException getException() {
		return null;
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
