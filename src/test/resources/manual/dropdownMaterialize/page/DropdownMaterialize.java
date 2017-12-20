package manual.dropdownMaterialize.page;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import org.openqa.selenium.support.FindBy;

public class DropdownMaterialize extends WebPage {
    @JDropdown(
            root = @FindBy(xpath = "//a[@class='dropdown-button' and text()='Drop Me!']"),
            list = @FindBy(css = "ul.dropdown-content")
    )
    public Dropdown dropMe;
}