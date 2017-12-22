package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.RawSearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSchemaValidator {

    private final static Logger logger = LoggerFactory.getLogger(JsonSchemaValidator.class);
    private ValidationExceptionConverter converter;

    public JsonSchemaValidator(ValidationExceptionConverter converter) {
        this.converter = converter;
    }

    public void validate(List<RawSearchRule> rawSearchRuleList) {
        for (RawSearchRule rawSearchRule : rawSearchRuleList) {
            if (rawSearchRule.isInvalid()) {
                continue;
            }
            try {
                rawSearchRule.getSchema().validate(rawSearchRule.getElement());
                rawSearchRule.setValidationResults(
                    Lists.newArrayList(
                        new ValidationResult(true, rawSearchRule + " is correct!")));
                logger.debug(rawSearchRule + " is valid");
            } catch (ValidationException e) {
                logger.warn(rawSearchRule + " is invalid:");
                rawSearchRule.setValidationResults(converter.toValidationResult(e));
            }
        }
    }


}
