package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class XpathToCssTransformation {

    public SearchRule transformRule(SearchRule searchRule) throws XpathToCssTransformerException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            engine.eval(new FileReader("src/main/resources/cssify.js"));

            Invocable invocable = (Invocable) engine;

            searchRule.setCss(invocable.invokeFunction("cssify", searchRule.getXpath()).toString());
            searchRule.setXpath(null);
        } catch (NoSuchMethodException | ScriptException | FileNotFoundException ex) {
            throw new XpathToCssTransformerException("Failed to transform Xpath to Css locator in this search rule:"
                + searchRule);
        }

        return searchRule;
    }

}