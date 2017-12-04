package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.DuplicateTitleInnerSearchRuleValidator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.RootExistenceValidator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.TitleOfComplexElementValidator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import java.io.IOException;
import java.util.List;
import org.assertj.core.util.Lists;

public class WebValidators {

    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new DuplicateTitleInnerSearchRuleValidator(),
        new RootExistenceValidator(),
        new TitleOfComplexElementValidator()
    );


    public void validate(List<WebPage> webPages)
        throws IOException {


        for (ValidatorVisitor validator : validators) {
            for (WebPage webPage : webPages) {
                for (SearchRule searchRule : webPage.getSearchRules()) {

                }

            }

//                searchRule.accept(validator);
        }
    }



}
