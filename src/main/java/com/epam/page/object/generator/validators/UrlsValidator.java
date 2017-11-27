package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.io.IOException;
import org.jsoup.Jsoup;

public class UrlsValidator implements Validator {

    @Override
    public void validate(ValidationContext validationContext) {
        StringBuilder invalidUrls = new StringBuilder();
        for (String url : validationContext.getUrls()) {
            try {
                Jsoup.connect(url).get();
            } catch (IOException | IllegalArgumentException e) {
                invalidUrls.append("\n Not valid url: ").append(url);
            }

        }
        if (invalidUrls.length() > 0) {
            throw new NotValidUrlException(String.valueOf(invalidUrls));
        }
    }

    @Override
    public int getPriority() {
        return 51;
    }

}
