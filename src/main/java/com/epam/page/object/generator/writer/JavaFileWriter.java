package com.epam.page.object.generator.writer;

import com.epam.page.object.generator.adapter.FormClass;
import com.epam.page.object.generator.adapter.JavaPoetClass;
import com.epam.page.object.generator.adapter.PageClass;
import com.epam.page.object.generator.adapter.SiteClass;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaFileWriter {

    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public JavaFileWriter(
        SupportedTypesContainer typesContainer,
        XpathToCssTransformation xpathToCssTransformation) {
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    public void writeFiles(String outputDir,
                          String packageName,
                          List<SearchRule> searchRules,
                          List<String> urls)
        throws XpathToCssTransformerException {

        List<JavaPoetClass> classes = new ArrayList<>();
        classes.add(new SiteClass(outputDir, packageName + ".site", urls));

        for (String url : urls) {
            classes.add(new PageClass(
                outputDir,
                packageName + ".page",
                url,
                searchRules,
                typesContainer,
                xpathToCssTransformation)
            );

            for (SearchRule searchRule : searchRules) {
                if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
                    classes.add(new FormClass(
                        outputDir,
                        packageName + ".form",
                        searchRule,
                        url,
                        typesContainer,
                        xpathToCssTransformation)
                    );
                }
            }
        }

        classes.forEach(javaPoetClass -> {
            try {
                javaPoetClass.writeClass();
            } catch (IOException | XpathToCssTransformerException e) {
                e.printStackTrace();
            }
        });
    }
}
