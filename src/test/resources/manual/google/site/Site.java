package manual.google.site;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import manual.google.page.Google;

@JSite("www.google.com")
public class Site extends WebSite {
  @JPage(
      url = "",
      title = "Google"
  )
  public static Google google;
}
