package com.epam.page.object.generator.container;

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
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.util.SearchRuleType;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.support.FindBy;

public class SupportedTypesContainer {

    private Map<String, ClassAndAnnotationPair> supportedTypesMap = new HashMap<>();

    public SupportedTypesContainer() {
        fillSupportedTypesMap();
    }

    private void fillSupportedTypesMap() {
        supportedTypesMap.put(SearchRuleType.BUTTON.getName(),
            new ClassAndAnnotationPair(Button.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXT.getName(),
            new ClassAndAnnotationPair(Text.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.CHECKBOX.getName(),
            new ClassAndAnnotationPair(CheckBox.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.IMAGE.getName(),
            new ClassAndAnnotationPair(Image.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.DATEPICKER.getName(),
                new ClassAndAnnotationPair(DatePicker.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.FILEINPUT.getName(),
                new ClassAndAnnotationPair(FileInput.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.INPUT.getName(),
            new ClassAndAnnotationPair(Input.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.LABEL.getName(),
            new ClassAndAnnotationPair(Label.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.LINK.getName(),
            new ClassAndAnnotationPair(Link.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXTAREA.getName(),
            new ClassAndAnnotationPair(TextArea.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.TEXTFIELD.getName(),
                new ClassAndAnnotationPair(TextField.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.RADIOBUTTONS.getName(),
                new ClassAndAnnotationPair(RadioButtons.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.SELECTOR.getName(),
            new ClassAndAnnotationPair(Selector.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TABS.getName(),
            new ClassAndAnnotationPair(Tabs.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TEXTLIST.getName(),
            new ClassAndAnnotationPair(TextList.class, FindBy.class));
        supportedTypesMap
            .put(SearchRuleType.CHECKLIST.getName(),
                new ClassAndAnnotationPair(CheckList.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.ELEMENTS.getName(),
            new ClassAndAnnotationPair(Elements.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.TABLE.getName(),
            new ClassAndAnnotationPair(Table.class, JTable.class));
        supportedTypesMap
            .put(SearchRuleType.COMBOBOX.getName(),
                new ClassAndAnnotationPair(ComboBox.class, JComboBox.class));
        supportedTypesMap
            .put(SearchRuleType.DROPDOWN.getName(),
                new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));
        supportedTypesMap
            .put(SearchRuleType.DROPLIST.getName(),
                new ClassAndAnnotationPair(DropList.class, JDropList.class));
        supportedTypesMap.put(SearchRuleType.MENU.getName(),
            new ClassAndAnnotationPair(Menu.class, JMenu.class));
        supportedTypesMap.put(SearchRuleType.SECTION.getName(),
            new ClassAndAnnotationPair(Section.class, FindBy.class));
        supportedTypesMap.put(SearchRuleType.FORM.getName(),
            new ClassAndAnnotationPair(Form.class, FindBy.class));
    }

    public Map<String, ClassAndAnnotationPair> getSupportedTypesMap() {
        return supportedTypesMap;
    }

    public void addSupportedType(String name, ClassAndAnnotationPair pair) {
        supportedTypesMap.put(name.toLowerCase(), pair);
    }
}