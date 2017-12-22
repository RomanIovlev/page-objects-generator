package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.classbuildable.FormClass;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.classbuildable.PageClass;
import com.epam.page.object.generator.adapter.classbuildable.SiteClass;
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
    private static final String SITE_EXTENSION = ".site";
    private static final String PAGE_EXTENSION = ".page";
    private static final String FORM_EXTENSION = ".form";

    private final static Logger logger = LoggerFactory.getLogger(JavaClassBuilder.class);

    public JavaClassBuilder(String packageName) {
        this.packageName = packageName;
    }

    public JavaClass build(SiteClass siteClass) {
        logger.debug("Start creating javaClass from " + siteClass);
        String classPackageName = packageName + SITE_EXTENSION;
        String className = "Site";
        Class<?> superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebSite.class;
        logger.debug("Start creating annotation...");
        JavaAnnotation annotation = siteClass.buildAnnotation();
        logger.debug("Finish creating annotation");
        logger.debug("Start creating fields...");
        List<JavaField> fields = siteClass.buildFields(this.packageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass build(PageClass pageClass) {
        logger.debug("Start creating javaClass from " + pageClass);
        String classPackageName = packageName + PAGE_EXTENSION;
        String className = capitalize(splitCamelCase(pageClass.getTitle()));
        Class<?> superClass = com.epam.jdi.uitests.web.selenium.elements.composite.WebPage.class;
        JavaAnnotation annotation = pageClass.buildAnnotation();
        logger.debug("Start creating fields...\n");
        List<JavaField> fields = pageClass.buildFields(this.packageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }

    public JavaClass build(FormClass formClass) {
        logger.debug("Start creating javaClass from " + formClass);
        FormWebElementGroup formWebElementGroup = formClass.getFormWebElementGroup();

        FormSearchRule formSearchRule = formWebElementGroup.getSearchRule();
        String classPackageName = packageName + FORM_EXTENSION;
        String className = capitalize(formSearchRule.getSection());
        Class<?> superClass = formSearchRule.getTypeName().equals(
            SearchRuleType.FORM.getName()) ? Form.class : Section.class;
        JavaAnnotation annotation = formClass.buildAnnotation();
        logger.debug("Start creating fields...\n");
        List<JavaField> fields = formClass.buildFields(classPackageName);
        logger.debug("Finish creating fields");
        Modifier modifier = Modifier.PUBLIC;
        logger.debug("Finish creating javaClass\n");

        return new JavaClass(classPackageName, className, superClass, annotation, fields, modifier);
    }
}
