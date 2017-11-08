package com.epam.page.object.generator;

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
	private SearchRuleValidator validator;
	private JavaFileWriter javaFileWriter;
	private String outPutDir;
	private List<String> urls;
	private String packageName;

    private boolean forceGenerateFile = false;

	public PageObjectsGenerator(JsonRuleMapper parser,
								SearchRuleValidator validator,
	                            JavaFileWriter javaFileWriter,
	                            String outPutDir,
								List<String> urls,
	                            String packageName) {
		this.parser = parser;
		this.validator = validator;
		this.javaFileWriter = javaFileWriter;
		this.outPutDir = outPutDir;
		this.urls = urls;
		this.packageName = packageName;
	}

	public void generatePageObjects() throws IOException, URISyntaxException {
		List<SearchRule> searchRules = parser.getRulesFromJSON();

		try {
			validator.validate();
		} catch (ValidationException | NotUniqueSelectorsException ex) {
			if (forceGenerateFile) {
				generateJavaFiles(searchRules);
			}
			throw ex;
		}

		generateJavaFiles(searchRules);
	}

	private void generateJavaFiles(List<SearchRule> searchRules) throws IOException, URISyntaxException {
		javaFileWriter.writeFile(packageName, outPutDir, searchRules, urls);
	}

	public void setForceGenerateFile(boolean forceGenerateFile) {
		this.forceGenerateFile = forceGenerateFile;
	}

}