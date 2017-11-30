package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import org.jsoup.nodes.Element;

public class FormInnerSearchRule implements SearchRule {

    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;

    public FormInnerSearchRule(String uniqueness, SearchRuleType type, Selector selector) {
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

    public Selector getSelector() {
        return selector;
    }

    @Override
    public String getRequiredValue(Element element) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    @Override
    public boolean beValidated(ValidatorVisitor validatorVisitor) {
        return validatorVisitor.validate(this);
    }

    @Override
    public String toString() {
        return "{" +
                "uniqueness='" + uniqueness + '\'' +
                ", type='" + type + '\'' +
                ", selector=" + selector +
                '}';
    }
}
