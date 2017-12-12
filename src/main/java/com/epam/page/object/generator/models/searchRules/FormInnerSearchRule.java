package com.epam.page.object.generator.models.searchRules;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.models.ClassAndAnnotationPair;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.models.webElements.FormWebElement;
import com.epam.page.object.generator.models.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormInnerSearchRule implements SearchRule {

    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;
    private ClassAndAnnotationPair classAndAnnotation;
    private XpathToCssTransformer transformer;
    private SearchRuleExtractor searchRuleExtractor;

    private List<ValidationResult> validationResults = new ArrayList<>();
    private final static Logger logger = LoggerFactory.getLogger(FormInnerSearchRule.class);

    public FormInnerSearchRule(String uniqueness, SearchRuleType type, Selector selector,
                               ClassAndAnnotationPair classAndAnnotation,
                               XpathToCssTransformer transformer,
                               SearchRuleExtractor searchRuleExtractor) {
        this.uniqueness = uniqueness;
        this.type = type;
        this.selector = selector;
        this.classAndAnnotation = classAndAnnotation;
        this.transformer = transformer;
        this.searchRuleExtractor = searchRuleExtractor;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public SearchRuleType getType() {
        return type;
    }

    public ClassAndAnnotationPair getClassAndAnnotation() {
        return classAndAnnotation;
    }

    public Selector getSelector() {
        return selector;
    }

    public Selector getTransformedSelector() {
        if (!uniqueness.equalsIgnoreCase("text") && selector.isXpath()) {
            try {
                return transformer.getCssSelector(selector);
            } catch (XpathToCssTransformerException e) {
                e.printStackTrace();
            }
        }

        return selector;
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (Element element : elements) {
            Elements extractElements = searchRuleExtractor
                .extractElementsFromElement(element, this);
            for (Element extractElement : extractElements) {
                webElements.add(
                    new FormWebElement(extractElement, getRequiredValue(extractElement), this));
            }
        }
        return webElements;
    }

    @Override
    public void fillWebElementGroup(List<WebElementGroup> webElementGroups, Elements elements) {
    }

    private String getRequiredValue(Element element) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
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
        return "{" +
            "uniqueness='" + uniqueness + '\'' +
            ", type='" + type + '\'' +
            ", selector=" + selector +
            '}';
    }
}
