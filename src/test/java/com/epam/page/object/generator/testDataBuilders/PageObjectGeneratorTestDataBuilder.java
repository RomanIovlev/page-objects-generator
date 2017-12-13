package com.epam.page.object.generator.testDataBuilders;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.adapter.classbuildable.JavaClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.SiteClassBuildable;
import com.epam.page.object.generator.adapter.classes.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.WebPage;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.models.webgroup.WebElementGroup;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SelectorUtils;
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

public class PageObjectGeneratorTestDataBuilder{

    private RawSearchRuleMapper rawSearchRuleMapper;
    private JsonSchemaValidator jsonSchemaValidator;
    private TypeTransformer typeTransformer;
    private ValidationChecker checker;
    private JsonValidators jsonValidators;
    private WebValidators webValidators;
    private JavaClassBuilder javaClassBuilder;
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder;
    private JavaFileWriter javaFileWriter;
    private WebPageTestDataBuilder webPagesBuilder;

    private SelectorUtils selectorUtils;
    private SearchRuleExtractor searchRuleExtractor;

    private boolean forceGenerateFile = false;

    private final static Logger logger = LoggerFactory.getLogger(PageObjectsGenerator.class);

    public PageObjectGeneratorTestDataBuilder(RawSearchRuleMapper rawSearchRuleMapper,
                                              JsonSchemaValidator jsonSchemaValidator,
                                              TypeTransformer typeTransformer,
                                              ValidationChecker checker,
                                              JsonValidators jsonValidators,
                                              WebValidators webValidators,
                                              JavaClassBuilder javaClassBuilder,
                                              WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                              JavaFileWriter javaFileWriter,
                                              WebPageTestDataBuilder webPagesBuilder,
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

    public void generatePageObjects(String jsonPath, String outputDir, List<String> paths)
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

        List<WebPage> webPages = webPagesBuilder.generate(paths, searchRuleExtractor);
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
