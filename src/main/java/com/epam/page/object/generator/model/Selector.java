package com.epam.page.object.generator.model;

public class Selector {

    private String type;
    private String value;

    public Selector(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isXpath() {
        return type.equals("xpath");
    }

    public boolean isCss() {
        return type.equals("css");
    }

    @Override
    public String toString() {
        return "{" +
            "type='" + type + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
