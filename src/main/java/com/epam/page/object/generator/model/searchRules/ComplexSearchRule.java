package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.List;
import org.jsoup.nodes.Element;

public class ComplexSearchRule implements SearchRule {

    private SearchRuleType type;
    private List<ComplexInnerSearchRule> complexInnerSearchRules;

    public ComplexSearchRule(SearchRuleType type,
                             List<ComplexInnerSearchRule> complexInnerSearchRules) {
        this.type = type;
        this.complexInnerSearchRules = complexInnerSearchRules;
    }

    public SearchRuleType getType() {
        return type;
    }

    public List<ComplexInnerSearchRule> getComplexInnerSearchRules() {
        return complexInnerSearchRules;
    }

    public ComplexInnerSearchRule getRoot() {
        return complexInnerSearchRules.stream()
            .filter(innerSearchRule -> innerSearchRule.getTitle().equals("root"))
            .findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean beValidated(ValidatorVisitor validatorVisitor) {
        return validatorVisitor.validate(this);
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

    @Override
    public String getRequiredValue(Element element) {
        String uniqueness = getRoot().getUniqueness();
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }
}
