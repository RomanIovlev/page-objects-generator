package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class PageObjectsGenerator {

    private RawSearchRuleMapper rawSearchRuleMapper;
    private JsonSchemaValidator jsonSchemaValidator;
    private TypeTransformer typeTransformer;
    private ValidationChecker checker;
    private JsonValidators jsonValidators;
    private JavaFileWriter javaFileWriter;
    private WebPagesBuilder webPagesBuilder;

    private boolean forceGenerateFile = false;

    public PageObjectsGenerator(RawSearchRuleMapper rawSearchRuleMapper,
                                JsonSchemaValidator jsonSchemaValidator,
                                TypeTransformer typeTransformer,
                                ValidationChecker checker,
                                JsonValidators jsonValidators,
                                JavaFileWriter javaFileWriter,
                                WebPagesBuilder webPagesBuilder) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
        this.jsonSchemaValidator = jsonSchemaValidator;
        this.typeTransformer = typeTransformer;
        this.checker = checker;
        this.jsonValidators = jsonValidators;
        this.javaFileWriter = javaFileWriter;
        this.webPagesBuilder = webPagesBuilder;
    }

    public void generatePageObjects(String jsonPath,
                                    String outputDir,
                                    String packageName,
                                    List<String> urls)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        // load property file
        PropertyLoader.loadProperties();

        // get list of RawSearchRules from json file
        List<RawSearchRule> rawSearchRuleList = rawSearchRuleMapper.getRawSearchRuleList(jsonPath);

        // visit list of RawSearchRules with using json schema
        rawSearchRuleList = jsonSchemaValidator.validate(rawSearchRuleList);

        //TODO write exception message if some rawSearchRule is invalid
        checker.checkRawSearchRules(rawSearchRuleList);

        List<SearchRule> searchRuleList = typeTransformer.transform(rawSearchRuleList);

        //TODO business json validation
        List<SearchRule> validSearchRules = jsonValidators.validate(searchRuleList);



        List<WebPage> webPages = webPagesBuilder.generate(urls);
        webPages.forEach(wp -> wp.addSearchRules(validSearchRules));

        // TODO: web validation




        //TODO check for invalid SearchRules
//        if (jsonValidators.hasInvalidRules()) {
//            if (forceGenerateFile) {
//                javaFileWriter.writeFiles(outputDir, packageName, webPages);
//            }
//
//            throw new ValidationException(jsonValidators.getValidationContext());
//        }

        javaFileWriter.writeFiles(outputDir, packageName, webPages);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}