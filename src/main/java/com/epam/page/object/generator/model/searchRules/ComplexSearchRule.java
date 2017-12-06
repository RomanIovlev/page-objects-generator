package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElements.ComplexWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public String getTypeName() {
        return type.getName();
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

    public String getUniqueness() {
        return getRoot().getUniqueness();
    }

    public Selector getTransformedSelector() {
        Selector selector = getSelector();
        if (!getUniqueness().equalsIgnoreCase("text")) {
            if (selector.isXpath()) {
                try {
                    return XpathToCssTransformation.getCssSelector(selector);
                } catch (XpathToCssTransformerException e) {
                    e.printStackTrace();
                }
            }
        }

        return selector;
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
        return validationResults.stream()
            .anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public void addValidationResult(ValidationResultNew validationResult) {
        validationResults.add(validationResult);
    }

    @Override
    public String toString() {
        return "SearchRule{" +
            "type='" + type + '\'' +
            ", complexInnerSearchRules=" + complexInnerSearchRules +
            '}';
    }

    @Override
    public Selector getSelector() {
        return getRoot().getSelector();
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (Element element : elements) {
            webElements.add(new ComplexWebElement(element, getRequiredValue(element), getRoot()));
        }
        return webElements;
    }
}
