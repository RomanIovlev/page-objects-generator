package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FormInnerSearchRule implements SearchRule {

    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;
    private ClassAndAnnotationPair classAndAnnotation;

    private List<ValidationResult> validationResults = new ArrayList<>();

    public FormInnerSearchRule(String uniqueness, SearchRuleType type, Selector selector,
                               ClassAndAnnotationPair classAndAnnotation) {
        this.uniqueness = uniqueness;
        this.type = type;
        this.selector = selector;
        this.classAndAnnotation = classAndAnnotation;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public SearchRuleType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public ClassAndAnnotationPair getClassAndAnnotation() {
        return classAndAnnotation;
    }

    public Selector getSelector() {
        return selector;
    }

    public Selector getTransformedSelector() {
        if (!uniqueness.equalsIgnoreCase("text")) {
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
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (Element element : elements) {
            Elements extractElements = SearchRuleExtractor
                .extractElementsFromElement(element, this);
            for (Element extractElement : extractElements) {
                webElements.add(
                    new FormWebElement(extractElement, getRequiredValue(extractElement), this));
            }
        }
        return webElements;
    }

    public String getRequiredValue(Element element) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        validationResults.add(validatorVisitor.visit(this));
    }

    @Override
    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    @Override
    public boolean isValid() {
        return validationResults.stream().allMatch(ValidationResult::isValid);
    }

    @Override
    public boolean isInvalid() {
        return validationResults.stream()
            .anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public void addValidationResult(ValidationResult validationResult) {
        validationResults.add(validationResult);
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
