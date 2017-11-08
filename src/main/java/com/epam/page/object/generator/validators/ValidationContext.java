package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationContext {

    private List<SearchRule> searchRules;
    private List<ValidationResult> validationResults;
    private List<String> urls;

    public ValidationContext(List<SearchRule> searchRules, List<String> urls) {
        validationResults = Lists.newArrayList();
        this.searchRules = searchRules;
        this.urls = urls;
    }

    public void addValidationResult(ValidationResult validationResult){
        validationResults.add(validationResult);
    }

    public List<SearchRule> getAllSearchRules(){
        return searchRules;
    }

    public List<SearchRule> getValidRules() {
        if(validationResults.isEmpty()) return searchRules;
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

    public List<String> getUrls(){
        return urls;
    }

    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }
}
