package com.googlecode.gwttouch.client.formview;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextBox;

public class TextFieldItem extends TextBox {

	private String placeholder;

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		if ( placeholder != null ) {
			DOM.setElementProperty(this.getElement(), "placeholder", placeholder);
		}
	}
}
