package com.epam.page.object.generator;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class PageObjectsGeneratorTest {

	@Test
	public void buttonTest() throws Exception {
		String validJSONPath = "src/test/resources/button.json";
		List<String> urls = new ArrayList<>();

		urls.add("https://www.google.ru");

		PageObjectsGenerator pageObjectGenerator = new PageObjectsGenerator(validJSONPath, urls, "src/test/resources/", "test");

		pageObjectGenerator.generatePageObjects();
	}

	@Test
	public void dropDownTest() throws Exception {
		String validJSONPath = "src/test/resources/dropdown.json";
		List<String> urls = new ArrayList<>();

		urls.add("https://www.w3schools.com/howto/howto_js_dropdown.asp");

		PageObjectsGenerator pageObjectGenerator = new PageObjectsGenerator(validJSONPath, urls,
			"src/test/resources/", "test");

		pageObjectGenerator.setCheckLocatorsUniqueness(true);
		pageObjectGenerator.setForceGenerateFile(false);

		pageObjectGenerator.generatePageObjects();
	}

}