package com.epam.page.object.generator.validators;

import java.util.List;

public interface Validator {

	void validate(ValidationContext validationContext, boolean isValidateAllRules);

	int getOrder();

	String getExceptionMessage();

}
