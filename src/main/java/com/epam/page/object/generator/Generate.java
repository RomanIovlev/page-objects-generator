package com.epam.page.object.generator;

import static java.util.Arrays.asList;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args)
        throws IOException, URISyntaxException, XpathToCssTransformerException {
        List<String> urls = asList(
            "https://www.w3schools.com/html/html_forms.asp",
            "https://www.w3schools.com/css/default.asp",
            "https://www.w3schools.com/html/html_form_input_types.asp");

        PageObjectsGenerator pog = PageObjectGeneratorFactory.getPageObjectGenerator("w3c.tests");
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/webElement.json", "src/main/java/", urls);
    }
}
