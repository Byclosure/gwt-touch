package com.googlecode.gwttouch.client.transitions;

import com.google.gwt.user.client.Element;

public abstract class SlideAnimation implements Animation {
	
	protected com.google.gwt.user.client.Element e;
	protected SimpleTransitionPanel panel;
	protected String direction;
	protected boolean cover;
	protected boolean reveal;
	protected boolean out;
	
	protected int fromX;
	protected int fromY;
	protected int zIndex;
	protected int toX;
	protected int toY;
	
    private SlideAnimation(Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out) {
    	this.e = e;
    	this.panel = panel;
    	this.direction = direction;
    	this.cover = cover;
    	this.reveal = reveal;
    	this.out = out;
    }		
	
	protected void calculateProperties(){
		int currentZ = "auto".equals(e.getStyle().getZIndex()) || "".equals(e.getStyle().getZIndex()) ? 0 : Integer.parseInt(e.getStyle().getZIndex());
		
		zIndex = currentZ + 1;
		toX = 0;
		toY = 0;
		fromX = 0;
		fromY = 0;
		
		int elementWidth = e.getClientWidth();
		int elementHeight = e.getClientHeight();
		
		if ( "left".equals(direction) || "right".equals(direction) ) {
			if (out) {
				toX = -elementWidth;
			} else {
				fromX = elementWidth;
			}
		} else if ( "up".equals(direction) || "down".equals(direction) ) {
			if ( out ) {
				toY = -elementHeight;
			} else {
				fromY = elementHeight;
			}
		}
		
		if ( "right".equals(direction) || "down".equals(direction) ) {
			toY *= -1;
			toX *= -1;
			fromY *= -1;
			fromX *= -1;
		}
		
		if ( cover && out ) {
			toX = 0;
			toY = 0;
			zIndex = currentZ;
		} else if ( reveal && !out ) {
			fromX = 0;
			fromY = 0;
			zIndex = currentZ;
		}
	}
	
	public static Animation newMoveToOriginAnimation(Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out){
		return new SlideAnimation(e, panel, direction, cover, reveal, out){
			@Override
			public void run() {
				calculateProperties();
				panel.removeTransitionEndHandler(e, panel);
				e.getStyle().setProperty("webkitTransform", "translate3d(" + fromX + "px, " + fromY +"px, 0px)");
				e.getStyle().setZIndex(zIndex);
				e.getStyle().setOpacity(0.99d);
				e.getStyle().setProperty("webkitTransitionDuration", "0ms");
				e.getStyle().setProperty("webkitTransitionProperty", "all");
				e.getStyle().setProperty("webkitTransitionTimingFunction", "ease-in-out");
			}			
		};
	}
	
	public static Animation newMoveToDestinationAnimation(Element e, SimpleTransitionPanel panel, String direction, boolean cover, boolean reveal, boolean out){
		return new SlideAnimation(e, panel, direction, cover, reveal, out){
			@Override
			public void run() {
				calculateProperties();
				panel.addTransitionEndHandler(e, panel);
				e.getStyle().setProperty("webkitTransitionDuration", "250ms");
				e.getStyle().setProperty("webkitTransitionProperty", "all");
				e.getStyle().setProperty("webkitTransitionTimingFunction", "ease-in-out");
				e.getStyle().setProperty("webkitTransform", "translate3d(" + toX + "px," + toY + "px, 0px)");
				e.getStyle().setZIndex(zIndex);
				e.getStyle().setOpacity(1d);			
			}	
		};
	}
}
