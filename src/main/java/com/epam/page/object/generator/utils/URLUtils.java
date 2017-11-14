package com.epam.page.object.generator.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class URLUtils {

	private URLUtils() {

	}

	public static String getDomainName(List<String> urls) throws URISyntaxException {
		return new URI(urls.get(0)).getHost();
	}

	public static String getPageTitle(String url) throws IOException {
		Document document = Jsoup.connect(url).get();

		return document.title();
	}

	public static String getUrlWithoutDomain(String url) throws URISyntaxException {
		return new URI(url).getPath();
	}

}
