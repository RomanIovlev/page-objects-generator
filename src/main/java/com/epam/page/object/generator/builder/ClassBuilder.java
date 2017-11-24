package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ClassBuilder {


    public ClassBuilder(List<SearchRule> searchRules, List<String> urls)
        throws XpathToCssTransformerException {
        try {
            JavaPoetClass siteClass = new SiteClass(urls, "", "");
            siteClass.writeClass();

            for (String url : urls) {
                JavaPoetClass pageClass = new PageClass("", "", url, searchRules,
                    new SupportedTypesContainer(), new XpathToCssTransformation());
                pageClass.writeClass();
                for (SearchRule searchRule : searchRules) {
                    JavaPoetClass formClass = new FormClass("", "", searchRule, url,
                        new SupportedTypesContainer(), new XpathToCssTransformation());
                    formClass.writeClass();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void writeFiles(String outputDir, String packageName) {
//        String sitePackageName = packageName + ".site";
//
//        TypeSpec siteClass = buildSiteClass(sitePackageName, urls);
//
//        javaFile = JavaFile.builder(sitePackageName, siteClass)
//            .build();
//
//        javaFile.writeTo(Paths.get(outputDir));
//
//        String pagesPackageName = packageName + ".page";
//
//        for (String url : urls) {
//            TypeSpec pageClass = buildPageClass(searchRules, url);
//
//            for (SearchRule searchRule : searchRules) {
//                if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
//                    JavaFile.builder(packageName + ".form",
//                        buildFormClass(searchRule, url))
//                        .build()
//                        .writeTo(Paths.get(outputDir));
//                }
//            }
//
//            javaFile = JavaFile.builder(pagesPackageName, pageClass)
//                .build();
//
//            javaFile.writeTo(Paths.get(outputDir));
//        }
//    }

}
