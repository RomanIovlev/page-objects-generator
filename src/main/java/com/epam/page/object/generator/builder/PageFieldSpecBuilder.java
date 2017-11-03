package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageFieldSpecBuilder {

	private BuildersContainer buildersContainer;

	public PageFieldSpecBuilder(BuildersContainer buildersContainer) {
		this.buildersContainer = buildersContainer;
	}

	public TypeSpec build(List<SearchRule> searchRules, String url) throws IOException {
		List<FieldSpec> fields = new ArrayList<>();
		String pageClassName = firstLetterUp(splitCamelCase(getPageTitle(url)));

		for (SearchRule searchRule : searchRules) {
			fields.addAll(buildersContainer.getBuilders().get(searchRule.getType().toLowerCase()).buildField(searchRule, url));
		}

		return TypeSpec.classBuilder(pageClassName)
			.addModifiers(PUBLIC)
			.superclass(WebPage.class)
			.addFields(fields)
			.build();
	}

}