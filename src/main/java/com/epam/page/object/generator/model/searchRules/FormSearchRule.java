package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

public class FormSearchRule implements SearchRule {

    private String section;
    private SearchRuleType type;
    private Selector selector;
    private List<FormInnerSearchRule> innerSearchRules;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public FormSearchRule(String section, SearchRuleType type, Selector selector,
                          List<FormInnerSearchRule> innerSearchRules) {
        this.section = section;
        this.type = type;
        this.selector = selector;
        this.innerSearchRules = innerSearchRules;
    }

    public String getSection() {
        return section;
    }

    public SearchRuleType getType() {
        return type;
    }

    public Selector getSelector() {
        return selector;
    }

    public List<FormInnerSearchRule> getInnerSearchRules() {
        return innerSearchRules;
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
        return "FormSearchRule{" +
                "section='" + section + '\'' +
                ", type='" + type + '\'' +
                ", selector=" + selector +
                ", innerSearchRules=" + innerSearchRules +
                '}';
    }
}
