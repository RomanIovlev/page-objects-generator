package com.epam.page.object.generator;

import com.epam.page.object.generator.adapter.classbuildable.JavaClassBuildable;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.adapter.classbuildable.PageClass;
import com.epam.page.object.generator.adapter.classbuildable.SiteClass;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.builder.webpage.WebPageBuilder;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.TypeTransformer;
import com.epam.page.object.generator.util.ValidationChecker;
import com.epam.page.object.generator.validator.JsonSchemaValidator;
import com.epam.page.object.generator.validator.JsonValidators;
import com.epam.page.object.generator.validator.WebValidators;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class which is needed for generation .java source files
 */
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
    private WebPageBuilder webPageBuilder;

    private SelectorUtils selectorUtils;
    private SearchRuleExtractor searchRuleExtractor;

    private boolean forceGenerateFile = false;

    private final static Logger logger = LoggerFactory.getLogger(PageObjectsGenerator.class);

    /**
     * Constructor
     * @param rawSearchRuleMapper {@link RawSearchRuleMapper}
     * @param jsonSchemaValidator {@link JsonSchemaValidator}
     * @param typeTransformer {@link TypeTransformer}
     * @param checker {@link ValidationChecker}
     * @param jsonValidators {@link JsonValidators}
     * @param webValidators {@link WebValidators}
     * @param javaClassBuilder {@link JavaClassBuilder}
     * @param webElementGroupFieldBuilder {@link WebElementGroupFieldBuilder}
     * @param javaFileWriter {@link JavaFileWriter}
     * @param webPageBuilder {@link WebPageBuilder}
     * @param selectorUtils {@link SelectorUtils}
     * @param searchRuleExtractor {@link SearchRuleExtractor}
     */
    public PageObjectsGenerator(RawSearchRuleMapper rawSearchRuleMapper,
                                JsonSchemaValidator jsonSchemaValidator,
                                TypeTransformer typeTransformer,
                                ValidationChecker checker,
                                JsonValidators jsonValidators,
                                WebValidators webValidators,
                                JavaClassBuilder javaClassBuilder,
                                WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                JavaFileWriter javaFileWriter,
                                WebPageBuilder webPageBuilder,
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
        this.webPageBuilder = webPageBuilder;
        this.selectorUtils = selectorUtils;
        this.searchRuleExtractor = searchRuleExtractor;
    }

    /**
     * Main method which checks and generates page objects
     * @param jsonPath path for input json file, which must starts from resources folder.
     * @param outputDir outputDir is path for the folder where it is needed to generate .java source files.
     * @param urls list of urls which PageObjectsGenerator must visit and tries finding elements on each of them
     * @throws IOException can be thrown from {@link JavaFileWriter#writeFiles(String, List)} if
     * outputDir path doesn't correct.
     */
    public void generatePageObjects(String jsonPath, String outputDir, List<String> urls)
        throws IOException {

        List<RawSearchRule> rawSearchRuleList = getRawSearchRules(jsonPath);
        jsonSchemaValidation(rawSearchRuleList);

        List<SearchRule> searchRuleList = getSearchRules(rawSearchRuleList);
        jsonValidation(searchRuleList);

        List<WebPage> webPages = createWebPages(urls, searchRuleList);
        webValidators.validate(webPages);

        List<JavaClassBuildable> rawJavaClasses = getJavaClassBuildables(webPages);

        List<JavaClass> javaClasses = getJavaClasses(rawJavaClasses);

        checker.checkWebPages(webPages, javaClasses, outputDir, javaFileWriter, forceGenerateFile);

        logger.info("Start generating JavaClasses...");
        javaFileWriter.writeFiles(outputDir, javaClasses);
        logger.info("Finish generating JavaClasses");
    }

    /**
     * Method creates rawSearchRuleList
     * @param jsonPath type String
     * @return rawSearchRuleList
     */
    private List<RawSearchRule> getRawSearchRules(String jsonPath) {
        logger.info("Start creating list of RawSearchRules...");
        List<RawSearchRule> rawSearchRuleList = rawSearchRuleMapper.getRawSearchRuleList(jsonPath);
        logger.info("Finish creating list of RawSearchRules\n");
        return rawSearchRuleList;
    }

    /**
     * Method checks JSON validness
     * @param searchRuleList type List of Search Rule
     */
    private void jsonValidation(List<SearchRule> searchRuleList) {
        logger.info("Start Json validation...\n");
        jsonValidators.validate(searchRuleList);
        logger.info("Finish Json validation\n");

        checker.checkSearchRules(searchRuleList);
    }

    /**
     * Method transforms RawSearchRules in SearchRules
     * @param rawSearchRuleList type List of Raw Search Rule
     * @return SearchRules
     */
    private List<SearchRule> getSearchRules(List<RawSearchRule> rawSearchRuleList) {
        logger.info("Start transforming RawSearchRules in SearchRules...");
        List<SearchRule> searchRuleList = typeTransformer
            .transform(rawSearchRuleList, selectorUtils, searchRuleExtractor);
        logger.info("Finish transforming RawSearchRules in SearchRules\n");
        return searchRuleList;
    }

    /**
     * Method validates RawSearchRules with using Json Schema
     * @param rawSearchRuleList type List of Raw Search Rule
     */
    private void jsonSchemaValidation(List<RawSearchRule> rawSearchRuleList) {
        logger.info("Start validation RawSearchRules with using Json Schema...");
        jsonSchemaValidator.validate(rawSearchRuleList);
        logger.info("Finish validation\n");

        checker.checkRawSearchRules(rawSearchRuleList);
    }

    /**
     * Method creates web pages
     * @param urls type List of String
     * @param searchRuleList type List of Search Rule
     * @return webPages
     */
    private List<WebPage> createWebPages(List<String> urls, List<SearchRule> searchRuleList) {
        logger.info("Start creating web pages...");
        List<WebPage> webPages = webPageBuilder.generate(urls, searchRuleExtractor);
        logger.info("Finish creating web pages\n");

        webPages.forEach(wp -> wp.addSearchRules(searchRuleList));
        return webPages;
    }

    /**
     * Method creates JavaClasses
     * @param javaClassBuildables {@link JavaClassBuildable}
     * @return javaClasses
     */
    private List<JavaClass> getJavaClasses(List<JavaClassBuildable> javaClassBuildables) {
        List<JavaClass> javaClasses = new ArrayList<>();
        logger.info("Start creating JavaClasses...");
        for (JavaClassBuildable javaClassBuildable : javaClassBuildables) {
            javaClasses.add(javaClassBuildable.accept(javaClassBuilder));
        }
        logger.info("Finish creating JavaClasses\n");
        return javaClasses;
    }

    /**
     * Method creates JavaClassBuildables
     * @param webPages type List of Web Page
     * @return rawJavaClasses
     */
    private List<JavaClassBuildable> getJavaClassBuildables(List<WebPage> webPages) {
        List<JavaClassBuildable> rawJavaClasses = new ArrayList<>();
        logger.info("Start creating JavaClassBuildables...");
        logger.debug("Start creating SiteClass...");
        rawJavaClasses.add(new SiteClass(webPages));
        logger.debug("Finish creating SiteClass\n");

        for (WebPage webPage : webPages) {
            logger.debug("Start PageClass for '" + webPage.getTitle() + "' page");
            rawJavaClasses.add(new PageClass(webPage, webElementGroupFieldBuilder));
            logger.debug("Finish creating PageClass\n");
            if (webPage.isContainedFormSearchRule()) {
                rawJavaClasses.addAll(webPage.getFormClasses(selectorUtils));
            }

        }
        logger.info("Finish creating JavaClassBuildables\n");
        return rawJavaClasses;
    }

    /**
     * Constructor is needed for tests and Method Generate
     * @param forceGenerateFile type boolean, if true java files are generated despite on exception
     */
    public void setForceGenerateFile(boolean forceGenerateFile) {
        this.forceGenerateFile = forceGenerateFile;
    }
}