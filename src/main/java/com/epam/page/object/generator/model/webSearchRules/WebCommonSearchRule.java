package com.epam.page.object.generator.model.webSearchRules;

import com.epam.page.object.generator.model.WebElement;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCommonSearchRule implements WebSearchRule {

    private CommonSearchRule searchRule;
    private List<WebElement> webElements;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public WebCommonSearchRule(CommonSearchRule searchRule, List<WebElement> webElements) {
        this.searchRule = searchRule;
        this.webElements = webElements;
    }

    public CommonSearchRule getSearchRule() {
        return searchRule;
    }

    public List<WebElement> getWebElements() {
        return webElements;
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
        return validationResults.stream()
            .anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public void addValidationResult(ValidationResultNew validationResult) {
        validationResults.add(validationResult);
    }

    @Override
    public boolean isUniqueness() {
        Set<String> uniquenessValues = new HashSet<>();
        for (WebElement webElement : webElements) {
            if(!uniquenessValues.add(webElement.getUniquenessValue())){
                return false;
            }
        }

        return true;
    }
}
