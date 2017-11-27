package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchRuleTypeGroups {

    public static final Set<SearchRuleType> commonTypes =
        Sets.newHashSet(
            SearchRuleType.BUTTON,
            SearchRuleType.TEXT,
            SearchRuleType.CHECKBOX,
            SearchRuleType.IMAGE,
            SearchRuleType.DATEPICKER,
            SearchRuleType.FILEINPUT,
            SearchRuleType.INPUT,
            SearchRuleType.LABEL,
            SearchRuleType.LINK,
            SearchRuleType.TEXTAREA,
            SearchRuleType.TEXTFIELD,
            SearchRuleType.RADIOBUTTONS,
            SearchRuleType.SELECTOR,
            SearchRuleType.TABS,
            SearchRuleType.TEXTLIST,
            SearchRuleType.CHECKLIST,
            SearchRuleType.ELEMENTS
        );

    public static final Set<SearchRuleType> complexTypes =
        Sets.newHashSet(
            SearchRuleType.TABLE,
            SearchRuleType.COMBOBOX,
            SearchRuleType.DROPDOWN,
            SearchRuleType.DROPLIST,
            SearchRuleType.MENU
        );

    public static final Set<SearchRuleType> formAndSectionTypes =
        Sets.newHashSet(
            SearchRuleType.FORM,
            SearchRuleType.SECTION
        );

    public static final Set<SearchRuleType> commonAndComplexTypes =
        Stream.of(commonTypes, complexTypes)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

    public static final Set<SearchRuleType> allExistingTypes =
        Stream.of(commonTypes, complexTypes, formAndSectionTypes)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

    private SearchRuleTypeGroups() {

    }

    public static boolean isCommonType(SearchRule searchRule) {
        return commonTypes
            .stream()
            .anyMatch(searchRuleType -> searchRuleType.getName().equals(searchRule.getType()));
    }

    public static boolean isComplexType(SearchRule searchRule) {
        return complexTypes
            .stream()
            .anyMatch(searchRuleType -> searchRuleType.getName().equals(searchRule.getType()));
    }

    public static boolean isFormOrSectionType(SearchRule searchRule) {
        return formAndSectionTypes
            .stream()
            .anyMatch(searchRuleType -> searchRuleType.getName().equals(searchRule.getType()));
    }
}
