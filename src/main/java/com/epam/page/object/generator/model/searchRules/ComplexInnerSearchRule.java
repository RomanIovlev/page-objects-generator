package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import org.jsoup.nodes.Element;

public class ComplexInnerSearchRule implements SearchRule {

    private String uniqueness;
    private String title;
    private Selector selector;

    public ComplexInnerSearchRule(String uniqueness, String title, Selector selector) {
        this.uniqueness = uniqueness;
        this.title = title;
        this.selector = selector;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public String getRequiredValue(Element element) {
        return null;
    }

    @Override
    public boolean beValidated(ValidatorVisitor validatorVisitor) {
        return validatorVisitor.validate(this);
    }

    @Override
    public String toString() {
        return "{" +
                "uniqueness='" + uniqueness + '\'' +
                ", title='" + title + '\'' +
                ", selector=" + selector +
                '}';
    }
}
