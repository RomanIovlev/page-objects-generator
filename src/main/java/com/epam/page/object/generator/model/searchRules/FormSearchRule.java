package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.List;
import org.jsoup.nodes.Element;

public class FormSearchRule implements SearchRule {

    private String section;
    private SearchRuleType type;
    private Selector selector;
    private List<FormInnerSearchRule> innerSearchRules;

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

    @Override
    public String getRequiredValue(Element element) {
        return null;
    }

    public List<FormInnerSearchRule> getInnerSearchRules() {
        return innerSearchRules;
    }

    @Override
    public boolean beValidated(ValidatorVisitor validatorVisitor) {
        return validatorVisitor.validate(this);
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
