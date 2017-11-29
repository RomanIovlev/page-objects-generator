package com.epam.page.object.generator;

import static java.util.Arrays.asList;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.ParseException;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Generate {

    // EXAMPLE
    public static void main(String[] args)
        throws ParseException, IOException, URISyntaxException, XpathToCssTransformerException {
        List<String> urls = asList(
            "https://www.w3schools.com/html/html_forms.asp",
            "https://www.w3schools.com/css/default.asp",
            "https://www.w3schools.com/html/html_form_input_types.asp");

        new Generate().initPog(
            "src/test/resources/webElement.json",
            urls,
            "src/main/java/",
            "w3c.tests",
            false,
            true)
            .generatePageObjects();
    }

    private PageObjectsGenerator initPog(String jsonPath, List<String> urls,
                                         String outputDir,
                                         String packageName,
                                         boolean checkLocatorUniqueness,
                                         boolean forceGenerateFiles) throws IOException {

        SupportedTypesContainer bc = new SupportedTypesContainer();

        JsonRuleMapper parser = new JsonRuleMapper(new File(jsonPath), new ObjectMapper());

        XpathToCssTransformation xpathToCssTransformation = new XpathToCssTransformation();

        JavaFileWriter javaFileWriter = new JavaFileWriter(bc, xpathToCssTransformation);

        ValidatorsStarter validatorsStarter = new ValidatorsStarter(bc);
        validatorsStarter.setCheckLocatorsUniqueness(checkLocatorUniqueness);

        PageObjectsGenerator pog = new PageObjectsGenerator(parser, validatorsStarter,
            javaFileWriter, outputDir, urls, packageName);

        pog.setForceGenerateFile(forceGenerateFiles);

        return pog;
    }
}
