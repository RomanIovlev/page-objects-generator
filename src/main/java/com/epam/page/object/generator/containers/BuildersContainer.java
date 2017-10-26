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
import com.epam.page.object.generator.builder.FieldsBuilder;
import com.epam.page.object.generator.builder.IFieldsBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.support.FindBy;

public class BuildersContainer {

	private Map<String, IFieldsBuilder> builders = new HashMap<>();

	public BuildersContainer() {
		fillBuildersMap();
	}

	private void fillBuildersMap() {
		builders.put("button", new FieldsBuilder(Button.class, FindBy.class));
		builders.put("text", new FieldsBuilder(Text.class, FindBy.class));
		builders.put("checkbox", new FieldsBuilder(CheckBox.class, FindBy.class));
		builders.put("image", new FieldsBuilder(Image.class, FindBy.class));
		builders.put("datepicker", new FieldsBuilder(DatePicker.class, FindBy.class));
		builders.put("fileinput", new FieldsBuilder(FileInput.class, FindBy.class));
		builders.put("input", new FieldsBuilder(Input.class, FindBy.class));
		builders.put("label", new FieldsBuilder(Label.class, FindBy.class));
		builders.put("link", new FieldsBuilder(Link.class, FindBy.class));
		builders.put("textarea", new FieldsBuilder(TextArea.class, FindBy.class));
		builders.put("textfield", new FieldsBuilder(TextField.class, FindBy.class));
		builders.put("radiobuttons", new FieldsBuilder(RadioButtons.class, FindBy.class));
		builders.put("selector", new FieldsBuilder(Selector.class, FindBy.class));
		builders.put("tabs", new FieldsBuilder(Tabs.class, FindBy.class));
		builders.put("textlist", new FieldsBuilder(TextList.class, FindBy.class));
		builders.put("checklist", new FieldsBuilder(CheckList.class, FindBy.class));
		builders.put("elements", new FieldsBuilder(Elements.class, FindBy.class));

		builders.put("table", new FieldsBuilder(Table.class, JTable.class));
		builders.put("combobox", new FieldsBuilder(ComboBox.class, JComboBox.class));
		builders.put("dropdown", new FieldsBuilder(Dropdown.class, JDropdown.class));
		builders.put("droplist", new FieldsBuilder(DropList.class, JDropList.class));
		builders.put("menu", new FieldsBuilder(Menu.class, JMenu.class));
	}

	public void addBuilder(String name, IFieldsBuilder builder) {
		builders.put(name.toLowerCase(), builder);
	}

	public Map<String, IFieldsBuilder> getBuilders() {
		return builders;
	}

	public Set<String> getSupportedTypes() {
		return builders.keySet();
	}

}