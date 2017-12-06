package com.epam.page.object.generator.builders;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.IJavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.adapter.IJavaClass;
import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaClassBuilder {

    private String packageName;
    private JavaFieldBuilder javaFieldBuilder;

    public JavaClassBuilder(String packageName,
                            JavaFieldBuilder javaFieldBuilder) {
        this.packageName = packageName;
        this.javaFieldBuilder = javaFieldBuilder;
    }

    public List<IJavaClass> build(List<WebPage> webPages) {
        List<IJavaClass> javaClasses = new ArrayList<>();
        javaClasses.add(buildSiteClass(webPages));

        for (WebPage webPage : webPages) {
            javaClasses.add(buildWebPageClass(webPage));

            if (webPage.isContainedFormSearchRule()) {
                javaClasses.addAll(buildWebElementGroups(webPage.getWebElementGroups()));
            }
        }

        return javaClasses;
    }

    private IJavaClass buildSiteClass(List<WebPage> webPages) {
        String packageName = this.packageName + ".site";
        String className = "Site";
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;

        List<AnnotationMember> annotationMembers = new ArrayList<>();
        annotationMembers.add(new AnnotationMember("value", "$S", webPages.get(0).getDomainName()));
        IJavaAnnotation annotation = new JavaAnnotation(JSite.class, annotationMembers);

        List<IJavaField> fields = javaFieldBuilder.buildSiteFields(webPages);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, annotation, fields, modifier);
    }

    private IJavaClass buildWebPageClass(WebPage webPage) {
        String packageName = this.packageName + ".page";
        String className = firstLetterUp(splitCamelCase(webPage.getTitle()));
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
        List<IJavaField> fields = javaFieldBuilder.buildWebPageFields(webPage);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, null, fields, modifier);
    }

    private List<IJavaClass> buildWebElementGroups(List<WebElementGroup> webElementGroups) {
        List<IJavaClass> javaClasses = new ArrayList<>();

        for (WebElementGroup webElementGroup : webElementGroups) {
            if (webElementGroup.isJavaClass()) {
                IJavaClass javaClass = buildWebElementGroup(webElementGroup);
                if (javaClass != null) {
                    javaClasses.add(javaClass);
                }
            }
        }

        return javaClasses;
    }

    private IJavaClass buildWebElementGroup(WebElementGroup webElementGroup) {
        if (webElementGroup instanceof FormWebElementGroup) {
            return buildFormClass((FormWebElementGroup) webElementGroup);
        }
        return null;
    }

    private IJavaClass buildFormClass(FormWebElementGroup formWebElementGroup) {
        FormSearchRule formSearchRule = formWebElementGroup.getSearchRule();
        String packageName = this.packageName + ".form";
        String className = firstLetterUp(splitCamelCase(formSearchRule.getSection()));
        Class superClass = formSearchRule.getTypeName().equals(
            SearchRuleType.FORM.getName()) ? Form.class
            : Section.class;

        List<IJavaField> fields = javaFieldBuilder.buildFormFields(formWebElementGroup);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, null, fields, modifier);
    }
}
