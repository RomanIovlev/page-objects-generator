package com.epam.page.object.generator.validators;

import java.io.IOException;
import java.util.Set;
import org.assertj.core.util.Sets;

public class SearchRuleValidator {

    private Set<Validator> validators = Sets.newLinkedHashSet(
        new LocatorExistenceValidator(),
        new TypeSupportedValidator(),
        new IntermediateCheckValidator());

    private UniquenessLocatorValidator uniquenessLocatorValidator = new UniquenessLocatorValidator();

    private ValidationContext validationContext;

    public SearchRuleValidator(ValidationContext validationContext) {
        this.validationContext = validationContext;
    }

    public void validate() throws IOException {

        for (Validator validator : validators) {
            validator.validate(validationContext);
        }

    }

    public void setCheckLocatorsUniqueness(boolean checkLocatorsUniqueness) {
        if (checkLocatorsUniqueness) {
            if(!validators.contains(uniquenessLocatorValidator)) {
                validators.add(uniquenessLocatorValidator);
            }
        }
        else{
            validators.remove(uniquenessLocatorValidator);
        }
    }

    public void addValidator(Validator validator){
        validators.add(validator);
    }
}
