package com.epam.page.object.generator.model;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRule {

    private String type;

    @JsonProperty("name")
    private String requiredAttribute;

	private String css;
	private String xpath;
	private List<SearchRule> innerSearchRules;

    public SearchRule() {

	}

    public SearchRule(String type, String requiredAttribute, String css, String xpath,
        List<SearchRule> innerSearchRules) {
        this.type = type;
        this.requiredAttribute = requiredAttribute;
        this.css = css;
        this.xpath = xpath;
        this.innerSearchRules = innerSearchRules;
    }

    public List<String> extractRequiredValuesFromFoundElements(String url) throws IOException {
        if (css == null) {
        	return Xsoup.compile(xpath).evaluate(getURLConnection(url)).list();
		} else {
			return requiredAttribute.equals("text")
				? extractElementsFromWebSiteByCss(url).eachText()
				: extractElementsFromWebSiteByCss(url).eachAttr(requiredAttribute);
		}
    }

    private Elements extractElementsFromWebSiteByCss(String url) throws IOException {
        Document document = getURLConnection(url);

        return document.select(css);
    }

    private boolean hasInnerRules() {
		return !innerSearchRules.isEmpty();
    }

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequiredAttribute() {
		return requiredAttribute;
	}

	public void setRequiredAttribute(String requiredAttribute) {
		this.requiredAttribute = requiredAttribute;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public List<SearchRule> getInnerSearchRules() {
		return innerSearchRules;
	}

	public void setInnerSearchRules(List<SearchRule> innerSearchRules) {
        this.innerSearchRules = innerSearchRules;
    }

    @Override
    public String toString() {
        return "SearchRule{" +
            "type='" + type + '\'' +
            ", requiredAttribute='" + requiredAttribute + '\'' +
            ", css='" + css + '\'' +
            ", xpath='" + xpath + '\'' +
            ", innerSearchRules=" + innerSearchRules +
            '}';
    }

}