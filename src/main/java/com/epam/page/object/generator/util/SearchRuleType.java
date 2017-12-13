package com.epam.page.object.generator.util;

public enum SearchRuleType {

    BUTTON("button"),
    TEXT("text"),
    CHECKBOX("checkbox"),
    IMAGE("image"),
    DATEPICKER("datepicker"),
    FILEINPUT("fileinput"),
    INPUT("input"),
    LABEL("label"),
    LINK("link"),
    TEXTAREA("textarea"),
    TEXTFIELD("textfield"),
    RADIOBUTTONS("radiobuttons"),
    SELECTOR("selector"),
    TABS("tabs"),
    TEXTLIST("textlist"),
    CHECKLIST("checklist"),
    ELEMENTS("elements"),
    TABLE("table"),
    COMBOBOX("combobox"),
    DROPDOWN("dropdown"),
    DROPLIST("droplist"),
    MENU("menu"),
    FORM("form"),
    SECTION("section");

    private final String name;

    SearchRuleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SearchRuleType getSearchRuleTypeByString(String name) {
        for (SearchRuleType searchRuleType : SearchRuleType.values()) {
            if (searchRuleType.getName().equals(name)) {
                return searchRuleType;
            }
        }
        return null;
    }
}
