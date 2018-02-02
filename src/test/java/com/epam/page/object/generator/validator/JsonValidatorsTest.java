package com.epam.page.object.generator.validator;

import static org.junit.Assert.*;

import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.databuilder.searchrule.CommonSearchRuleTestDataBuilder;
import com.epam.page.object.generator.databuilder.searchrule.ComplexSearchRuleTestDataBuilder;
import com.epam.page.object.generator.validator.json.DuplicateTitleInnerSearchRuleValidator;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class JsonValidatorsTest {

    private int COUNT_VALIDATORS = 3;

    private JsonValidators jsonValidators = new JsonValidators();
    private DuplicateTitleInnerSearchRuleValidator validator = new DuplicateTitleInnerSearchRuleValidator();

    @Test
    public void addValidator_JsonValidators_Valid() {

        assertEquals(COUNT_VALIDATORS, jsonValidators.getValidators().size());
        jsonValidators.addValidator(validator);
        assertEquals(COUNT_VALIDATORS + 1, jsonValidators.getValidators().size());
    }

    @Test
    public void addValidatorList_JsonValidators_Valid() {
        assertEquals(COUNT_VALIDATORS, jsonValidators.getValidators().size());
        jsonValidators.addValidatorsList(Lists.newArrayList(validator, validator));
        assertEquals(COUNT_VALIDATORS + 2, jsonValidators.getValidators().size());
    }

    @Test
    public void createWithCustomValidators_JsonValidators_Valid() {
        assertEquals(COUNT_VALIDATORS, jsonValidators.getValidators().size());
        jsonValidators = new JsonValidators(Lists.newArrayList(validator));
        assertEquals(COUNT_VALIDATORS + 1, jsonValidators.getValidators().size());
    }

    @Test
    public void validate_JsonValidators_StatusOk() {
        CommonSearchRule common1 = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorCss();
        CommonSearchRule common2 = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessValue_SelectorXpath();
        ComplexSearchRule complex1 = ComplexSearchRuleTestDataBuilder
            .getComplexSearchRule_WithRoot();
        ComplexSearchRule complex2 = ComplexSearchRuleTestDataBuilder
            .getComplexSearchRule_WithDuplicateTitle();

        List<SearchRule> searchRules = Lists.newArrayList(common1, common2, complex1, complex2);

        jsonValidators.validate(searchRules);

        assertTrue(common1.isValid());
        assertTrue(common2.isValid());
        assertTrue(complex1.isValid());
        assertTrue(complex2.isInvalid());
    }
}