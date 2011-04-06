package com.googlecode.gwttouch.client.menuview;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioMenuItem extends Composite implements HasText, HasName {

	private FlowPanel root = new FlowPanel();
	private final Label label = new Label();
	private final RadioButton radio;
	
	@UiConstructor
	public RadioMenuItem(String name) {
		radio = new RadioButton(name);
		initWidget(root);

		root.setStyleName("gwtTouch-FieldTextItem");
		label.setStyleName("");
		radio.setStyleName("gwtTouch-FieldCheckItem");
		
		root.add(radio);
		root.add(label);
	}
	
	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}

	@Override
	public void setName(String name) {
		radio.setName(name);
	}

	@Override
	public String getName() {
		return radio.getName();
	}

}
