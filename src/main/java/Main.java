import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.builder.FieldAnnotationFactory;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.google.com");

        String outputDir = "src/test/resources/";
        String packageName = "test";

        String jsonPath = "src/test/resources/button.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(jsonPath);
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(true);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
            fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
            siteFieldSpecBuilder, validator, urls, packageName);


        pog.generatePageObjects();
    }
}
