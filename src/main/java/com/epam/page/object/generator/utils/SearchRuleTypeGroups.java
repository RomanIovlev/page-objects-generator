package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Sets;
import java.util.Set;

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

}
