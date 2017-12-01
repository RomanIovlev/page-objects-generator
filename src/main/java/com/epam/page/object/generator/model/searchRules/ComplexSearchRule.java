package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

public class ComplexSearchRule implements SearchRule {

    private SearchRuleType type;
    private List<ComplexInnerSearchRule> complexInnerSearchRules;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public ComplexSearchRule(SearchRuleType type,
                             List<ComplexInnerSearchRule> complexInnerSearchRules) {
        this.type = type;
        this.complexInnerSearchRules = complexInnerSearchRules;
    }

    public SearchRuleType getType() {
        return type;
    }

    public List<ComplexInnerSearchRule> getInnerSearchRules() {
        return complexInnerSearchRules;
    }

    public ComplexInnerSearchRule getRoot() {
        return complexInnerSearchRules.stream()
            .filter(innerSearchRule -> innerSearchRule.getTitle().equals("root"))
            .findFirst().orElseThrow(NullPointerException::new);
    }

    public String getRequiredValue(Element element) {
        String uniqueness = getRoot().getUniqueness();
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    @Override
    public ValidationResultNew beValidated(ValidatorVisitor validatorVisitor) {
        return validatorVisitor.validate(this);
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
    public String toString() {
        return "ComplexSearchRule{" +
            "type='" + type + '\'' +
            ", complexInnerSearchRules=" + complexInnerSearchRules +
            '}';
    }

    @Override
    public Selector getSelector() {
        return getRoot().getSelector();
    }
}
