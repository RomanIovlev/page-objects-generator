package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.io.IOException;
import org.jsoup.Jsoup;

public class UrlsValidator implements Validator {

    @Override
    public void validate(ValidationContext validationContext) {
        for (String url : validationContext.getUrls()) {
            try {
                Jsoup.connect(url).get();
            } catch (IOException | IllegalArgumentException e) {
                throw new NotValidUrlException("Not valid url: " + url);
            }

        }
    }

    @Override
    public int getPriority() {
        return 51;
    }

    @Override
    public String getExceptionMessage() {
        return null;
    }
}
