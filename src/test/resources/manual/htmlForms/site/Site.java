package manual.htmlForms.site;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import manual.htmlForms.page.HtmlForms;

@JSite("www.w3schools.com")
public class Site extends WebSite {
  @JPage(
      url = "/html/html_forms.asp",
      title = "HTML Forms"
  )
  public static HtmlForms htmlForms;
}
