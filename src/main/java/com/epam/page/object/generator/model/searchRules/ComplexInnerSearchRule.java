package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webSearchRules.WebSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.select.Elements;

public class ComplexInnerSearchRule implements SearchRule {

    private String uniqueness;
    private String title;
    private Selector selector;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

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

    public boolean isRoot(){
        return title.equals("root");
    }

    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public WebSearchRule getWebSearchRule(Elements elements) {
        return null;
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
        return "{" +
                "uniqueness='" + uniqueness + '\'' +
                ", title='" + title + '\'' +
                ", selector=" + selector +
                '}';
    }
}
