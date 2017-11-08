package com.epam.page.object.generator.validators;

public interface Validator {

	RuntimeException getException();

	void validate();

	int getOrder();

	ValidationContext getValidationContext();

	void setValidationContext(ValidationContext validationContext);
}
