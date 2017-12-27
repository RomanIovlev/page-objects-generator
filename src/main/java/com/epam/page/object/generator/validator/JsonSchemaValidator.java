package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.RawSearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator for Json schema. Check that searchRule have required field, which contains required
 * inner fields and they have't null values. Don't validate any business logic.
 */
public class JsonSchemaValidator {

    private final static Logger logger = LoggerFactory.getLogger(JsonSchemaValidator.class);
    private ValidationExceptionConverter converter;

    public JsonSchemaValidator(ValidationExceptionConverter converter) {
        this.converter = converter;
    }

    /**
     * This method gets list of {@link RawSearchRule} and validate it with {@link Schema}. The
     * result is {@link ValidationResult} for each {@link RawSearchRule} which contains fields:
     * <ul><li>isValid: true; reason: rawSearchRule + " passed!" -- for valid {@link
     * RawSearchRule}</li> <li>isValid: false; reason: exception messege -- for invalid {@link
     * RawSearchRule}</li></ul>
     */
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