package com.epam.page.object.generator;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import com.epam.page.object.generator.writer.JavaFileWriter;
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
// searchRules validation

      List<SearchRule> validSearchRules = validatorsStarter.validate(searchRules,urls);

        WebPagesBuilder webPageGenerator = new WebPagesBuilder();
        List<WebPage> webPages = webPageGenerator.generate(urls);
        webPages.forEach(wp -> wp.addSearchRulesForCurrentWebPage(validSearchRules));
        // TODO: web validation
        //
        generateJavaFiles(webPages);
    }

    private void generateJavaFiles(List<WebPage> webPages)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        if (validatorsStarter.getValidationContext().hasInvalidRules()) {
            if (forceGenerateFile) {
                javaFileWriter.writeFiles(outPutDir, packageName, webPages);
            }

            throw new ValidationException(validatorsStarter.getValidationContext());
        }

        javaFileWriter.writeFiles(outPutDir, packageName, webPages);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}