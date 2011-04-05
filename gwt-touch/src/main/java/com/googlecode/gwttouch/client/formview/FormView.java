package com.googlecode.gwttouch.client.formview;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormView extends Composite implements HasWidgets {

	private static FormViewUiBinder uiBinder = GWT.create(FormViewUiBinder.class);

	interface FormViewUiBinder extends UiBinder<Widget, FormView> { }

	@UiField
	VerticalPanel main;

	public FormView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		main.add(w);
	}

	@Override
	public void clear() {
		main.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return main.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return main.remove(w);
	}
}
