package com.googlecode.gwttouch.client.formview;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;


public class TextField extends Composite implements HasText {

	final Label label;
	final SimplePanel labelWrapper;
	final TextBox textBox;

	public TextField(){
		
		final FlowPanel root = new FlowPanel();
		root.setStyleName("gwtTouch-TextField");
		
        //Ensure the style sheet is injected
        FormViewResources.INSTANCE.css().ensureInjected();
		
        //Create the Label, hide, and add style
        labelWrapper = new SimplePanel();
        labelWrapper.setVisible(false);
        labelWrapper.setStyleName("gwtTouch-TextFieldLabel");
        
        label = new Label();
        label.setStyleName("");
        labelWrapper.add(label);
        
        //Create textBox, remove default GWT style
        textBox = new TextBox();
        textBox.setStyleName("");

        root.add(textBox);
        root.add(labelWrapper);

        initWidget(root);
	}


	public String getPlaceholder() {
		return DOM.getElementProperty(textBox.getElement(), "placeholder");
	}

	public void setPlaceholder(String placeholder) {
		placeholder = (placeholder==null)?"":placeholder;
		DOM.setElementProperty(textBox.getElement(), "placeholder", placeholder);
	}

	@Override
	public String getText() {
		return textBox.getText();
	}

	@Override
	public void setText(String text) {
		textBox.setText(text);
	}
	
	public void setLabel(String text) {
		boolean showLabel = (text!=null && !text.isEmpty());
		label.setText(text);
		labelWrapper.setVisible(showLabel);
		
		//TODO: 35 is a magic number, should be configurable
		if(showLabel) {
			labelWrapper.getElement().getStyle().setWidth(35, Unit.PCT);
			textBox.getElement().getStyle().setLeft(35, Unit.PCT);
		} else {
			textBox.getElement().getStyle().setLeft(0, Unit.PCT);
		}
	}
	
	public String getLabel() {
		return label.getText();
	}
	
	public void setEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}
}
