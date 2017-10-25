package com.epam.page.object.generator.validators;

import static java.lang.String.format;

import com.epam.page.object.generator.builder.FieldsBuilder;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchRuleValidator {

    private static Set<String> supportedTypes = FieldsBuilder.getSupportedTypes();

    public static void validate(List<SearchRule> rules) throws ValidationException {

        boolean exceptionOccured = false;
        String msg = "";

        List<SearchRule> unsupportedTypeRules = new ArrayList<>();
        List<SearchRule> noLocatorRules = new ArrayList<>();

        for (Iterator<SearchRule> iter = rules.iterator(); iter.hasNext(); ) {
            SearchRule rule = iter.next();

            if (!ruleTypeSupported(rule)) {
                exceptionOccured = true;
                unsupportedTypeRules.add(rule);
                iter.remove();
                continue;
            }
            if(!ruleHasCorrectLocator(rule)) {
                exceptionOccured = true;
                noLocatorRules.add(rule);
                iter.remove();
            }
        }
        if (!unsupportedTypeRules.isEmpty()) {
            msg += "Unsupported types found: " + unsupportedTypeRules + ".\n";
        }

        if (!noLocatorRules.isEmpty()) {
            msg += "Either css or xpath must be specified: " + noLocatorRules + ".\n";
        }

        if (exceptionOccured) {
            throw new ValidationException(msg);
        }
    }

    private static boolean ruleTypeSupported(SearchRule rule) {
        return supportedTypes.contains(rule.getType().toLowerCase());
    }

    private static boolean ruleHasCorrectLocator(SearchRule rule) {
        if (rule.getCss() == null & rule.getXpath() == null) {
            return false;
        }

        return true;
    }

    public static void checkLocatorUniquenessExceptions(List<SearchRule> searchRules, String url)
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