package com.epam.page.object.generator;

import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class PageObjectsGenerator {

	private JSONIntoRuleParser parser;
	private JavaFileWriter fileWriter;
	private SiteFieldSpecBuilder siteFieldSpecBuilder;
	private List<String> urls;
	private String packageName;
	private SearchRuleValidator validator;

	private boolean forceGenerateFile = false;

	public PageObjectsGenerator(JSONIntoRuleParser parser,
	                            JavaFileWriter fileWriter,
	                            SiteFieldSpecBuilder siteFieldSpecBuilder,
	                            SearchRuleValidator validator,
	                            List<String> urls,
	                            String packageName) {
		this.siteFieldSpecBuilder = siteFieldSpecBuilder;
		this.parser = parser;
		this.urls = urls;
		this.packageName = packageName;
		this.fileWriter = fileWriter;
		this.validator = validator;
	}

	public void generatePageObjects()
		throws IOException, URISyntaxException {
		List<SearchRule> searchRules = parser.getRulesFromJSON();

		try {
			validator.validate(searchRules);
		} catch (ValidationException ex) {
			if (forceGenerateFile) {
				generateJavaFiles(searchRules);
			}
			throw ex;
		}

		generateJavaFiles(searchRules);
	}

	private void generateJavaFiles(List<SearchRule> searchRules)
			throws IOException, URISyntaxException {
		fileWriter.write(packageName, siteFieldSpecBuilder.build(urls, searchRules));
	}

	public void setForceGenerateFile(boolean forceGenerateFile) {
		this.forceGenerateFile = forceGenerateFile;
	}

}