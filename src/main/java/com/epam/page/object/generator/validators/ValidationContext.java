package com.epam.page.object.generator.validators;

import static java.util.stream.Collectors.groupingBy;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationContext {

    private List<SearchRule> searchRules;
    private List<ValidationResult> validationResults;
    private List<String> urls;

    public ValidationContext(List<SearchRule> searchRules, List<String> urls) {
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
        validationResults.stream().collect(groupingBy(ValidationResult::getSearchRule, ))
        return validRules;
    }

    public List<SearchRule> getNotValidRules() {
        return notValidRules
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<RuntimeException, List<SearchRule>> getNotValidRulesWithExceptions() {
        return notValidRules;
    }

    public void addRuleToInvalid(SearchRule searchRule, RuntimeException ex) {
        if (notValidRules.containsKey(ex)) {
            notValidRules.get(ex).add(searchRule);
        } else {
            notValidRules.put(ex, Lists.newArrayList(searchRule));
        }
        //TODO throw new Exception if we detect first Exception
    }

    public List<String> getUrls(){
        return urls;
    }
}
