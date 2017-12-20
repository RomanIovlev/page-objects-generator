package manual.htmlForms.form;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import org.openqa.selenium.support.FindBy;

public class HtmlForm extends Form {
  @FindBy(
      css = "input[type=text][name='firstname']"
  )
  public TextField firstname;

  @FindBy(
      css = "input[type=text][name='lastname']"
  )
  public TextField lastname;

  @FindBy(
      css = "input[type=submit][value='Submit']"
  )
  public Button submit;
}
