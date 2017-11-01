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
import com.epam.page.object.generator.builder.FieldsBuilder;
import com.epam.page.object.generator.builder.IFieldsBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.support.FindBy;

public class BuildersContainer {

	private Map<String, IFieldsBuilder> builders = new HashMap<>();

	private FieldAnnotationFactory fieldAnnotationFactory;

	public BuildersContainer(FieldAnnotationFactory fieldAnnotationFactory) {
		this.fieldAnnotationFactory = fieldAnnotationFactory;
		fillBuildersMap();
	}

	private void fillBuildersMap() {
		builders.put("button", new FieldsBuilder(Button.class, FindBy.class, fieldAnnotationFactory));
		builders.put("text", new FieldsBuilder(Text.class, FindBy.class, fieldAnnotationFactory));
		builders.put("checkbox", new FieldsBuilder(CheckBox.class, FindBy.class, fieldAnnotationFactory));
		builders.put("image", new FieldsBuilder(Image.class, FindBy.class, fieldAnnotationFactory));
		builders.put("datepicker", new FieldsBuilder(DatePicker.class, FindBy.class, fieldAnnotationFactory));
		builders.put("fileinput", new FieldsBuilder(FileInput.class, FindBy.class, fieldAnnotationFactory));
		builders.put("input", new FieldsBuilder(Input.class, FindBy.class, fieldAnnotationFactory));
		builders.put("label", new FieldsBuilder(Label.class, FindBy.class, fieldAnnotationFactory));
		builders.put("link", new FieldsBuilder(Link.class, FindBy.class, fieldAnnotationFactory));
		builders.put("textarea", new FieldsBuilder(TextArea.class, FindBy.class, fieldAnnotationFactory));
		builders.put("textfield", new FieldsBuilder(TextField.class, FindBy.class, fieldAnnotationFactory));
		builders.put("radiobuttons", new FieldsBuilder(RadioButtons.class, FindBy.class, fieldAnnotationFactory));
		builders.put("selector", new FieldsBuilder(Selector.class, FindBy.class, fieldAnnotationFactory));
		builders.put("tabs", new FieldsBuilder(Tabs.class, FindBy.class, fieldAnnotationFactory));
		builders.put("textlist", new FieldsBuilder(TextList.class, FindBy.class, fieldAnnotationFactory));
		builders.put("checklist", new FieldsBuilder(CheckList.class, FindBy.class, fieldAnnotationFactory));
		builders.put("elements", new FieldsBuilder(Elements.class, FindBy.class, fieldAnnotationFactory));

		builders.put("table", new FieldsBuilder(Table.class, JTable.class, fieldAnnotationFactory));
		builders.put("combobox", new FieldsBuilder(ComboBox.class, JComboBox.class, fieldAnnotationFactory));
		builders.put("dropdown", new FieldsBuilder(Dropdown.class, JDropdown.class, fieldAnnotationFactory));
		builders.put("droplist", new FieldsBuilder(DropList.class, JDropList.class, fieldAnnotationFactory));
		builders.put("menu", new FieldsBuilder(Menu.class, JMenu.class, fieldAnnotationFactory));
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