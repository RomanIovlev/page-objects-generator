package com.epam.page.object.generator.validators;

import java.util.List;

public interface Validator {

	void validate(ValidationContext validationContext);

	int getOrder();

	String getExceptionMessage();

}
