package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ClassBuilder {


    public ClassBuilder(List<SearchRule> searchRules, List<String> urls) {
        try {
            JavaPoetClass javaPoetClass = new SiteClass(urls, "", "");
            javaPoetClass.writeClass();
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
