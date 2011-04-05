package com.googlecode.gwttouch.client.formview;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;


public class TextField extends Composite implements HasText {

	Label labelField;

	TextBox textBox;

	private String label;

	private String placeholder;

	private String text;

	public TextField(){
		HorizontalPanel horizontalPanel = new HorizontalPanel();

        labelField = new Label();
        labelField.setVisible(false);

        textBox = new TextBox();

        horizontalPanel.add(labelField);
        horizontalPanel.add(textBox);

        initWidget(horizontalPanel);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
		if ( label != null ) {
			labelField.setVisible(true);
			labelField.setText(label);
		}
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		if ( placeholder != null ) {
			DOM.setElementProperty(textBox.getElement(), "placeholder", placeholder);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if ( text != null ) {
			textBox.setText(text);
		}
	}


}
