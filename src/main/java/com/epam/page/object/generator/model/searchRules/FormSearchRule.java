package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.select.Elements;

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

    public String getTypeName(){
        return type.getName();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (FormInnerSearchRule innerSearchRule : innerSearchRules) {
            webElements.addAll(innerSearchRule.getWebElements(elements));
        }
        return webElements;
    }

    public List<FormInnerSearchRule> getInnerSearchRules() {
        return innerSearchRules;
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
        return "SearchRule{" +
                "section='" + section + '\'' +
                ", type='" + type + '\'' +
                ", selector=" + selector +
                ", innerSearchRules=" + innerSearchRules +
                '}';
    }
}
