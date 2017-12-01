package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

public class CommonSearchRule implements SearchRule {

    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public CommonSearchRule(String uniqueness, SearchRuleType type, Selector selector) {
        this.uniqueness = uniqueness;
        this.type = type;
        this.selector = selector;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public SearchRuleType getType() {
        return type;
    }

    public String getRequiredValue(Element element) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    @Override
    public Selector getSelector() {
        return selector;
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
        return "CommonSearchRule{" +
                "uniqueness='" + uniqueness + '\'' +
                ", type='" + type + '\'' +
                ", selector=" + selector +
                '}';
    }
}
