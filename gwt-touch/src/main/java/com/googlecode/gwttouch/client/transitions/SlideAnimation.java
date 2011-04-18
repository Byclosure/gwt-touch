package com.googlecode.gwttouch.client.transitions;

import com.google.gwt.user.client.Element;

abstract class SlideAnimation implements Animation {
	
	static class SlideAnimationConfiguration {
		public Element element;
		public SimpleTransitionPanel panel;
		public String direction;
		public boolean cover;
		public boolean reveal;
		public boolean out;
	}
	
	static class SlideAnimationStyleProperties {
		int fromX;
		int fromY;
		int zIndex;
		int toX;
		int toY;
		
		static SlideAnimationStyleProperties computeSlideAnimationStyleProperties(SlideAnimation.SlideAnimationConfiguration animationConfiguration) {
			int currentZ = "auto".equals(animationConfiguration.element.getStyle().getZIndex()) || 
			                   "".equals(animationConfiguration.element.getStyle().getZIndex()) ? 0 : Integer.parseInt(animationConfiguration.element.getStyle().getZIndex());
			
			int zIndex = currentZ + 1;
			int toX = 0;
			int toY = 0;
			int fromX = 0;
			int fromY = 0;
			
			int elementWidth = animationConfiguration.element.getClientWidth();
			int elementHeight = animationConfiguration.element.getClientHeight();
			
			if ( "left".equals(animationConfiguration.direction) || "right".equals(animationConfiguration.direction) ) {
				if (animationConfiguration.out) {
					toX = -elementWidth;
				} else {
					fromX = elementWidth;
				}
			} else if ( "up".equals(animationConfiguration.direction) || "down".equals(animationConfiguration.direction) ) {
				if ( animationConfiguration.out ) {
					toY = -elementHeight;
				} else {
					fromY = elementHeight;
				}
			}
			
			if ( "right".equals(animationConfiguration.direction) || "down".equals(animationConfiguration.direction) ) {
				toY *= -1;
				toX *= -1;
				fromY *= -1;
				fromX *= -1;
			}
			
			if ( animationConfiguration.cover && animationConfiguration.out ) {
				toX = 0;
				toY = 0;
				zIndex = currentZ;
			} else if ( animationConfiguration.reveal && !animationConfiguration.out ) {
				fromX = 0;
				fromY = 0;
				zIndex = currentZ;
			}
			
			SlideAnimationStyleProperties result = new SlideAnimationStyleProperties();
			result.fromX = fromX;
			result.fromY = fromY;
			result.zIndex = zIndex;
			result.toX = toX;
			result.toY = toY;		
	        return result;		
		}
	}
	
	SlideAnimationConfiguration configuration;
		
    SlideAnimation(SlideAnimationConfiguration configuration) {
    	this.configuration = configuration;
    }
	
	public static Animation newMoveToOriginAnimation(final SlideAnimation.SlideAnimationConfiguration config, final SlideAnimation.SlideAnimationStyleProperties styles){
		return new SlideAnimation(config){
			@Override
			public void run() {
				//System.out.println("STARTING FROM for " + config.element.getId());
				SimpleTransitionPanel.ExecutingAnimation executingAnimation = config.panel.getExecutingAnimationFor(config.element);				
				if ( executingAnimation != null ) {
					config.panel.onTransitionEnd(config.element);
				}
				
				config.panel.removeTransitionEndHandler(config.element, config.panel);
				config.element.getStyle().setProperty("webkitTransform", "translate3d(" + styles.fromX + "px, " + styles.fromY +"px, 0px)");
				config.element.getStyle().setZIndex(styles.zIndex);
				config.element.getStyle().setOpacity(0.99d);
				config.element.getStyle().setProperty("webkitTransitionDuration", "0ms");
				config.element.getStyle().setProperty("webkitTransitionProperty", "all");
				config.element.getStyle().setProperty("webkitTransitionTimingFunction", "ease-in-out");
			}			
		};
	}
	
	
	public static Animation newMoveToDestinationAnimation(final SlideAnimation.SlideAnimationConfiguration config, final SlideAnimation.SlideAnimationStyleProperties styles){
		return new SlideAnimation(config){
			@Override
			public void run() {
				//System.out.println("STARTING TO for " + config.element.getId());
				//config.element.getParentElement().getStyle().setProperty("webkitPerspective", "1200");
				//config.element.getParentElement().getStyle().setProperty("webkitTransformStyle", "preserve-3d");
				
				config.element.getStyle().setProperty("webkitTransitionDuration", "250ms");
				config.element.getStyle().setProperty("webkitTransitionProperty", "all");
				config.element.getStyle().setProperty("webkitTransitionTimingFunction", "ease-in-out");
				
				config.panel.addTransitionEndHandler(config.element, config.panel);
				
				config.element.getStyle().setProperty("webkitTransform", "translate3d(" + styles.toX + "px," + styles.toY + "px, 0px)");
				config.element.getStyle().setZIndex(styles.zIndex);
				config.element.getStyle().setOpacity(1d);			
			}	
		};
	}
}
