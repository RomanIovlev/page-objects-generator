package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.select.Elements;

public class SearchRuleValidator {

	private Set<String> supportedTypes;

    private boolean checkLocatorsUniqueness;

	public SearchRuleValidator(Set<String> supportedTypes) {
		this.supportedTypes = supportedTypes;
	}

	public void validate(List<SearchRule> rules, List<String> urls) throws IOException {

	    if (checkLocatorsUniqueness) {
            for (String url : urls) {
                checkLocatorUniquenessExceptions(rules, url);
            }
        }

        boolean exceptionOccurred = false;
        String msg = "";

        List<SearchRule> unsupportedTypeRules = new ArrayList<>();
        List<SearchRule> noLocatorRules = new ArrayList<>();

        for (Iterator<SearchRule> iterator = rules.iterator(); iterator.hasNext(); ) {
            SearchRule rule = iterator.next();

            if (!ruleTypeSupported(rule)) {
                exceptionOccurred = true;
                unsupportedTypeRules.add(rule);
                iterator.remove();
                continue;
            }

            if(!ruleHasLocator(rule)) {
                exceptionOccurred = true;
                noLocatorRules.add(rule);
                iterator.remove();
            }
        }

        if (!unsupportedTypeRules.isEmpty()) {
            msg += "Unsupported types found: " + unsupportedTypeRules + ".\n";
        }

        if (!noLocatorRules.isEmpty()) {
            msg += "Either css or xpath must be specified: " + noLocatorRules + ".\n";
        }

        if (exceptionOccurred) {
            throw new ValidationException(msg);
        }
    }

    public void setCheckLocatorsUniqueness(boolean checkLocatorsUniqueness) {
        this.checkLocatorsUniqueness = checkLocatorsUniqueness;
    }

    private boolean ruleTypeSupported(SearchRule rule) {
        return supportedTypes.contains(rule.getType().toLowerCase());
    }

    private boolean ruleHasLocator(SearchRule rule) {
        return !(rule.getCss() == null && rule.getXpath() == null);
    }

    private void checkLocatorUniquenessExceptions(List<SearchRule> searchRules, String url)
        throws NotUniqueSelectorsException, IOException {
        List<String> notUniqueLocators = new ArrayList<>();

        for (SearchRule rule : searchRules) {
            Elements elements = rule.extractElementsFromWebSite(url);

            if (elements.size() > 1) {
                notUniqueLocators = elements.stream()
                    .map(e->e.cssSelector() + "\n").collect(Collectors.toList());
            }
        }

        if (!notUniqueLocators.isEmpty()) {
            throw new NotUniqueSelectorsException("Search rules with not unique locator: \n" + notUniqueLocators);
        }
    }

}