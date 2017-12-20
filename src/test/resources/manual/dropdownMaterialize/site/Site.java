package manual.dropdownMaterialize.site;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import manual.dropdownMaterialize.page.DropdownMaterialize;

@JSite("materializecss.com")
public class Site extends WebSite {
  @JPage(
      url = "/dropdown.html",
      title = "Dropdown - Materialize"
  )
  public static DropdownMaterialize dropdownMaterialize;
}
