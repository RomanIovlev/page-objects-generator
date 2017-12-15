package com.epam.page.object.generator;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args) throws IOException {
        List<String> urls = asList(
            "https://www.w3schools.com/html/html_forms.asp",
            "https://www.w3schools.com/css/default.asp",
            "https://www.w3schools.com/html/html_form_input_types.asp");

        PageObjectsGenerator pog = PageObjectGeneratorFactory.getPageObjectGenerator("w3c.tests",
            "/groups.json", true);
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/webElement.json", "src/main/java/", urls);
    }
}
