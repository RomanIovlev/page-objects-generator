package com.epam.page.object.generator.util;

import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.error.ValidationException;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.validator.ValidationResult;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationChecker {

    private final static Logger logger = LoggerFactory.getLogger(ValidationChecker.class);

    public void checkRawSearchRules(List<RawSearchRule> rawSearchRules) {
        if (rawSearchRules.stream().anyMatch(RawSearchRule::isInvalid)) {
            StringBuilder stringBuilder = new StringBuilder(
                "\nJson file has invalid search rules:\n");

            rawSearchRules.stream()
                .filter(RawSearchRule::isInvalid)
                .forEach(
                    rawSearchRule -> stringBuilder.append("\n").append(rawSearchRule)
                        .append(" is invalid:\n").append(rawSearchRule.getExceptionMessage()));

            String message = stringBuilder.toString();
            ValidationException validationException = new ValidationException(message);
            logger.error(message, validationException);
            throw validationException;
        }
    }

    public void checkSearchRules(List<SearchRule> searchRuleList) {
        if (searchRuleList.stream().anyMatch(Validatable::isInvalid)) {
            String validationResultString = searchRuleList.stream()
                .filter(Validatable::isInvalid)
                .map(Validatable::getValidationResults)
                .flatMap(Collection::stream)
                .filter(ValidationResult::isInvalid)
                .map(ValidationResult::getReason)
                .collect(Collectors.joining("\n"));

            ValidationException validationException = new ValidationException(
                validationResultString);
            logger.error(validationResultString, validationException);
            throw validationException;
        }
    }

    public void checkWebPages(List<WebPage> webPages, List<JavaClass> javaClasses, String outputDir,
                              JavaFileWriter javaFileWriter, boolean forceGenerateFile)
        throws IOException {
        if (webPages.stream().anyMatch(WebPage::hasInvalidWebElementGroup)) {
            if (forceGenerateFile) {
                logger.info("Start generating JavaClasses...");
                javaFileWriter.writeFiles(outputDir, javaClasses);
                logger.info("Finish generating JavaClasses");
            }

            String validationResultString = webPages.stream()
                .filter(WebPage::hasInvalidWebElementGroup)
                .map(WebPage::getWebElementGroups)
                .flatMap(Collection::stream)
                .map(Validatable::getValidationResults)
                .flatMap(Collection::stream)
                .filter(ValidationResult::isInvalid)
                .map(ValidationResult::getReason)
                .collect(Collectors.joining("\n"));

            ValidationException validationException = new ValidationException(
                validationResultString);
            logger.error(validationResultString, validationException);
            throw validationException;
        }
    }
}