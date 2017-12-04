package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.WebElementGroup;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import com.epam.page.object.generator.validators.searchRuleWebValidators.UniquenessElementExistenceValidator;
import java.io.IOException;
import java.util.List;
import org.assertj.core.util.Lists;

public class WebValidators {

    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new UniquenessElementExistenceValidator()
    );


    public void validate(List<WebPage> webPages)
        throws IOException {

        for (ValidatorVisitor validator : validators) {
            for (WebPage webPage : webPages) {
                for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
                    validator.visit(webElementGroup);
                }
            }
        }
    }
}