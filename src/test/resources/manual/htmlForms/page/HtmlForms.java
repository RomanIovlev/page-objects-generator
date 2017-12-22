package manual.htmlForms.page;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;
import manual.htmlForms.form.HtmlForm;

public class HtmlForms extends WebPage {
  @FindBy(
      css = ".w3-example form"
  )
  public HtmlForm htmlForm;
}
