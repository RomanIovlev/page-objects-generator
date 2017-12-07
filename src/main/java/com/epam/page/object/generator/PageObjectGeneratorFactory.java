package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.builders.WebPagesBuilder;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.ValidationExceptionConverter;
import com.epam.page.object.generator.validators.WebValidators;

public class PageObjectGeneratorFactory {

    public static PageObjectsGenerator getPageObjectGenerator(String packageName) {
        RawSearchRuleMapper parser = new RawSearchRuleMapper();
        JsonSchemaValidator validator = new JsonSchemaValidator(new ValidationExceptionConverter());
        TypeTransformer transformer = new TypeTransformer(new SupportedTypesContainer());
        ValidationChecker checker = new ValidationChecker();
        JavaFileWriter javaPoetAdapter = new JavaFileWriter();
        JsonValidators jsonValidators = new JsonValidators();
        WebValidators webValidators = new WebValidators();
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder(packageName);
        WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();
        WebPagesBuilder builder = new WebPagesBuilder();

        return new PageObjectsGenerator(parser, validator, transformer, checker,
            jsonValidators, webValidators, javaClassBuilder, webElementGroupFieldBuilder,
            javaPoetAdapter, builder);
    }

}
