package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class PageObjectsGenerator {

    private JsonRuleMapper parser;
    private ValidatorsStarter validatorsStarter;
    private JavaFileWriter javaFileWriter;
    private String outPutDir;
    private List<String> urls;
    private String packageName;

    private boolean forceGenerateFile = false;

    public PageObjectsGenerator(JsonRuleMapper parser,
                                ValidatorsStarter validatorsStarter,
                                JavaFileWriter javaFileWriter,
                                String outPutDir,
                                List<String> urls,
                                String packageName) {
        this.parser = parser;
        this.validatorsStarter = validatorsStarter;
        this.javaFileWriter = javaFileWriter;
        this.outPutDir = outPutDir;
        this.urls = urls;
        this.packageName = packageName;
    }

    public void generatePageObjects()
        throws IOException, URISyntaxException, XpathToCssTransformerException {
        List<SearchRule> searchRules = parser.getRulesFromJSON();
        List<SearchRule> validSearchRules = validatorsStarter.validate(searchRules, urls);
        generateJavaFiles(validSearchRules);
    }

    private void generateJavaFiles(List<SearchRule> searchRules)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        if (validatorsStarter.getValidationContext().hasInvalidRules()) {
            if (forceGenerateFile) {
                javaFileWriter.writeFiles(outPutDir, packageName, searchRules, urls);
            }

            throw new ValidationException(validatorsStarter.getValidationContext());
        }

        javaFileWriter.writeFiles(outPutDir, packageName, searchRules, urls);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}