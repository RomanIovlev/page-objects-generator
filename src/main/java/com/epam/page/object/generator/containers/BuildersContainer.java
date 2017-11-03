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
import com.epam.page.object.generator.builder.FieldAnnotationFactory;
import com.epam.page.object.generator.builder.FieldSpecFactory;
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
		builders.put("button", new FieldsBuilder(new FieldSpecFactory(Button.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("text", new FieldsBuilder(new FieldSpecFactory(Text.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("checkbox", new FieldsBuilder(new FieldSpecFactory(CheckBox.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("image", new FieldsBuilder(new FieldSpecFactory(Image.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("datepicker", new FieldsBuilder(new FieldSpecFactory(DatePicker.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("fileinput", new FieldsBuilder(new FieldSpecFactory(FileInput.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("input", new FieldsBuilder(new FieldSpecFactory(Input.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("label", new FieldsBuilder(new FieldSpecFactory(Label.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("link", new FieldsBuilder(new FieldSpecFactory(Link.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("textarea", new FieldsBuilder(new FieldSpecFactory(TextArea.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("textfield", new FieldsBuilder(new FieldSpecFactory(TextField.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("radiobuttons", new FieldsBuilder(new FieldSpecFactory(RadioButtons.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("selector", new FieldsBuilder(new FieldSpecFactory(Selector.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("tabs", new FieldsBuilder(new FieldSpecFactory(Tabs.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("textlist", new FieldsBuilder(new FieldSpecFactory(TextList.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("checklist", new FieldsBuilder(new FieldSpecFactory(CheckList.class), new FieldAnnotationFactory(FindBy.class)));
		builders.put("elements", new FieldsBuilder(new FieldSpecFactory(Elements.class), new FieldAnnotationFactory(FindBy.class)));

		builders.put("table", new FieldsBuilder(new FieldSpecFactory(Table.class), new FieldAnnotationFactory(JTable.class)));
		builders.put("combobox", new FieldsBuilder(new FieldSpecFactory(ComboBox.class), new FieldAnnotationFactory(JComboBox.class)));
		builders.put("dropdown", new FieldsBuilder(new FieldSpecFactory(Dropdown.class), new FieldAnnotationFactory(JDropdown.class)));
		builders.put("droplist", new FieldsBuilder(new FieldSpecFactory(DropList.class), new FieldAnnotationFactory(JDropList.class)));
		builders.put("menu", new FieldsBuilder(new FieldSpecFactory(Menu.class), new FieldAnnotationFactory(JMenu.class)));
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