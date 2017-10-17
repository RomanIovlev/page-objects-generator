package com.epam.page.object.generator;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
        List<String> urls = asList(
            "https://www.w3schools.com/html/html_forms.asp",
            "https://www.w3schools.com/css/default.asp",
            "https://www.w3schools.com/html/html_form_input_types.asp");

        new PageObjectsGenerator(
            "src/test/resources/custom.json",
            urls,
            "src/main/java/",
            "w3c.tests")
            .generatePageObjects();
    }
}
