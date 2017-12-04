package com.epam.page.object.generator;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args)
        throws IOException, URISyntaxException, XpathToCssTransformerException {
//        List<String> urls = asList(
//            "https://www.w3schools.com/html/html_forms.asp",
//            "https://www.w3schools.com/css/default.asp",
//            "https://www.w3schools.com/html/html_form_input_types.asp");
//
//        new Generate().initPog(
//            "src/test/resources/webElement.json",
//            urls,
//            "src/main/java/",
//            "w3c.tests",
//            false,
//            true)
//            .generatePageObjects();
    }

//    private PageObjectsGenerator initPog(String jsonPath,
//                                         List<String> urls,
//                                         String outputDir,
//                                         String packageName,
//                                         boolean checkLocatorUniqueness,
//                                         boolean forceGenerateFiles) throws IOException {
//
//        SupportedTypesContainer bc = new SupportedTypesContainer();
//
//        JsonRuleMapper parser = new JsonRuleMapper(new File(jsonPath), new ObjectMapper());
//
//        XpathToCssTransformation xpathToCssTransformation = new XpathToCssTransformation();
//
//        JavaFileWriter javaFileWriter = new JavaFileWriter(bc, xpathToCssTransformation);
//
//        JsonValidators validatorsStarter = new JsonValidators(bc);
//        validatorsStarter.setCheckLocatorsUniqueness(checkLocatorUniqueness);
//
//        PageObjectsGenerator pog = new PageObjectsGenerator(parser, validatorsStarter,
//            javaFileWriter, outputDir, urls, packageName);
//
//        pog.setForceGenerateFile(forceGenerateFiles);
//
//        return pog;
//    }
}
