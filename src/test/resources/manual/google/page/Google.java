package manual.google.page;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

public class Google extends WebPage {
    @FindBy(
        css = "input[type=submit][value='Поиск в Google']"
    )
    public Button поискВGoogle;

    @FindBy(
        css = "input[type=submit][value='Мне повезёт!']"
    )
    public Button мнеПовезёт;
}
