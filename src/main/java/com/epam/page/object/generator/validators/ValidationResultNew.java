package com.epam.page.object.generator.validators;

public class ValidationResultNew {

    private boolean isValid;
    private String reason;

    public ValidationResultNew(boolean isValid,
                            String reason) {
        this.isValid = isValid;
        this.reason = reason;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isInvalid() {return !isValid;}

    public String getReason() {
        return reason;
    }
}
