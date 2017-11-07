package com.epam.page.object.generator.validators;

public interface Validator {

	RuntimeException getException();

	void validate(ValidationContext context);

	int getOrder();
}
