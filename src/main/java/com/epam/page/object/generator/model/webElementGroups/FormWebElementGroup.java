package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;

public class FormWebElementGroup implements WebElementGroup{

    private FormSearchRule searchRule;
    private List<WebElement> webElements;

    private List<ValidationResultNew> validationResults = new ArrayList<>();

    public FormWebElementGroup(FormSearchRule searchRule, List<WebElement> webElements) {
        this.searchRule = searchRule;
        this.webElements = webElements;
    }

    @Override
    public FormSearchRule getSearchRule() {
        return searchRule;
    }

    @Override
    public List<WebElement> getWebElements() {
        return webElements;
    }

    @Override
    public boolean isJavaClass() {
        return true;
    }

    @Override
    public List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder) {
        return webElementGroupFieldBuilder.visit(this);
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        validationResults.add(validatorVisitor.visit(this));
    }

    @Override
    public List<ValidationResultNew> getValidationResults() {
        return validationResults;
    }

    @Override
    public boolean isValid() {
        return validationResults.stream().allMatch(ValidationResultNew::isValid);
    }

    @Override
    public boolean isInvalid() {
        return validationResults.stream().anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public void addValidationResult(ValidationResultNew validationResult) {
        validationResults.add(validationResult);
    }

    @Override
    public String toString() {
        return searchRule.toString();
    }

    //
    //    public IJavaClass buildJavaClass(WebElementGroup webElementGroup, String packageName, SupportedTypesContainer typesContainer) {
    //    @Override
//        String totalPackageName = packageName + ".form";
//        String className = firstLetterUp(splitCamelCase(searchRule.getSection()));
//        Class superClass = searchRule.getTypeName().equals(
//            SearchRuleType.FORM.getName()) ? Form.class
//            : Section.class;
//
//        List<IJavaField> fields = buildInnerFields(typesContainer);
//        Modifier modifier = Modifier.PUBLIC;
//
//        return new JavaClass(totalPackageName, className, superClass, null, fields, modifier);
//    }
//
//    @Override
//    public List<IJavaField> buildFields(SupportedTypesContainer typesContainer) {
//        List<IJavaField> javaFields = new ArrayList<>();
//
//        String className = typesContainer
//            .getSupportedTypesMap().get(searchRule.getTypeName()).getElementClass()
//            .getName();
//        String fieldName = firstLetterDown(splitCamelCase(searchRule.getSection()));
//
//        Class annotationClass = typesContainer.getSupportedTypesMap()
//            .get(searchRule.getTypeName())
//            .getElementAnnotation();
//
//        IJavaAnnotation annotation = javaAnnotationBuilder
//            .buildAnnotation(annotationClass, searchRule);
//        Modifier[] modifiers = new Modifier[]{PUBLIC};
//
//        javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
//
//        return javaFields;
//    }
//
//    private List<IJavaField> buildInnerFields(SupportedTypesContainer typesContainer){
//        List<IJavaField> javaFields = new ArrayList<>();
//
//        for (WebElement webElement : webElements) {
//            FormInnerSearchRule innerSearchRule = ((FormWebElement) webElement).getSearchRule();
//
//            String className = typesContainer
//                .getSupportedTypesMap().get(innerSearchRule.getTypeName()).getElementClass()
//                .getName();
//            String fieldName = firstLetterDown(splitCamelCase(searchRule.getSection()));
//
//            Class annotationClass = typesContainer.getSupportedTypesMap()
//                .get(searchRule.getTypeName())
//                .getElementAnnotation();
//
//            IJavaAnnotation annotation = javaAnnotationBuilder
//                .buildAnnotation(annotationClass, searchRule);
//            Modifier[] modifiers = new Modifier[]{PUBLIC};
//
//            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
//        }
//
//    }
}
