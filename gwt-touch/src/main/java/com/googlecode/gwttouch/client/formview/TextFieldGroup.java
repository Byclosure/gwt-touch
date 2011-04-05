package com.googlecode.gwttouch.client.formview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextFieldGroup extends Composite implements HasWidgets{

	interface Binder extends UiBinder<FlexTable, TextFieldGroup> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private String label;

	@UiField
	FlexTable fieldGroupTable;

	private List<TextBox> fields = new ArrayList<TextBox>();

	public TextFieldGroup(){
		initWidget(binder.createAndBindUi(this));
		setLabel(null);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
		if ( label != null ) {
			fieldGroupTable.setVisible(true);
		}
	}

	@Override
	public void add(Widget w) {
		if ( w instanceof com.google.gwt.user.client.ui.TextBox) {
			fields.add((TextBox)w);
		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		boolean labelSet = label != null && label.trim().length() > 0;

		int widgetRow = 0;
		int fieldsColumn = 0;//FIRST
		if ( labelSet ) {

			fieldGroupTable.setWidget(widgetRow, 0, new Label(label));
			fieldsColumn = fieldsColumn + 1;
		}

		for(TextBox field : fields){
			fieldGroupTable.setWidget(widgetRow++, fieldsColumn, field);
		}

	}

	@Override
	public void clear() {
	}

	@Override
	public Iterator<Widget> iterator() {
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		return false;
	}
}
