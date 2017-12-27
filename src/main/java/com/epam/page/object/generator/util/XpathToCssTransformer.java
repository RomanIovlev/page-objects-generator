package com.epam.page.object.generator.util;

import com.epam.page.object.generator.error.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Transforms xpath {@link Selector} to css using `cssify.js`
 */
public class XpathToCssTransformer {

    public XpathToCssTransformer() {
    }

    /**
     * Transforms xpath {@link Selector} to css using `cssify.js`
     *
     * @param selector with xpath type
     * @return transformed selector with css type
     * @throws XpathToCssTransformerException when transformation can't be performed
     */
    public Selector getCssSelector(Selector selector) throws XpathToCssTransformerException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        try {
            engine.eval(new FileReader("src/main/resources/cssify.js"));

            Invocable invocable = (Invocable) engine;

            return new Selector("css",
                invocable.invokeFunction("cssify", selector.getValue()).toString());
        } catch (NoSuchMethodException | ScriptException | FileNotFoundException ex) {
            throw new XpathToCssTransformerException(
                "Failed to transform Xpath to Css locator in this selector:"
                    + selector);
        }
    }

}