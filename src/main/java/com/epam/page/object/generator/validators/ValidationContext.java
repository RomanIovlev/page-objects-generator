package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationContext {

    private List<SearchRule> validRules;
    private Map<RuntimeException, List<SearchRule>> notValidRules;
    private List<String> urls;

    public ValidationContext(List<SearchRule> toValidate, List<String> urls) {
        this.validRules = toValidate;
        this.notValidRules = Maps.newHashMap();
        this.urls = urls;
    }

    public List<SearchRule> getValidRules() {
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
}
