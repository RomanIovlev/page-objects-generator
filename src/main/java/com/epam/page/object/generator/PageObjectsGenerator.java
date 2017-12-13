package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.javaclass.IJavaClass;
import com.epam.page.object.generator.adapter.classbuildable.JavaClassBuildable;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.adapter.classbuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.SiteClassBuildable;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.error.ValidationException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.builder.WebPagesBuilder;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.TypeTransformer;
import com.epam.page.object.generator.util.ValidationChecker;
import com.epam.page.object.generator.validator.JsonSchemaValidator;
import com.epam.page.object.generator.validator.JsonValidators;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.WebValidators;
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

    private SelectorUtils selectorUtils;
    private SearchRuleExtractor searchRuleExtractor;

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
                                WebPagesBuilder webPagesBuilder,
                                SelectorUtils selectorUtils,
                                SearchRuleExtractor searchRuleExtractor) {
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
        this.selectorUtils = selectorUtils;
        this.searchRuleExtractor = searchRuleExtractor;
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

        logger.info("Start transforming of RawSearchRules...\n");
        List<SearchRule> searchRuleList = typeTransformer
            .transform(rawSearchRuleList, selectorUtils, searchRuleExtractor);
        logger.info("Finish transforming of RawSearchRules\n");

        logger.info("Start Json validation...\n");
        jsonValidators.validate(searchRuleList);
        logger.info("Finish Json validation\n");

        checker.checkSearchRules(searchRuleList);

        List<WebPage> webPages = webPagesBuilder.generate(urls, searchRuleExtractor);
        webPages.forEach(wp -> wp.addSearchRules(searchRuleList));
        webValidators.validate(webPages);

        List<JavaClassBuildable> rawJavaClasses = getJavaClassBuildables(webPages);

        List<IJavaClass> javaClasses = getJavaClasses(rawJavaClasses);

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

    private List<IJavaClass> getJavaClasses(List<JavaClassBuildable> rawJavaClasses) {
        List<IJavaClass> javaClasses = new ArrayList<>();

        for (JavaClassBuildable javaClass : rawJavaClasses) {
            javaClasses.add(javaClass.accept(javaClassBuilder));
        }
        return javaClasses;
    }

    private List<JavaClassBuildable> getJavaClassBuildables(List<WebPage> webPages) {
        List<JavaClassBuildable> rawJavaClasses = new ArrayList<>();
        rawJavaClasses.add(new SiteClassBuildable(webPages));

        for (WebPage webPage : webPages) {
            rawJavaClasses.add(new PageClassBuildable(webPage, webElementGroupFieldBuilder));
            if (webPage.isContainedFormSearchRule()) {
                rawJavaClasses.addAll(webPage.getFormClasses(selectorUtils));
            }
        }
        return rawJavaClasses;
    }

    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}