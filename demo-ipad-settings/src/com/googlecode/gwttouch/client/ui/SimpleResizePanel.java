package com.googlecode.gwttouch.client.ui;

import com.googlecode.gwttouch.client.transitions.SimpleTransitionPanel;


public class SimpleResizePanel extends SimpleTransitionPanel {
	
	public SimpleResizePanel() {
		super();
		getElement().getStyle().setProperty("webkitBackfaceVisibility", "hidden");
	}
	
//public class SimpleResizePanel extends SimplePanel
//	implements RequiresResize, ProvidesResize {
//
//	public SimpleResizePanel() {
//		super();
//		dock(this);
//	}
//	
//	@Override
//	public void onResize() {
//		if(getWidget()!=null && getWidget() instanceof RequiresResize) {
//			((RequiresResize)getWidget()).onResize();
//		}
//	}
//
//	@Override
//	public void add(Widget w) {
//		dock(w);
//		super.add(w);
//	}
//
//	@Override
//	public void setWidget(IsWidget w) {
//		dock((Widget)w);
//		super.setWidget(w);
//	}
//
//	@Override
//	public void setWidget(Widget w) {
//		dock(w);
//		super.setWidget(w);
//	}
//
//	void dock(Widget w) {
//		if(w!=null) {
//			w.getElement().getStyle().setPosition(Position.ABSOLUTE);
//			w.getElement().getStyle().setTop(0, Unit.PX);
//			w.getElement().getStyle().setBottom(0, Unit.PX);
//			w.getElement().getStyle().setLeft(0, Unit.PX);
//			w.getElement().getStyle().setRight(0, Unit.PX);
//		}
//	}
}
