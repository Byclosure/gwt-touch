package com.googlecode.gwttouch.client.menuview;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class TwoLineMenuItem extends Composite
	implements HasText {

	private Label label = new Label();
	
	public TwoLineMenuItem() {
		initWidget(label);
		label.setStyleName("gwtTouch-FieldTextItem");
	}
	
	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}

}
