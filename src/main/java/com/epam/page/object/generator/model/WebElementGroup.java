package com.epam.page.object.generator.model;

import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebElementGroup implements Validatable {

    private SearchRule searchRule;
    private List<WebElement> webElements;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public WebElementGroup(SearchRule searchRule, List<WebElement> webElements) {
        this.searchRule = searchRule;
        this.webElements = webElements;
    }

    public SearchRule getSearchRule() {
        return searchRule;
    }

    public List<WebElement> getWebElements() {
        return webElements;
    }

    public boolean isUniqueness() {
        Set<String> uniquenessValues = new HashSet<>();
        for (WebElement webElement : webElements) {
            if(!uniquenessValues.add(webElement.getUniquenessValue())){
                return false;
            }
        }
        return true;
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        validatorVisitor.visit(this);
    }

    @Override
    public List<ValidationResultNew> getValidationResults() {
        return validationResults;
    }

    @Override
    public boolean isValid() {
        return validationResults.stream().allMatch(ValidationResultNew::isValid);
    }

    @Override
    public boolean isInvalid() {
        return validationResults.stream().anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public void addValidationResult(ValidationResultNew validationResult) {
        validationResults.add(validationResult);
    }
}
