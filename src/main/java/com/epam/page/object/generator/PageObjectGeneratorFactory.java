package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.builders.WebPagesBuilder;
import com.epam.page.object.generator.builders.searchRuleBuilders.SearchRuleBuilders;
import com.epam.page.object.generator.builders.searchRuleBuilders.SearchRuleBuildersFactory;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleGroupsScheme;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.utils.SearchRuleGroups;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.ValidationExceptionConverter;
import com.epam.page.object.generator.validators.WebValidators;

public class PageObjectGeneratorFactory {

    public static PageObjectsGenerator getPageObjectGenerator(String packageName,
                                                              String propertyFile) {
        PropertyLoader propertyLoader = new PropertyLoader(propertyFile);
        SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
        SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
        SearchRuleBuildersFactory searchRuleBuildersFactory = new SearchRuleBuildersFactory(
            searchRuleGroupsScheme, searchRuleGroupList);
        RawSearchRuleMapper parser = new RawSearchRuleMapper(searchRuleGroupsScheme,
            searchRuleGroupList);
        JsonSchemaValidator validator = new JsonSchemaValidator(new ValidationExceptionConverter());

        SearchRuleBuilders searchRuleBuilders = searchRuleBuildersFactory.getMapWithBuilders();
        XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
        TypeTransformer transformer = new TypeTransformer(new SupportedTypesContainer(),
            searchRuleBuilders, xpathToCssTransformer);
        ValidationChecker checker = new ValidationChecker();
        JavaFileWriter javaPoetAdapter = new JavaFileWriter();
        JsonValidators jsonValidators = new JsonValidators();
        WebValidators webValidators = new WebValidators();
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder(packageName);
        WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();
        WebPagesBuilder builder = new WebPagesBuilder();
        SelectorUtils selectorUtils = new SelectorUtils();
        SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

        return new PageObjectsGenerator(parser, validator, transformer, checker,
            jsonValidators, webValidators, javaClassBuilder, webElementGroupFieldBuilder,
            javaPoetAdapter, builder, selectorUtils, searchRuleExtractor);
    }

}
