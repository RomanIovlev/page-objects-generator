package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.SearchRule;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class XpathToCssTransformer {

	private XpathToCssTransformer() {

	}

	public static void transformRule(SearchRule searchRule) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

		try {
			engine.eval(new FileReader("src/main/resources/cssify.js"));

			Invocable invocable = (Invocable) engine;

			searchRule.setCss(invocable.invokeFunction("cssify", searchRule.getXpath()).toString());
			searchRule.setXpath(null);
		} catch (NoSuchMethodException | ScriptException | FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}