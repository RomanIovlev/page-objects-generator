package com.epam.page.object.generator;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPageGenerator;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import com.epam.page.object.generator.writer.JavaFileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

        WebPageGenerator webPageGenerator = new WebPageGenerator();
        List<WebPage> webPages = webPageGenerator.generate(urls);
        webPages.forEach(wp -> wp.addSearchRulesForCurrentWebPage(searchRules));
        List <SearchRule> searchRules1 = new ArrayList<>();
        List<String> urls1 = new ArrayList<>();
        for (WebPage wp:webPages) {
            searchRules1.addAll(wp.getValidSearchRulesOfCurrentWebPage());
            searchRules1.addAll(wp.getInvalidSearchRulesOfCurrentWebPage());
            urls1.add(wp.getUrl());
        }
        List<SearchRule> validSearchRules = validatorsStarter.validate(searchRules1, urls1);

        generateJavaFiles(validSearchRules);
    }

    private void generateJavaFiles(List<SearchRule> searchRules)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        if (validatorsStarter.getValidationContext().hasInvalidRules()) {
            if (forceGenerateFile) {
                javaFileWriter.writeFile(packageName, outPutDir, searchRules, urls);
            }

            throw new ValidationException(validatorsStarter.getValidationContext());
        }

        javaFileWriter.writeFile(packageName, outPutDir, searchRules, urls);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}