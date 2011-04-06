package com.googlecode.gwttouch.client.menuview;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class MenuView extends Composite implements HasWidgets.ForIsWidget {

	private final FlowPanel panel = new FlowPanel();
	
	public MenuView() {
		MenuViewResources.INSTANCE.css().ensureInjected();
		initWidget(panel);
		panel.setStyleName("gwtTouch-MenuLayout");
	}




	@Override
	public void add(Widget w) {
		panel.add(w);
	}

	@Override
	public void clear() {
		panel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return panel.remove(w);
	}

	@Override
	public void add(IsWidget w) {
		panel.add(w);
	}

	@Override
	public boolean remove(IsWidget w) {
		return panel.remove(w);
	}
}