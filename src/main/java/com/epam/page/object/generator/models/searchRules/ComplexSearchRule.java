package com.epam.page.object.generator.models.searchRules;

import com.epam.page.object.generator.models.ClassAndAnnotationPair;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.models.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.models.webElements.ComplexWebElement;
import com.epam.page.object.generator.models.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexSearchRule implements SearchRule {

    private SearchRuleType type;
    private List<ComplexInnerSearchRule> complexInnerSearchRules;
    private ClassAndAnnotationPair classAndAnnotation;
    private SelectorUtils selectorUtils;

    private List<ValidationResult> validationResults = new ArrayList<>();
    private final static Logger logger = LoggerFactory.getLogger(ComplexSearchRule.class);

    public ComplexSearchRule(SearchRuleType type,
                             List<ComplexInnerSearchRule> complexInnerSearchRules,
                             ClassAndAnnotationPair classAndAnnotation,
                             SelectorUtils selectorUtils) {
        this.type = type;
        this.complexInnerSearchRules = complexInnerSearchRules;
        this.classAndAnnotation = classAndAnnotation;
        this.selectorUtils = selectorUtils;
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
            .findFirst().orElse(null);
    }

    private String getRequiredValue(Element element) {
        String uniqueness = getRoot().getUniqueness();
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    public ClassAndAnnotationPair getClassAndAnnotation() {
        return classAndAnnotation;
    }

    public String getUniqueness() {
        return getRoot().getUniqueness();
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        ValidationResult visit = validatorVisitor.visit(this);
        logger.info(this + " is '" + visit.isValid() + "', reason '" + visit.getReason() + "'");
        validationResults.add(visit);
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

    @Override
    public void fillWebElementGroup(List<WebElementGroup> webElementGroups, Elements elements) {
        webElementGroups.add(new ComplexWebElementGroup(this, getWebElements(elements),
            selectorUtils));
    }
}
