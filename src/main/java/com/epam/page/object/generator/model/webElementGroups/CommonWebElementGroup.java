package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;

public class CommonWebElementGroup implements WebElementGroup{

    private CommonSearchRule searchRule;
    private List<WebElement> webElements;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public CommonWebElementGroup(CommonSearchRule searchRule, List<WebElement> webElements) {
        this.searchRule = searchRule;
        this.webElements = webElements;
    }

    @Override
    public CommonSearchRule getSearchRule() {
        return searchRule;
    }

    @Override
    public List<WebElement> getWebElements() {
        return webElements;
    }

    @Override
    public boolean isJavaClass() {
        return false;
    }

    @Override
    public List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder) {
        return webElementGroupFieldBuilder.visit(this);
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        validationResults.add(validatorVisitor.visit(this));
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

    @Override
    public String toString() {
        return searchRule.toString();
    }
}
