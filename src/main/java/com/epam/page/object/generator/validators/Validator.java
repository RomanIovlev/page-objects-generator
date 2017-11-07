package com.epam.page.object.generator.validators;

public interface Validator {

	void validate(ValidationContext context);

	int getOrder();
}
