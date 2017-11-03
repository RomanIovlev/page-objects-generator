package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getDomainName;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static com.epam.page.object.generator.utils.URLUtils.getUrlWithoutDomain;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SiteFieldSpecBuilder {


	private String packageName;

	public SiteFieldSpecBuilder(String packageName) {
		this.packageName = packageName;
	}

	public TypeSpec build(List<String> urls) throws IOException, URISyntaxException {
		List<FieldSpec> siteClassFields = new ArrayList<>();

		for (String url : urls) {
			String titleName = splitCamelCase(getPageTitle(url));
			String pageFieldName = firstLetterDown(titleName);
			String pageClassName = firstLetterUp(titleName);
			ClassName pageClass = getPageClassName(pageClassName);

			siteClassFields.add(FieldSpec.builder(pageClass, pageFieldName)
					.addModifiers(PUBLIC, STATIC)
					.addAnnotation(AnnotationSpec.builder(JPage.class)
							.addMember("url", "$S", getUrlWithoutDomain(url))
							.addMember("title", "$S", getPageTitle(url))
							.build())
					.build());
		}

		return TypeSpec.classBuilder("Site")
				.addModifiers(PUBLIC)
				.addAnnotation(AnnotationSpec.builder(JSite.class)
						.addMember("domain", "$S", getDomainName(urls))
						.build())
				.superclass(WebSite.class)
				.addFields(siteClassFields)
				.build();
	}

	private ClassName getPageClassName(String pageClassName) {
		return ClassName.get(packageName + ".page", pageClassName);
	}

}