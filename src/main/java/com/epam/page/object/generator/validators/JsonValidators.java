package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.models.searchrule.Validatable;
import com.epam.page.object.generator.validators.json.DuplicateTitleInnerSearchRuleValidator;
import com.epam.page.object.generator.validators.json.RootExistenceValidator;
import com.epam.page.object.generator.validators.json.TitleOfComplexElementValidator;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonValidators {

    private final static Logger logger = LoggerFactory.getLogger(JsonValidators.class);

    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new DuplicateTitleInnerSearchRuleValidator(),
        new RootExistenceValidator(),
        new TitleOfComplexElementValidator()
    );

    public JsonValidators() {
    }

    public JsonValidators(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    public void validate(List<SearchRule> searchRules) {

        for (ValidatorVisitor validator : validators) {
            logger.info(validator + " starts validation...");
            for (Validatable searchRule : searchRules) {
                searchRule.accept(validator);
            }
            logger.info(validator + " finished validation\n");
        }
    }

    public void addValidator(ValidatorVisitor validator) {
        validators.add(validator);
    }

    public void addValidatorsList(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    public List<ValidatorVisitor> getValidators(){
        return ImmutableList.copyOf(validators);
    }
}
