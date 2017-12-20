package manual.howToCreate.site;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import manual.howToCreate.page.HowToCreate;

@JSite("www.w3schools.com")
public class Site extends WebSite {
  @JPage(
      url = "/howto/howto_js_dropdown.asp",
      title = "How To Create a Dropdown Menu With CSS and JavaScript"
  )
  public static HowToCreate howToCreate;
}
