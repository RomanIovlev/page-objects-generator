package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.validator.web.ElementUniquenessValidator;
import com.epam.page.object.generator.validator.web.UniquenessAttributeExistenceValidator;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * This class use to validate all searchRules by all WEB validators.
 */
public class WebValidators {

    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new ElementUniquenessValidator(),
        new UniquenessAttributeExistenceValidator()
    );

    public WebValidators() {
    }

    public WebValidators(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * This method will validate all {@link WebElementGroup} of list of {@link WebPage} by all
     * {@link WebValidators} The result is {@link ValidationResult} for each {@link WebElementGroup}
     * which contains fields: <ul><li>isValid: true; reason: rawSearchRule + " passed!" -- for valid
     * {@link WebElementGroup}</li><li>isValid: false; reason: exception messege -- for invalid
     * {@link WebElementGroup}</li></ul>
     *
     * * The result is setting inside {@link WebElementGroup} (for example inside {@link
     * CommonWebElementGroup#validationResults)
     */
    public void validate(List<WebPage> webPages) {
        for (ValidatorVisitor validator : validators) {
            for (WebPage webPage : webPages) {
                for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
                    webElementGroup.accept(validator);
                }
            }
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

    public List<ValidatorVisitor> getValidators() {
        return ImmutableList.copyOf(validators);
    }
}