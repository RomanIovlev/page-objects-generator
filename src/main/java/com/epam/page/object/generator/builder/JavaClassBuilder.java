package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.util.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.classbuildable.FormClassBuildable;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.classbuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.SiteClassBuildable;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.util.SearchRuleType;
import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaClassBuilder {

    private String packageName;

    public JavaClassBuilder(String packageName) {
        this.packageName = packageName;
    }

    public JavaClass visit(SiteClassBuildable siteClassBuildable) {
        String classPackageName = packageName + ".site";
        String className = "Site";
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;
        JavaAnnotation annotation = siteClassBuildable.buildAnnotation();
        List<JavaField> fields = siteClassBuildable.buildFields(this.packageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass visit(PageClassBuildable pageClassBuildable) {
        String classPackageName = packageName + ".page";
        String className = firstLetterUp(splitCamelCase(pageClassBuildable.getTitle()));
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
        JavaAnnotation annotation = pageClassBuildable.buildAnnotation();
        List<JavaField> fields = pageClassBuildable.buildFields(this.packageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass visit(FormClassBuildable formClassBuildable) {
        FormWebElementGroup formWebElementGroup = formClassBuildable.getFormWebElementGroup();

        FormSearchRule formSearchRule = formWebElementGroup.getSearchRule();
        String classPackageName = packageName + ".form";
        String className = firstLetterUp(formSearchRule.getSection());
        Class superClass = formSearchRule.getTypeName().equals(
            SearchRuleType.FORM.getName()) ? Form.class : Section.class;
        JavaAnnotation annotation = formClassBuildable.buildAnnotation();
        List<JavaField> fields = formClassBuildable.buildFields(classPackageName);
        Modifier modifier = Modifier.PUBLIC;

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }
}
