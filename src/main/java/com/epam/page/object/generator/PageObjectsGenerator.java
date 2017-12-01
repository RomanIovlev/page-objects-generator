package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class PageObjectsGenerator {

    private RawSearchRuleMapper rawSearchRuleMapper;
    private JsonSchemaValidator jsonSchemaValidator;
    private TypeTransformer typeTransformer;
    private ValidatorsStarter validatorsStarter;
    private JavaFileWriter javaFileWriter;
    private WebPagesBuilder webPagesBuilder;

    private boolean forceGenerateFile = false;

    public PageObjectsGenerator(RawSearchRuleMapper rawSearchRuleMapper,
                                JsonSchemaValidator jsonSchemaValidator,
                                TypeTransformer typeTransformer,
                                ValidatorsStarter validatorsStarter,
                                JavaFileWriter javaFileWriter,
                                WebPagesBuilder webPagesBuilder) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
        this.jsonSchemaValidator = jsonSchemaValidator;
        this.typeTransformer = typeTransformer;
        this.validatorsStarter = validatorsStarter;
        this.javaFileWriter = javaFileWriter;
        this.webPagesBuilder = webPagesBuilder;
    }

    public void generatePageObjects(String jsonPath,
                                    String outputDir,
                                    String packageName,
                                    List<String> urls)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        PropertyLoader.loadProperties();

        List<RawSearchRule> rawSearchRuleList = rawSearchRuleMapper.getRawSearchRuleList(jsonPath);
        rawSearchRuleList = jsonSchemaValidator.validate(rawSearchRuleList);

        //TODO write exception message if some rawSearchRule is invalid
        List<SearchRule> searchRuleList = typeTransformer.transform(rawSearchRuleList);

        //TODO business json validation
        List<SearchRule> validSearchRules = validatorsStarter.validate(searchRuleList);

        List<WebPage> webPages = webPagesBuilder.generate(urls);
        webPages.forEach(wp -> wp.addSearchRules(validSearchRules));

        // TODO: web validation


        //TODO check for invalid SearchRules
//        if (validatorsStarter.hasInvalidRules()) {
//            if (forceGenerateFile) {
//                javaFileWriter.writeFiles(outputDir, packageName, webPages);
//            }
//
//            throw new ValidationException(validatorsStarter.getValidationContext());
//        }

        javaFileWriter.writeFiles(outputDir, packageName, webPages);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}