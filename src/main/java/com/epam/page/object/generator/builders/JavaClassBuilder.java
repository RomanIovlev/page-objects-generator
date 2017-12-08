package com.epam.page.object.generator.builders;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.adapter.javaClassBuildable.FormClassBuildable;
import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaClasses.JavaClass;
import com.epam.page.object.generator.adapter.javaClassBuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.javaClassBuildable.SiteClassBuildable;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaClassBuilder {

    private String packageName;

    public JavaClassBuilder(String packageName) {
        this.packageName = packageName;
    }

    public IJavaClass visit(SiteClassBuildable siteClassBuildable) {
        String packageName = this.packageName + ".site";
        String className = "Site";
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;
        IJavaAnnotation annotation = siteClassBuildable.buildAnnotation();
        List<IJavaField> fields = siteClassBuildable.buildFields(this.packageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, annotation, fields, modifier);
    }

    public IJavaClass visit(PageClassBuildable pageClassBuildable) {
        String packageName = this.packageName + ".page";
        String className = firstLetterUp(splitCamelCase(pageClassBuildable.getTitle()));
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
        IJavaAnnotation annotation = pageClassBuildable.buildAnnotation();
        List<IJavaField> fields = pageClassBuildable.buildFields(this.packageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, annotation, fields, modifier);
    }

    public IJavaClass visit(FormClassBuildable formClassBuildable) {
        FormWebElementGroup formWebElementGroup = formClassBuildable.getFormWebElementGroup();

        FormSearchRule formSearchRule = formWebElementGroup.getSearchRule();
        String packageName = this.packageName + ".form";
        String className = firstLetterUp(formSearchRule.getSection());
        Class superClass = formSearchRule.getTypeName().equals(
            SearchRuleType.FORM.getName()) ? Form.class : Section.class;
        IJavaAnnotation annotation = formClassBuildable.buildAnnotation();
        List<IJavaField> fields = formClassBuildable.buildFields(packageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(packageName, className, superClass, annotation, fields, modifier);
    }
}
