package com.epam.page.object.generator.validator;

import static org.junit.Assert.*;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.databuilder.WebPageTestDataBuilder;
import com.epam.page.object.generator.validator.web.ElementUniquenessValidator;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class WebValidatorsTest {

    private int COUNT_VALIDATORS = 2;

    private WebValidators webValidators = new WebValidators();
    private ElementUniquenessValidator validator = new ElementUniquenessValidator();

    @Test
    public void addValidator() {

        assertEquals(COUNT_VALIDATORS, webValidators.getValidators().size());
        webValidators.addValidator(validator);
        assertEquals(COUNT_VALIDATORS + 1, webValidators.getValidators().size());
    }

    @Test
    public void addValidatorsList() {
        assertEquals(COUNT_VALIDATORS, webValidators.getValidators().size());
        webValidators.addValidatorsList(Lists.newArrayList(validator, validator));
        assertEquals(COUNT_VALIDATORS + 2, webValidators.getValidators().size());
    }

    @Test
    public void createWebValidatorsWithCustomValidators() {
        assertEquals(COUNT_VALIDATORS, webValidators.getValidators().size());
        webValidators = new WebValidators(Lists.newArrayList(validator));
        assertEquals(COUNT_VALIDATORS + 1, webValidators.getValidators().size());
    }

    @Test
    public void validate(){
        WebPage jdiWebPage = WebPageTestDataBuilder.getJdiWebPage();
        List<WebPage> webPages = Lists.newArrayList(jdiWebPage);

        webValidators.validate(webPages);

        assertTrue(jdiWebPage.getWebElementGroups().stream().allMatch(Validatable::isValid));
    }

}