package com.epam.page.object.generator.containers;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.CheckBox;
import com.epam.jdi.uitests.web.selenium.elements.common.DatePicker;
import com.epam.jdi.uitests.web.selenium.elements.common.FileInput;
import com.epam.jdi.uitests.web.selenium.elements.common.Image;
import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.common.Link;
import com.epam.jdi.uitests.web.selenium.elements.common.Text;
import com.epam.jdi.uitests.web.selenium.elements.common.TextArea;
import com.epam.jdi.uitests.web.selenium.elements.common.TextField;
import com.epam.jdi.uitests.web.selenium.elements.complex.CheckList;
import com.epam.jdi.uitests.web.selenium.elements.complex.ComboBox;
import com.epam.jdi.uitests.web.selenium.elements.complex.DropList;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.complex.Elements;
import com.epam.jdi.uitests.web.selenium.elements.complex.Menu;
import com.epam.jdi.uitests.web.selenium.elements.complex.RadioButtons;
import com.epam.jdi.uitests.web.selenium.elements.complex.Selector;
import com.epam.jdi.uitests.web.selenium.elements.complex.Tabs;
import com.epam.jdi.uitests.web.selenium.elements.complex.TextList;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.Table;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;
import com.epam.page.object.generator.model.UIElement;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.support.FindBy;

public class SupportedTypesContainer {

    private Map<String, UIElement> supportedTypesMap = new HashMap<>();

    public SupportedTypesContainer() {
        fillSupportedTypesMap();
    }

    private void fillSupportedTypesMap() {
        supportedTypesMap.put(SearchRuleType.BUTTON.getName(),
            new UIElement(Button.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXT.getName(),
            new UIElement(Text.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.CHECKBOX.getName(),
            new UIElement(CheckBox.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.IMAGE.getName(),
            new UIElement(Image.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.DATEPICKER.getName(),
                new UIElement(DatePicker.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.FILEINPUT.getName(),
                new UIElement(FileInput.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.INPUT.getName(),
            new UIElement(Input.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.LABEL.getName(),
            new UIElement(Label.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.LINK.getName(),
            new UIElement(Link.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXTAREA.getName(),
            new UIElement(TextArea.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.TEXTFIELD.getName(),
                new UIElement(TextField.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.RADIOBUTTONS.getName(),
                new UIElement(RadioButtons.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.SELECTOR.getName(),
            new UIElement(Selector.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TABS.getName(),
            new UIElement(Tabs.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXTLIST.getName(),
            new UIElement(TextList.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.CHECKLIST.getName(),
                new UIElement(CheckList.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.ELEMENTS.getName(),
            new UIElement(Elements.class, FindBy.class));

        supportedTypesMap.put(SearchRuleType.TABLE.getName(),
            new UIElement(Table.class, JTable.class));
        supportedTypesMap
            .put(SearchRuleType.COMBOBOX.getName(),
                new UIElement(ComboBox.class, JComboBox.class));
        supportedTypesMap
            .put(SearchRuleType.DROPDOWN.getName(),
                new UIElement(Dropdown.class, JDropdown.class));
        supportedTypesMap
            .put(SearchRuleType.DROPLIST.getName(),
                new UIElement(DropList.class, JDropList.class));
        supportedTypesMap.put(SearchRuleType.MENU.getName(),
            new UIElement(Menu.class, JMenu.class));
        supportedTypesMap.put(SearchRuleType.SECTION.getName(),
            new UIElement(Section.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.FORM.getName(),
            new UIElement(Form.class, FindBy.class));
    }

    public Map<String, UIElement> getSupportedTypesMap() {
        return supportedTypesMap;
    }

    public void addSupportedType(String name, UIElement pair) {
        supportedTypesMap.put(name.toLowerCase(), pair);
    }

    public Set<String> getSupportedTypes() {
        return supportedTypesMap.keySet();
    }

}