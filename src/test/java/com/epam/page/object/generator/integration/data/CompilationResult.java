package com.epam.page.object.generator.integration.data;

public class CompilationResult {
    private Class manualClass;
    private Class expectedClass;
    private boolean compilationSuccess;

    public CompilationResult(Class manualClass, Class expectedClass,
                             boolean compilationSuccess) {
        this.manualClass = manualClass;
        this.expectedClass = expectedClass;
        this.compilationSuccess = compilationSuccess;
    }

    public Class getManualClass() {
        return manualClass;
    }

    public void setManualClass(Class manualClass) {
        this.manualClass = manualClass;
    }

    public Class getExpectedClass() {
        return expectedClass;
    }

    public void setExpectedClass(Class expectedClass) {
        this.expectedClass = expectedClass;
    }

    public boolean isCompilationSuccess() {
        return compilationSuccess;
    }

    public void setCompilationSuccess(boolean compilationSuccess) {
        this.compilationSuccess = compilationSuccess;
    }
}
