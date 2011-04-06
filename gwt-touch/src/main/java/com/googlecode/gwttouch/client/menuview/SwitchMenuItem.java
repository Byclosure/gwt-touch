package com.googlecode.gwttouch.client.menuview;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class SwitchMenuItem extends Composite
	implements HasText, HasName {

	private FlowPanel root = new FlowPanel();
	private Label label = new Label();
	private CheckBox check = new CheckBox();
	
	public SwitchMenuItem() {
		initWidget(root);

		root.setStyleName("gwtTouch-FieldTextItem");
		label.setStyleName("");
		check.setStyleName("gwtTouch-SwitchMenuItem");
		
		root.add(check);
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
		check.setName(name);
	}

	@Override
	public String getName() {
		return check.getName();
	}
}
