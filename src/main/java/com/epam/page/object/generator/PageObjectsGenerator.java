package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.IJavaClass;
import com.epam.page.object.generator.adapter.JavaClassBuildable;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.adapter.PageClass;
import com.epam.page.object.generator.adapter.SiteClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.WebValidators;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class PageObjectsGenerator {

    private RawSearchRuleMapper rawSearchRuleMapper;
    private JsonSchemaValidator jsonSchemaValidator;
    private TypeTransformer typeTransformer;
    private ValidationChecker checker;
    private JsonValidators jsonValidators;
    private WebValidators webValidators;
    private JavaClassBuilder javaClassBuilder;
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;
    private JavaFileWriter javaFileWriter;
    private WebPagesBuilder webPagesBuilder;

    private boolean forceGenerateFile = false;

    public PageObjectsGenerator(RawSearchRuleMapper rawSearchRuleMapper,
                                JsonSchemaValidator jsonSchemaValidator,
                                TypeTransformer typeTransformer,
                                ValidationChecker checker,
                                JsonValidators jsonValidators,
                                WebValidators webValidators,
                                JavaClassBuilder javaClassBuilder,
                                WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                JavaFileWriter javaFileWriter,
                                WebPagesBuilder webPagesBuilder) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
        this.jsonSchemaValidator = jsonSchemaValidator;
        this.typeTransformer = typeTransformer;
        this.checker = checker;
        this.jsonValidators = jsonValidators;
        this.webValidators = webValidators;
        this.javaClassBuilder = javaClassBuilder;
        this.webElementGroupFieldBuilder = webElementGroupFieldBuilder;
        this.javaFileWriter = javaFileWriter;
        this.webPagesBuilder = webPagesBuilder;
    }

    public void generatePageObjects(String jsonPath,
                                    String outputDir,
                                    List<String> urls)
        throws IOException, URISyntaxException, XpathToCssTransformerException {

        // load property file
        PropertyLoader.loadProperties();

        // get list of RawSearchRules from json file
        List<RawSearchRule> rawSearchRuleList = rawSearchRuleMapper.getRawSearchRuleList(jsonPath);

        // visit list of RawSearchRules with using json schema
        rawSearchRuleList = jsonSchemaValidator.validate(rawSearchRuleList);

        checker.checkRawSearchRules(rawSearchRuleList);

        List<SearchRule> searchRuleList = typeTransformer.transform(rawSearchRuleList);

        jsonValidators.validate(searchRuleList);

        List<WebPage> webPages = webPagesBuilder.generate(urls);
        webPages.forEach(wp -> wp.addSearchRules(searchRuleList));

        webValidators.validate(webPages);

        List<JavaClassBuildable> rawJavaClasses = new ArrayList<>();

        rawJavaClasses.add(new SiteClass(webPages));

        for (WebPage webPage : webPages) {
            rawJavaClasses.add(new PageClass(webPage, webElementGroupFieldBuilder));
            if(webPage.isContainedFormSearchRule()){
                rawJavaClasses.addAll(webPage.getFormClasses());
            }
        }

        List<IJavaClass> javaClasses = new ArrayList<>();

        for (JavaClassBuildable javaClass : rawJavaClasses) {
            javaClasses.add(javaClass.accept(javaClassBuilder));
        }

        if (webPages.stream().anyMatch(WebPage::hasInvalidWebElementGroup)) {
            if (forceGenerateFile) {
                javaFileWriter.writeFiles(outputDir, javaClasses);
            }
            StringBuilder stringBuilder = new StringBuilder("\n");
            for (WebPage webPage : webPages) {
                if (webPage.hasInvalidWebElementGroup()) {
                    for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
                        webElementGroup.getValidationResults().stream()
                            .filter(ValidationResultNew::isInvalid)
                            .forEach(validationResultNew -> stringBuilder
                                .append(validationResultNew.getReason()).append("\n"));
                    }
                }
            }

            throw new ValidationException(stringBuilder.toString());
        }

        javaFileWriter.writeFiles(outputDir, javaClasses);
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}