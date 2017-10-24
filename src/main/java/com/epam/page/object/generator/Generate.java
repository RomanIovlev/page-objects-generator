package com.epam.page.object.generator;

import com.epam.page.object.generator.errors.ValidationException;
import java.util.ArrayList;
import java.util.List;


public class Generate {

    public static void main(String[] args) throws ValidationException {
       /* List<String> urls = asList(
//            "https://www.w3schools.com/html/html_forms.asp",
//            "https://www.w3schools.com/css/default.asp",
//            "https://www.w3schools.com/html/html_form_input_types.asp",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp"
        );

        new PageObjectsGenerator(
            "src/test/resources/new.json",
            urls,
            "src/main/java/",
            "w3c.tests")
            .generatePageObjects(true);
            */

       List<String> strings = new ArrayList<>();
       strings.add("hell");
       strings.add("yeah");

       new Generate().validate(strings);

        System.out.println("AFTER: " + strings);
    }

    private void validate(List<String> strings) {
        strings.removeIf(s -> s.equals("hell"));
    }

}