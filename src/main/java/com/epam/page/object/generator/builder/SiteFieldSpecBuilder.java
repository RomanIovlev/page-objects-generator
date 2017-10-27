package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SiteFieldSpecBuilder {


	private String packageName;
	private BuildersContainer buildersContainer;
	private JavaFileWriter fileWriter;
	private SearchRuleValidator validator;

	public SiteFieldSpecBuilder(String packageName,
	                            BuildersContainer buildersContainer,
	                            JavaFileWriter fileWriter,
								SearchRuleValidator validator) {
		this.packageName = packageName;
		this.buildersContainer = buildersContainer;
		this.fileWriter = fileWriter;
		this.validator = validator;
	}

	public TypeSpec build(List<String> urls, List<SearchRule> searchRules)
		throws IOException, URISyntaxException {
		List<FieldSpec> siteClassFields = new ArrayList<>();
		for (String url : urls) {
			String titleName = splitCamelCase(getPageTitle(url));
			String pageFieldName = firstLetterDown(titleName);
			String pageClassName = firstLetterUp(titleName);
			ClassName pageClass = createPageClass(pageClassName, searchRules, url);

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

	private ClassName createPageClass(String pageClassName, List<SearchRule> searchRules, String url)
		throws IOException {
		List<FieldSpec> fields = new ArrayList<>();

		for (SearchRule searchRule : searchRules) {
			fields.addAll(buildersContainer.getBuilders().get(searchRule.getType().toLowerCase()).buildField(searchRule, url));
		}

		TypeSpec pageClass = TypeSpec.classBuilder(pageClassName)
				.addModifiers(PUBLIC)
				.superclass(WebPage.class)
				.addFields(fields)
				.build();

		fileWriter.write(packageName, pageClass);

		return ClassName.get(packageName + ".pages", pageClassName);
	}

	private String getDomainName(List<String> urls) throws URISyntaxException {
		return new URI(urls.get(0)).getHost();
	}

	private String getPageTitle(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		return document.title();
	}

	private String getUrlWithoutDomain(String url) throws URISyntaxException {
		return new URI(url).getPath();
	}

}