package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.adapter.javaClassBuildable.JavaClassBuildable;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.adapter.javaClassBuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.javaClassBuildable.SiteClassBuildable;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.builders.WebPagesBuilder;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.WebValidators;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger logger = LoggerFactory.getLogger(PageObjectsGenerator.class);

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

    public void generatePageObjects(String jsonPath, String outputDir, List<String> urls)
        throws IOException {

        logger.info("Create list of RawSearchRules...");
        List<RawSearchRule> rawSearchRuleList = rawSearchRuleMapper.getRawSearchRuleList(jsonPath);
        logger.info("Finish creating list of RawSearchRules\n");

        logger.info("Start validation RawSearchRules with using Json Schema...");
        jsonSchemaValidator.validate(rawSearchRuleList);
        logger.info("Finish validation!\n");

        checker.checkRawSearchRules(rawSearchRuleList);

        logger.info("Start transforming RawSearchRules...");
        List<SearchRule> searchRuleList = typeTransformer.transform(rawSearchRuleList);
        logger.info("Finish transforming RawSearchRules\n");

        logger.info("Start Json validation...\n");
        jsonValidators.validate(searchRuleList);
        logger.info("Finish Json validation\n");

        checker.checkSearchRules(searchRuleList);

        List<WebPage> webPages = webPagesBuilder.generate(urls);
        webPages.forEach(wp -> wp.addSearchRules(searchRuleList));
        webValidators.validate(webPages);

        List<JavaClassBuildable> rawJavaClasses = new ArrayList<>();

        rawJavaClasses.add(new SiteClassBuildable(webPages));

        for (WebPage webPage : webPages) {
            rawJavaClasses.add(new PageClassBuildable(webPage, webElementGroupFieldBuilder));
            if (webPage.isContainedFormSearchRule()) {
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
                            .filter(ValidationResult::isInvalid)
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