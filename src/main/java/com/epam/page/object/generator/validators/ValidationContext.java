package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationContext {

    private List<SearchRule> rulesToValidate;
    private List<SearchRule> validRules;
    private Map<RuntimeException, List<SearchRule>> notValidRules;
    private List<String> urls;

    public ValidationContext(List<SearchRule> toValidate, List<String> urls) {
        this.rulesToValidate = toValidate;
        this.validRules = toValidate;
        this.notValidRules = Maps.newHashMap();
        this.urls = urls;
    }

    public List<SearchRule> getRulesToValidate() {
        return rulesToValidate;
    }

    public List<SearchRule> getValidRules() {
        return validRules;
    }

    public Map<RuntimeException, List<SearchRule>> getNotValidRules() {
        return notValidRules;
    }

    public void addRuleToInvalid(SearchRule searchRule, RuntimeException ex) {
        validRules.remove(searchRule);

        if (notValidRules.containsKey(ex)) {
            notValidRules.get(ex).add(searchRule);
        } else {
            notValidRules.put(ex, Lists.newArrayList(searchRule));
        }
        //TODO throw new Exception if we detect first Exception
    }
}
