package com.epam.page.object.generator;

import com.epam.jdi.uitests.web.selenium.elements.common.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import com.epam.page.object.generator.builder.FieldsBuilder;
import com.epam.page.object.generator.builder.IFieldsBuilder;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PageObjectsGenerator {
	private JSONIntoRuleParser parser;
	private JavaFileWriter fileWriter;
	private SiteFieldSpecBuilder siteFieldSpecBuilder;
	private List<String> urls;
	private String packageName;
	private SearchRuleValidator validator;
	private boolean forceGenerateFile = false;
	private Map<String, IFieldsBuilder> builders = new HashMap<>();

	public PageObjectsGenerator(JSONIntoRuleParser parser,
	                            JavaFileWriter fileWriter,
	                            SiteFieldSpecBuilder siteFieldSpecBuilder,
	                            SearchRuleValidator validator,
	                            List<String> urls,
	                            String packageName) {
		fillBuildersMap();
		this.siteFieldSpecBuilder = siteFieldSpecBuilder;
		this.parser = parser;
		this.urls = urls;
		this.packageName = packageName;
		this.fileWriter = fileWriter;
		this.validator = validator;
	}

	private void fillBuildersMap() {
		builders.put("button", new FieldsBuilder(Button.class));
		builders.put("text", new FieldsBuilder(Text.class));
		builders.put("checkbox", new FieldsBuilder(CheckBox.class));
		builders.put("image", new FieldsBuilder(Image.class));
		builders.put("datepicker", new FieldsBuilder(DatePicker.class));
		builders.put("fileinput", new FieldsBuilder(FileInput.class));
		builders.put("input", new FieldsBuilder(Input.class));
		builders.put("label", new FieldsBuilder(Label.class));
		builders.put("link", new FieldsBuilder(Link.class));
		builders.put("textarea", new FieldsBuilder(TextArea.class));
		builders.put("textfield", new FieldsBuilder(TextField.class));
		builders.put("radiobuttons", new FieldsBuilder(RadioButtons.class));
		builders.put("selector", new FieldsBuilder(Selector.class));
		builders.put("tabs", new FieldsBuilder(Tabs.class));
		builders.put("textlist", new FieldsBuilder(TextList.class));
		builders.put("table", new FieldsBuilder(Table.class));
		builders.put("checklist", new FieldsBuilder(CheckList.class));
		builders.put("combobox", new FieldsBuilder(ComboBox.class));
		builders.put("dropdown", new FieldsBuilder(Dropdown.class));
		builders.put("droplist", new FieldsBuilder(DropList.class));
		builders.put("elements", new FieldsBuilder(Elements.class));
	}

	public PageObjectsGenerator addBuilder(String name, IFieldsBuilder builder) {
		builders.put(name.toLowerCase(), builder);
		parser.getSupportedTypes().add(name.toLowerCase());

		return this;
	}

	public void generatePageObjects() throws
			IOException, URISyntaxException, ValidationException, NotUniqueSelectorsException {
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
			throws IOException, URISyntaxException, NotUniqueSelectorsException {
		fileWriter.write(packageName, siteFieldSpecBuilder.build(urls, searchRules));
	}

	public void setForceGenerateFile(boolean forceGenerateFile) {
		this.forceGenerateFile = forceGenerateFile;
	}
}