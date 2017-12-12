package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.models.WebPage;
import com.epam.page.object.generator.models.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.validators.webValidators.ElementUniquenessValidator;
import com.epam.page.object.generator.validators.webValidators.UniquenessAttributeExistenceValidator;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.util.Lists;

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

    public List<ValidatorVisitor> getValidators(){
        return ImmutableList.copyOf(validators);
    }
}