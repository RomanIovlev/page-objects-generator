package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CommonSearchRule implements SearchRule {

    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public CommonSearchRule(String uniqueness, SearchRuleType type, Selector selector) {
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

    public String getTypeName(){
        return type.getName();
    }

    public String getRequiredValue(Element element) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
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
    public Selector getSelector() {
        return selector;
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (Element element : elements) {
            webElements.add(new CommonWebElement(element, getRequiredValue(element)));
        }
        return webElements;
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
                "uniqueness='" + uniqueness + '\'' +
                ", type='" + type + '\'' +
                ", selector=" + selector +
                '}';
    }
}
