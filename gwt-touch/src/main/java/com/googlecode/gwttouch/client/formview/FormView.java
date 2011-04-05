package com.googlecode.gwttouch.client.formview;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FormView extends Composite implements HasWidgets {

	final FlowPanel outer;
	final FlowPanel inner;
	final Label header;
	final Label footer;

	public FormView() {
		
        outer = new FlowPanel();
        initWidget(outer);
        
        inner = new FlowPanel();
        header = new Label();
        footer = new Label();
        
        //Ensure the style sheet is injected
        FormViewResources.INSTANCE.css().ensureInjected();
        
        //Set the default style names
        outer.setStyleName("gwtTouch-FormView");
        inner.setStyleName("gwtTouch-FormViewBody");
        header.setStyleName("gwtTouch-HeaderLabel");
        footer.setStyleName("gwtTouch-FooterLabel");
        
        //by default header and footer are hidden
        header.setVisible(false);
        footer.setVisible(false);
        
        outer.add(header);
        outer.add(inner);
        outer.add(footer);
	}

	@Override
	public void add(Widget w) {
		inner.add(w);
	}

	@Override
	public void clear() {
		inner.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return inner.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return inner.remove(w);
	}
	
	public void setHeaderText(String text) {
		boolean hideHeader = (text!=null && !text.isEmpty());
		header.setText(text);
		header.setVisible(hideHeader);
	}
	
	public String getHeaderText() {
		return header.getText();
	}
	
	public void setFooterText(String text) {
		boolean hideFooter = (text!=null && !text.isEmpty());
		footer.setText(text);
		footer.setVisible(hideFooter);
	}
	
	public String getFooterText() {
		return footer.getText();
	}
}
