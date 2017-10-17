package com.epam.page.object.generator;

import org.json.simple.parser.ParseException;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
        List<String> urls = asList(
//            "https://www.w3schools.com/html/html_forms.asp",
//            "https://www.w3schools.com/css/default.asp",
//            "https://www.w3schools.com/html/html_form_input_types.asp",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp"
        );


        new PageObjectsGenerator(
            "src/test/resources/custom.json",
            urls,
            "src/main/java/",
            "w3c.tests")
            .generatePageObjects();
    }
}
