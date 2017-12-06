package com.epam.page.object.generator.validators;

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

    public List<RawSearchRule> validate(List<RawSearchRule> rawSearchRuleList) {

        logger.info("Start validation SearchRules");
        for (RawSearchRule rawSearchRule : rawSearchRuleList) {
            try {
                rawSearchRule.getSchema().validate(rawSearchRule.getElement());
                rawSearchRule.setValidationResults(
                    Lists.newArrayList(
                        new ValidationResultNew(true, rawSearchRule + " is correct!")));
                logger.info(rawSearchRule + "is valid");
            } catch (ValidationException e) {
                logger.error(rawSearchRule + " is invalid:");
                rawSearchRule.setValidationResults(converter.toValidationResult(e));
            }
        }

        return rawSearchRuleList;
    }


}
