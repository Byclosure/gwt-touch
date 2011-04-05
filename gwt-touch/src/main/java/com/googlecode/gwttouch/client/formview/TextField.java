package com.googlecode.gwttouch.client.formview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;


public class TextField extends Composite {

	interface Binder extends UiBinder<HorizontalPanel, TextField> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Label labelField;

	@UiField
	TextBox textBox;

	private String label;

	private String placeholder;

	private String text;

	public TextField(){
		initWidget(binder.createAndBindUi(this));
		labelField.setVisible(false);
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
