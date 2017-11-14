package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Context which creates for validation process. {@link ValidationContext} contains: <ul> <li>{@link
 * ValidationContext#searchRules} - list of {@link SearchRule} which need to validate.</li>
 * <li>{@link ValidationContext#validationResults} - list of {@link ValidationResult} which contains
 * information about previous validation processes.</li> <li>{@link ValidationContext#urls} - list
 * of urls which used by some validators for validation elements on the web pages.</li> </ul>
 */
public class ValidationContext {

    /**
     * List of {@link SearchRule} which need to validate.
     */
    private List<SearchRule> searchRules;

    /**
     * List of {@link ValidationResult} which contains all information about {@link SearchRule}
     * after validation process.
     */
    private List<ValidationResult> validationResults;

    /**
     * List of all urls which will be used by validators.
     */
    private List<String> urls;

    public ValidationContext(List<SearchRule> searchRules, List<String> urls) {
        validationResults = Lists.newArrayList();
        this.searchRules = searchRules;
        this.urls = urls;
    }

    /**
     * Add a new {@link ValidationResult} to the {@link ValidationContext#validationResults}.
     *
     * @param validationResult validation result of the {@link SearchRule}.
     */
    public void addValidationResult(ValidationResult validationResult) {
        validationResults.add(validationResult);
    }

    /**
     * Method returns all {@link SearchRule} no matter which validators they are passed or not.
     *
     * @return list of {@link SearchRule}.
     */
    public List<SearchRule> getAllSearchRules() {
        return searchRules;
    }

    /**
     * Method returns list of {@link SearchRule} which have passed all validators at the moment.
     *
     * @return list of valid {@link SearchRule}.
     */
    public List<SearchRule> getValidRules() {
        if (validationResults.isEmpty()) {
            return searchRules;
        }
        return validationResults
            .stream()
            .collect(Collectors.groupingBy(ValidationResult::getSearchRule))
            .entrySet()
            .stream()
            .filter(searchRuleListEntry -> searchRuleListEntry
                .getValue()
                .stream()
                .allMatch(ValidationResult::isValid))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * Method return all urls which will be used by validators.
     *
     * @return list of urls.
     */
    public List<String> getUrls() {
        return urls;
    }

    /**
     * Method returns list of {@link ValidationResult} which contains all information about {@link
     * SearchRule} after validation process.
     *
     * @return list of {@link ValidationResult}
     */
    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    /**
     * Check for invalid rules.
     *
     * @return <b>true</b> - if some {@link SearchRule} haven't passed at least one validator at the
     * moment<br/> <b>false</b> - if all {@link SearchRule} have passed all validators at the
     * moment
     */
    public boolean hasInvalidRules() {
        return validationResults.stream().anyMatch(validationResult -> !validationResult.isValid());
    }

    public String getExceptionsAboutInvalidRules() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        validationResults.stream()
            .filter(validationResult -> !validationResult.isValid())
            .forEach(validationResult -> stringBuilder
                .append(validationResult.getExceptionMessage())
                .append(": ")
                .append(validationResult.getSearchRule())
                .append("\n"));

        return stringBuilder.toString();
    }
}
