package com.googlecode.gwttouch.client.transitions;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class SimpleTransitionPanel extends Composite implements AcceptsOneWidget{
	
	private FlowPanel root = new FlowPanel();
	
	private IsWidget currentlyViewedWidget;
	
	public SimpleTransitionPanel() {
		initWidget(root);
		root.getElement().getStyle().setHeight(100, Unit.PCT);
	}
	
	private boolean isBetweenChangeFromOneViewToAnother(IsWidget w){
		return w == null && root.getWidgetCount() > 0;
	}
	
	private boolean isTransitionToANewWidget(IsWidget w){
		return w != null && root.getWidgetCount() >= 1;
	}
	
	@Override
	public void setWidget(IsWidget w) {
		if (currentlyViewedWidget == w || isBetweenChangeFromOneViewToAnother(w)) {
			return;
		} else if ( isTransitionToANewWidget(w) ) {
			root.add(w);
			animateWidgets();
			currentlyViewedWidget = w;
		} else if ( w != null && root.getWidgetCount() == 0){
			root.add(w);
			currentlyViewedWidget = w;
		}
	}
	
	private void animateWidgets(){
		root.getElement().getStyle().setHeight(100, Unit.PCT);
		
		Widget newCard = root.getWidget(root.getWidgetCount() - 1);
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		Animation from = SlideAnimation.newMoveToOriginAnimation(newCard.getElement(), this, "right", false, false, false);
		from.run();
		
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		final Animation to = SlideAnimation.newMoveToDestinationAnimation(newCard.getElement(), this, "right", false, false, false);
		Timer t = new Timer() {
		      public void run() {
		    	  to.run();
		      }
		};
		t.schedule(5);
		
		Widget oldCard = root.getWidget(Math.max(root.getWidgetCount() - 2, 0));
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		Animation oldCardFrom = SlideAnimation.newMoveToOriginAnimation(oldCard.getElement(), this, "right", false, false, true);
		oldCardFrom.run();
		
		//Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out
		final Animation oldCardTo = SlideAnimation.newMoveToDestinationAnimation(oldCard.getElement(), this, "right", false, false, true);
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
}
