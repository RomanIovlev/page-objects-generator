package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElements.ComplexWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
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

    public boolean isRoot() {
        return title.equals("root");
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
        return "{" +
            "uniqueness='" + uniqueness + '\'' +
            ", title='" + title + '\'' +
            ", selector=" + selector +
            '}';
    }
}
