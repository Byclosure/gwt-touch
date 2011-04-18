package com.googlecode.gwttouch.client.transitions;

import java.util.HashMap;
import java.util.Map;

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
import com.google.gwt.user.client.Element;

public class SimpleTransitionPanel extends Composite implements AcceptsOneWidget, RequiresResize, ProvidesResize{
	
	private FlowPanel root = new FlowPanel();
	
	private IsWidget currentlyViewedPanel = null;
	
	private Map<Element, ExecutingAnimation> runningAnimations  = new HashMap<Element, ExecutingAnimation>();
	
	public SimpleTransitionPanel() {
		initWidget(root);
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
		
		if (currentlyViewedPanel == w || isBetweenChangeFromOneViewToAnother(w)) {
			return;
		}
		
		if ( isTransitionToANewWidget(w) ) {
			root.add(w);
			animateWidgets();
		} else if ( w != null && root.getWidgetCount() == 0){
			root.add(w);
		}
		currentlyViewedPanel = w;
	}
	
	private void animateWidgets(){
		root.getElement().getStyle().setHeight(100, Unit.PCT);
		if ( root.getWidgetCount() > 2 ) {
			root.remove(0);				
		}
		Widget newCard = root.getWidget(root.getWidgetCount() - 1);
		animateCard(newCard, "left", false, false,false);
		
		Widget oldCard = root.getWidget(Math.max(root.getWidgetCount() - 2, 0));
		animateCard(oldCard, "left", false, false, true);		
	}
	
	private void animateCard(Widget card, String direction, boolean cover, boolean reveal, boolean out) {
		SlideAnimation.SlideAnimationConfiguration cardAnimationConfiguration = new SlideAnimation.SlideAnimationConfiguration();
		cardAnimationConfiguration.element = card.getElement();
		cardAnimationConfiguration.panel = this;
		cardAnimationConfiguration.direction = direction;
		cardAnimationConfiguration.cover = cover;
		cardAnimationConfiguration.reveal = reveal;
		cardAnimationConfiguration.out = out;
		SlideAnimation.SlideAnimationStyleProperties cardAnimationStyleConfig = 
			SlideAnimation.SlideAnimationStyleProperties.computeSlideAnimationStyleProperties(cardAnimationConfiguration);
		
		Animation cardFrom = SlideAnimation.newMoveToOriginAnimation(cardAnimationConfiguration, cardAnimationStyleConfig);
		cardFrom.run();
		
		final Animation cardTo = SlideAnimation.newMoveToDestinationAnimation(cardAnimationConfiguration, cardAnimationStyleConfig);
		Timer cardToTimer = new Timer() {
		      public void run() {
		    	  cardTo.run();
		      }
		};
		cardToTimer.schedule(5);
		
		ExecutingAnimation newCardExecutingAnimation = new ExecutingAnimation();
		newCardExecutingAnimation.from = cardFrom;
		newCardExecutingAnimation.to = cardTo;
		runningAnimations.put(card.getElement(), newCardExecutingAnimation);		
	}	
	
	public final native void removeTransitionEndHandler(final com.google.gwt.user.client.Element e, final SimpleTransitionPanel containerPanel) /*-{
    if ( typeof $wnd['com.googlecode.gwttouch.transitions.internal.SimpleTransitionPanel.transitionHandler'] != 'undefined' ) {
        e.removeEventListener('webkitTransitionEnd', $wnd['com.googlecode.gwttouch.transitions.internal.SimpleTransitionPanel.transitionHandler'], this, false);
    } 		
    }-*/;		
	
	public final native void addTransitionEndHandler(final com.google.gwt.user.client.Element e, final SimpleTransitionPanel containerPanel) /*-{
		$wnd['com.googlecode.gwttouch.transitions.internal.SimpleTransitionPanel.transitionHandler'] = function(event) {
	    	containerPanel.@com.googlecode.gwttouch.client.transitions.SimpleTransitionPanel::onTransitionEnd(Lcom/google/gwt/user/client/Element;)(e);
	 	};
	    e.addEventListener('webkitTransitionEnd', $wnd['com.googlecode.gwttouch.transitions.internal.SimpleTransitionPanel.transitionHandler'], false);
    }-*/;
	
	public void onTransitionEnd(Element e){
		if ( runningAnimations.get(e) == null ) {
			//System.out.println("CLEARING UP RETURNING... " + e.getId());
			return;
		}
		//e.getStyle().setProperty("zIndex", "");
		//e.getStyle().setProperty("opacity", "");
		e.getStyle().setProperty("webkitTransitionDuration", "");
		e.getStyle().setProperty("webkitTransitionProperty", "");
		e.getStyle().setProperty("webkitTransitionTimingFunction", "");
		//e.getStyle().setProperty("webkitTransform", "");
		
		//e.getParentElement().getStyle().setProperty("webkitPerspective", "");
		//e.getParentElement().getStyle().setProperty("webkitTransformStyle", "");
		
		runningAnimations.remove(e);
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

	ExecutingAnimation getExecutingAnimationFor(Element e){
		return runningAnimations.get(e);
	}
	
	static class ExecutingAnimation {
		Animation from;
		Animation to;
	}
}
