package com.epam.page.object.generator.models.searchrule;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.webgroup.WebElementGroup;
import com.epam.page.object.generator.models.webelement.CommonWebElement;
import com.epam.page.object.generator.models.webelement.ComplexWebElement;
import com.epam.page.object.generator.models.webelement.WebElement;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexInnerSearchRule implements SearchRule {

    private String uniqueness;
    private String title;
    private Selector selector;
    private XpathToCssTransformer transformer;

    private List<ValidationResult> validationResults = new ArrayList<>();
    private final static Logger logger = LoggerFactory.getLogger(ComplexInnerSearchRule.class);

    public ComplexInnerSearchRule(String uniqueness, String title, Selector selector,
                                  XpathToCssTransformer transformer) {
        this.uniqueness = uniqueness;
        this.title = title;
        this.selector = selector;
        this.transformer = transformer;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRoot() {
        return title.equals("root");
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
    public Selector getSelector() {
        return selector;
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();

        if (uniqueness != null) {
            for (Element element : elements) {
                webElements.add(new ComplexWebElement(element, uniqueness.equals("text")
                    ? element.text()
                    : element.attr(uniqueness), this));
            }
        } else {
            for (Element element : elements) {
                webElements.add(new CommonWebElement(element, null));
            }
        }

        return webElements;
    }

    @Override
    public void fillWebElementGroup(List<WebElementGroup> webElementGroups, Elements elements) {
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
            ", title='" + title + '\'' +
            ", selector=" + selector +
            '}';
    }
}
