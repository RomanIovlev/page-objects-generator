package com.epam.page.object.generator.validators;

import static org.junit.Assert.*;

import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.testDataBuilders.RawSearchRuleTestDataBuilder;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class JsonSchemaValidatorTest {

    private ValidationExceptionConverter validationExceptionConverter = new ValidationExceptionConverter();
    private JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator(
        validationExceptionConverter);

    private List<RawSearchRule> rawSearchRules = Lists
        .newArrayList(RawSearchRuleTestDataBuilder.getCommonSearchRule_UniquenessText_SelectorCss(),
            RawSearchRuleTestDataBuilder.getCommonSearchRule_DoesNotPassJsonSchemaValidator(),
            RawSearchRuleTestDataBuilder.getCommonSearchRule_UniquenessText_SelectorXpath());


    @Test
    public void validate() {
        rawSearchRules.get(2)
            .setValidationResults(Lists.newArrayList(new ValidationResult(false, "")));

        jsonSchemaValidator.validate(rawSearchRules);

        assertTrue(rawSearchRules.get(0).isValid());
        assertTrue(rawSearchRules.get(1).isInvalid());
        assertTrue(rawSearchRules.get(2).isInvalid());
    }
}