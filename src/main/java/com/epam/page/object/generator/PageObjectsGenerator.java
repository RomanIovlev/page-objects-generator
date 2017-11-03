package com.epam.page.object.generator;

import com.epam.page.object.generator.builder.PageFieldSpecBuilder;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class PageObjectsGenerator {

	private JsonRuleMapper parser;
	private JavaFileWriter fileWriter;
	private PageFieldSpecBuilder pageFieldSpecBuilder;
	private SiteFieldSpecBuilder siteFieldSpecBuilder;
	private SearchRuleValidator validator;
	private List<String> urls;
	private String packageName;

	private boolean forceGenerateFile = false;

	public PageObjectsGenerator(JsonRuleMapper parser,
	                            JavaFileWriter fileWriter,
								PageFieldSpecBuilder pageFieldSpecBuilder,
	                            SiteFieldSpecBuilder siteFieldSpecBuilder,
	                            SearchRuleValidator validator,
	                            List<String> urls,
	                            String packageName) {
		this.parser = parser;
		this.fileWriter = fileWriter;
		this.pageFieldSpecBuilder = pageFieldSpecBuilder;
		this.siteFieldSpecBuilder = siteFieldSpecBuilder;
		this.validator = validator;
		this.urls = urls;
		this.packageName = packageName;
	}

	public void generatePageObjects() throws IOException, URISyntaxException {
		List<SearchRule> searchRules = parser.getRulesFromJSON();

		try {
			validator.validate(searchRules, urls);
		} catch (ValidationException | NotUniqueSelectorsException ex) {
			if (forceGenerateFile) {
				generateJavaFiles(searchRules);
			}
			throw ex;
		}

		generateJavaFiles(searchRules);
	}

	private void generateJavaFiles(List<SearchRule> searchRules) throws IOException, URISyntaxException {
		for (String url: urls) {
			fileWriter.write(packageName + ".page", pageFieldSpecBuilder.build(searchRules, url));
		}

		fileWriter.write(packageName + ".site", siteFieldSpecBuilder.build(urls));
	}

	public void setForceGenerateFile(boolean forceGenerateFile) {
		this.forceGenerateFile = forceGenerateFile;
	}

}