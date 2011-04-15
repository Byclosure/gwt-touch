package com.googlecode.gwttouch.client.transitions;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class SimpleTransitionPanel extends Composite implements AcceptsOneWidget, RequiresResize, ProvidesResize{
	
	private FlowPanel root = new FlowPanel();
	
	public SimpleTransitionPanel() {
		initWidget(root);
//		root.getElement().getStyle().setHeight(100, Unit.PCT);
		//TODO: discuss this change w/ Carlos, and why I did it
		dock(root);
	}
	
	private boolean isBetweenChangeFromOneViewToAnother(IsWidget w){
		return w == null && root.getWidgetCount() > 0;
	}
	
	private boolean isTransitionToANewWidget(IsWidget w){
		return w != null && root.getWidgetCount() >= 1;
	}
	
	@Override
	public void setWidget(IsWidget w) {

		dock(w);
		
		if (isBetweenChangeFromOneViewToAnother(w)) {

			return;
		} else if ( isTransitionToANewWidget(w) ) {
			root.add(w);
			animateWidgets();
		} else if ( w != null && root.getWidgetCount() == 0){
			root.add(w);
		}
	}
	
	private void animateWidgets(){
		root.getElement().getStyle().setHeight(100, Unit.PCT);
		if ( root.getWidgetCount() > 2 ) {
			root.remove(0);
		}
		Widget newCard = root.getWidget(root.getWidgetCount() - 1);
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		Animation from = SlideAnimation.newMoveToOriginAnimation(newCard.getElement(), this, "left", false, false, false);
		from.run();
		
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		final Animation to = SlideAnimation.newMoveToDestinationAnimation(newCard.getElement(), this, "left", false, false, false);
		Timer t = new Timer() {
		      public void run() {
		    	  to.run();
		      }
		};
		t.schedule(5);
		
		Widget oldCard = root.getWidget(Math.max(root.getWidgetCount() - 2, 0));
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		Animation oldCardFrom = SlideAnimation.newMoveToOriginAnimation(oldCard.getElement(), this, "left", false, false, true);
		oldCardFrom.run();
		
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		final Animation oldCardTo = SlideAnimation.newMoveToDestinationAnimation(oldCard.getElement(), this, "left", false, false, true);
		Timer oldCardTimer = new Timer() {
		      public void run() {
		    	  oldCardTo.run();
		      }
		};
		oldCardTimer.schedule(5);
	}
	
	public final native void addTransitionEndHandler(final com.google.gwt.user.client.Element e, final SimpleTransitionPanel containerPanel) /*-{
		$wnd['carlitosHandler'] = function(event) {
	     	if ('left' === event.propertyName ) {
	     	   e.removeEventListener('webkitTransitionEnd', this, false);
	     	   containerPanel.@com.googlecode.gwttouch.client.transitions.SimpleTransitionPanel::onTransitionEnd(Ljava/lang/String;)('ignored lame parameter');
	     	} 
	 	};
	    e.addEventListener('webkitTransitionEnd', $wnd['carlitosHandler'], false);
    }-*/;
	
	public final native void removeTransitionEndHandler(final com.google.gwt.user.client.Element e, final SimpleTransitionPanel containerPanel) /*-{
        if ( typeof $wnd['carlitosHandler'] != 'undefined' ) {
            e.removeEventListener('webkitTransitionEnd', $wnd['carlitosHandler'], this, false);
        } 		
    }-*/;	
	
	public void onTransitionEnd(String e){
	}

	void dock(IsWidget w) {
		if(w instanceof Widget) {
			dock((Widget)w);
		}
	}
	
	@Override
	public void onResize() {
		if(getWidget()!=null && getWidget() instanceof RequiresResize) {
			((RequiresResize)getWidget()).onResize();
		}
	}
	
	void dock(Widget w) {
		if(w!=null) {
			w.getElement().getStyle().setPosition(Position.ABSOLUTE);
			w.getElement().getStyle().setTop(0, Unit.PX);
			w.getElement().getStyle().setBottom(0, Unit.PX);
			w.getElement().getStyle().setLeft(0, Unit.PX);
			w.getElement().getStyle().setRight(0, Unit.PX);
		}
	}
}
