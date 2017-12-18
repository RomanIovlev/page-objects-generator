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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaClassBuilder {

    private String packageName;
    private final static Logger logger = LoggerFactory.getLogger(JavaClassBuilder.class);

    public JavaClassBuilder(String packageName) {
        this.packageName = packageName;
    }

    public JavaClass visit(SiteClassBuildable siteClassBuildable) {
        logger.debug("Start creating javaClass from " + siteClassBuildable);
        String classPackageName = packageName + ".site";
        String className = "Site";
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;
        logger.debug("Start creating annotation...");
        JavaAnnotation annotation = siteClassBuildable.buildAnnotation();
        logger.debug("Finish creating annotation");
        logger.debug("Start creating fields...");
        List<JavaField> fields = siteClassBuildable.buildFields(this.packageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass visit(PageClassBuildable pageClassBuildable) {
        logger.debug("Start creating javaClass from " + pageClassBuildable);
        String classPackageName = packageName + ".page";
        String className = firstLetterUp(splitCamelCase(pageClassBuildable.getTitle()));
        Class superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
        JavaAnnotation annotation = pageClassBuildable.buildAnnotation();
        logger.debug("Start creating fields...\n");
        List<JavaField> fields = pageClassBuildable.buildFields(this.packageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass visit(FormClassBuildable formClassBuildable) {
        logger.debug("Start creating javaClass from " + formClassBuildable);
        FormWebElementGroup formWebElementGroup = formClassBuildable.getFormWebElementGroup();

        FormSearchRule formSearchRule = formWebElementGroup.getSearchRule();
        String classPackageName = packageName + ".form";
        String className = firstLetterUp(formSearchRule.getSection());
        Class superClass = formSearchRule.getTypeName().equals(
            SearchRuleType.FORM.getName()) ? Form.class : Section.class;
        JavaAnnotation annotation = formClassBuildable.buildAnnotation();
        logger.debug("Start creating fields...\n");
        List<JavaField> fields = formClassBuildable.buildFields(classPackageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }
}
