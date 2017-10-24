package com.epam.page.object.generator;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.Test;

public class PageObjectsGeneratorTest {

	@Test
	public void test1() throws Exception {
		String validJSONPath = "src/test/resources/valid1.json";
		List<String> urls = new ArrayList<>();

		urls.add("https://www.google.ru");

		PageObjectsGenerator pageObjectGenerator = new PageObjectsGenerator(validJSONPath, urls, "src/test/resources/", "test");

		pageObjectGenerator.generatePageObjects(true);
	}

}