package manual.howToCreate.page;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.support.FindBy;

public class HowToCreate extends WebPage {
  @JDropdown(
      root = @FindBy(xpath = "//button[@class='dropbtn' and text()='Click Me']")
  )
  public Dropdown clickMe;
}
