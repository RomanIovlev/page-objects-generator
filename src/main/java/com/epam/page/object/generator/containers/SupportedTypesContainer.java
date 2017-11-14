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
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropList;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JMenu;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JTable;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.support.FindBy;

public class SupportedTypesContainer {

    private Map<String, ClassAndAnnotationPair> supportedTypesMap = new HashMap<>();

    public SupportedTypesContainer() {
        fillSupportedTypesMap();
    }

    private void fillSupportedTypesMap() {
        supportedTypesMap.put("button", new ClassAndAnnotationPair(Button.class, FindBy.class));
        supportedTypesMap.put("text", new ClassAndAnnotationPair(Text.class, FindBy.class));
        supportedTypesMap.put("checkbox", new ClassAndAnnotationPair(CheckBox.class, FindBy.class));
        supportedTypesMap.put("image", new ClassAndAnnotationPair(Image.class, FindBy.class));
        supportedTypesMap
            .put("datepicker", new ClassAndAnnotationPair(DatePicker.class, FindBy.class));
        supportedTypesMap
            .put("fileinput", new ClassAndAnnotationPair(FileInput.class, FindBy.class));
        supportedTypesMap.put("input", new ClassAndAnnotationPair(Input.class, FindBy.class));
        supportedTypesMap.put("label", new ClassAndAnnotationPair(Label.class, FindBy.class));
        supportedTypesMap.put("link", new ClassAndAnnotationPair(Link.class, FindBy.class));
        supportedTypesMap.put("textarea", new ClassAndAnnotationPair(TextArea.class, FindBy.class));
        supportedTypesMap
            .put("textfield", new ClassAndAnnotationPair(TextField.class, FindBy.class));
        supportedTypesMap
            .put("radiobuttons", new ClassAndAnnotationPair(RadioButtons.class, FindBy.class));
        supportedTypesMap.put("selector", new ClassAndAnnotationPair(Selector.class, FindBy.class));
        supportedTypesMap.put("tabs", new ClassAndAnnotationPair(Tabs.class, FindBy.class));
        supportedTypesMap.put("textlist", new ClassAndAnnotationPair(TextList.class, FindBy.class));
        supportedTypesMap
            .put("checklist", new ClassAndAnnotationPair(CheckList.class, FindBy.class));
        supportedTypesMap.put("elements", new ClassAndAnnotationPair(Elements.class, FindBy.class));

        supportedTypesMap.put("table", new ClassAndAnnotationPair(Table.class, JTable.class));
        supportedTypesMap
            .put("combobox", new ClassAndAnnotationPair(ComboBox.class, JComboBox.class));
        supportedTypesMap
            .put("dropdown", new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));
        supportedTypesMap
            .put("droplist", new ClassAndAnnotationPair(DropList.class, JDropList.class));
        supportedTypesMap.put("menu", new ClassAndAnnotationPair(Menu.class, JMenu.class));
    }

    public Map<String, ClassAndAnnotationPair> getSupportedTypesMap() {
        return supportedTypesMap;
    }

    public void addSupportedType(String name, ClassAndAnnotationPair pair) {
        supportedTypesMap.put(name.toLowerCase(), pair);
    }

    public Set<String> getSupportedTypes() {
        return supportedTypesMap.keySet();
    }

}