package com.epam.page.object.generator.validators;

public interface Validator {

	void validate(ValidationContext validationContext);

	int getPriority();

	String getExceptionMessage();

	void setIsValidateAllSearchRules(boolean flag);

}
