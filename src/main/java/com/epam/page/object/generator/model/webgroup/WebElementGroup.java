package com.epam.page.object.generator.model.webgroup;

import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.classbuildable.PageClass;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.model.webelement.WebElement;
import java.util.List;

/**
 * Represents group of web-page elements.
 *
 * @see CommonWebElementGroup
 * @see ComplexWebElementGroup
 * @see FormWebElementGroup
 */
public interface WebElementGroup extends Validatable {

    /**
     * Returns {@link SearchRule} which was used for searching elements of {@link WebElementGroup}
     *
     * @return {@link SearchRule} which was used for searching elements of {@link WebElementGroup}
     */
    SearchRule getSearchRule();

    /**
     * Returns list of {@link WebElement} of current group
     *
     * @return list of {@link WebElement} of current group
     */
    List<WebElement> getWebElements();

    /**
     * Returns list of {@link JavaField} that will be used during building {@link JavaClass} from
     * {@link PageClass}.
     *
     * @param webElementGroupFieldBuilder instance of {@link WebElementGroupFieldBuilder}
     * @return list of {@link JavaField} for current web group.
     */
    List<JavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                           String packageName);
}

