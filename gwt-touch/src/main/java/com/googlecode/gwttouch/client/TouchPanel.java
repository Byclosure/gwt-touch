package com.googlecode.gwttouch.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class TouchPanel extends SimplePanel implements RequiresResize, ProvidesResize {

	//In Android, OverScroller
	class ScrollBar {
		
		DivElement elem;
		double locationY;
		double maxHeight;
		double height;
		
		public ScrollBar() {
			elem = DOM.createDiv().cast();
			elem.setClassName("touchScrollBar-hide");
		}
		void show() {
			elem.setClassName("touchScrollBar-show");
		}
		void hide() {
			elem.setClassName("touchScrollBar-hide");
		}
		void calcLocationY() {
			maxHeight = getOffsetHeight();
			height = ((double)getOffsetHeight() / (double)getWidget().getOffsetHeight()) * (double)getOffsetHeight();
			
			locationY = (double)getOffsetHeight() *  ((double)scrollY / (double)getWidget().getOffsetHeight()) * -1;
			if(locationY<1) {
				height = height + locationY;
				height = Math.max(height, 10d);
				locationY = 0d;
			}
			else if(locationY + height >= maxHeight) {
				height = height - (locationY + height - maxHeight);
				height = Math.max(height, 10);
				locationY = maxHeight - height;
			} else {
				
			}
			elem.getStyle().setTop(locationY,Unit.PX);
			elem.getStyle().setHeight((int)height, Unit.PX);
		}
		void calcMaxHeight() {
			maxHeight = getOffsetHeight();
		}
		void calcHeight() {
			height = ((double)getOffsetHeight() / (double)getWidget().getOffsetHeight()) * (double)getOffsetHeight();
			elem.getStyle().setHeight((int)height, Unit.PX);
		}
	}
	
	
	private ScrollBar scroller;
	protected boolean moved = false;
	protected boolean scrolling = false;
	protected boolean momentum = false;
	protected boolean elastic = false;
	protected int scrollX = 0;
	protected int scrollY = 0;
	protected int movedX = 0;
	protected int movedY = 0;
	protected int touchX = 0;
	protected int touchY = 0;
	protected int maxHeight = 0;

	public TouchPanel() {
		sinkEvents(Event.TOUCHEVENTS | Event.MOUSEEVENTS);
		scroller = new ScrollBar();
		getElement().appendChild(scroller.elem);
	}

	@Override
	public void onBrowserEvent(Event e) {
		

		switch(e.getTypeInt()) {
//		case Event.ONMOUSEOVER :
//		case Event.ONMOUSEOUT :e.stopPropagation(); break;
//		case Event.ONDBLCLICK :
//		case Event.ONCLICK : System.out.println("onclick");e.stopPropagation(); break;
	
		
		case Event.ONMOUSEDOWN :
		case Event.ONTOUCHSTART : onTouchStart(e); break;
		
		
		case Event.ONMOUSEUP :
		case Event.ONTOUCHEND :
		case Event.ONTOUCHCANCEL : onTouchEnd(e); break;
		
		
		case Event.ONMOUSEMOVE :
		case Event.ONTOUCHMOVE : onTouchMove(e); break;
		
//		default : break;
		}
		

		
		super.onBrowserEvent(e);
	}

	@Override
	public void setWidget(Widget widget) {
		widget.getElement().getStyle().setPosition(Position.ABSOLUTE);
		widget.getElement().getStyle().setTop(0, Unit.PX);
		widget.getElement().getStyle().setLeft(0, Unit.PX);
		widget.getElement().getStyle().setRight(0, Unit.PX);
		super.setWidget(widget);
		onResize();
	}

	@Override
	public void add(Widget widget) {
	    if (getWidget() != null) {
	        throw new IllegalStateException(
	        		"SimplePanel can only contain one child widget");
	      }
	      this.setWidget(widget);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		onResize();
	}
	
	protected void onTouchEnd(Event event) {
		

		event.preventDefault();
//		event.stopPropagation();
		
		if (!scrolling) {
			return;
		}
		
		scrolling = false;
		JsArray<Touch> touches = event.getTouches();
		
		if (moved) {
			// Ease back to within boundaries
			if (scrollY > 0 || scrollY < maxHeight) {
				//TODO: rebound scroll
				//rebound scroll???
				reboundScroll();
				
			} else if (momentum) {
				//TODO: scroll with momentum
				//scroll with momentum
			} else {
				scroller.hide();
			}
			

			
		} else {
			
//			event.preventDefault();
//			event.stopPropagation();
			
//TODO: figurie out what this code does
//			// Dispatch a fake click event if this touch event did not move
//			var touch = touches[0],
//				target = touch.target,
//				me = document.createEvent('MouseEvent');
//
//			while (target.nodeType !== 1) {
//				target = target.parentNode;
//			}
//			me.initMouseEvent('click', true, true, touch.view, 1, touch.screenX, touch.screenY, touch.clientX, touch.clientY, false, false, false, false, 0, null);
//			target.dispatchEvent(me);
			Element target = DOM.eventGetTarget(event);
			
			NativeEvent clickEvent =
				Document.get().createMouseEvent("click",true, true,0, event.getScreenX(), event.getScreenY(), event.getClientX(), event.getClientY(), false, false, false, false, NativeEvent.BUTTON_LEFT, target);

			if(event.getTypeInt()==Event.ONTOUCHEND) {
				target.focus();
				target.dispatchEvent(clickEvent);
//				target.focus();
			} else if(!"INPUT".equals(target.getNodeName()) && 
					!"SELECT".equals(target.getNodeName()) &&
					!"A".equals(target.getNodeName()) ) {
				
				target.dispatchEvent(clickEvent);
				
			} else {
				target.focus();
			}

			scroller.hide();
			
		}
	}
	
	protected void onTouchStart(Event e) {
		
//		Element target = DOM.eventGetTarget(e);
//		if("INPUT".equals(target.getNodeName())) {
//			return;
//		}
		
			e.preventDefault();
			e.stopPropagation();

			JsArray<Touch> touches = e.getTouches();

			maxHeight = Math.abs(this.getOffsetHeight()-getWidget().getOffsetHeight())*-1;


			
			scroller.calcLocationY();
			scroller.show();
			
			
			scrolling = true;
			moved = false;
			movedY = 0;
			touchY = 0;

			//TODO: add transition timout
//			clearTimeout(timeoutID);
//			setTransitionTime(0);
//			$this.css('-webkit-transition-duration', time + 'ms');
			

			// Check scroll position
//			if (momentum) {
//				int y = getPosition();
//				if (y != scrollY) {
//					setPosition(y);
//					moved = true;
//				}
//			}

			if(touches!=null) {
				touchY = touches.get(0).getPageY() - scrollY;
			} else {
				touchY = e.getClientY() - scrollY;
			}
		
	}

	protected void onTouchMove(Event e) {
		
		if (!scrolling) {
			return;
		}
		

		e.preventDefault();
//		e.stopPropagation();
		
//		scroller.calcLocationY();

		JsArray<Touch> touches = e.getTouches();
		int dy;
		if(touches!=null) {
			dy = touches.get(0).getPageY() - touchY;
		} else {
			dy = e.getClientY() - touchY;
		}
		

		
		
		// Elastic-drag or stop when moving outside of boundaries
//		if (dy > 0) {
//			if (elastic) {
//				dy /= 2;
//			} else {
//				dy = 0;
//			}
//		} else if (dy < maxHeight) {
//			if (elastic) {
//				dy = (dy + maxHeight) / 2;
//			} else {
//				dy = maxHeight;
//			}
//		}
//System.out.println(movedY + " = " + dy + " - " + scrollY);
		//we divide by 2 to slow things down
//		dy/=2;
		movedY = dy - scrollY;
		moved = true;
		setPosition(dy);

		scroller.calcLocationY();
	}
	
	protected void setPosition(int y) {
		this.scrollY = y;
		this.scrollTo(y, 0);
//		$this.css('-webkit-transform', cssTranslate(scrollY));
	}
	
	//TODO: figure out WTF getPosition is
	protected int getPosition() {
		return scrollY;
	}
	
	protected void scrollTo(int y, int reboundTime) {
		getWidget().getElement().getStyle().setTop(y, Unit.PX);
	}
	
	protected void reboundScroll() {
		if (scrollY > 0) {
			scrollY=0;
			scrollTo(0, 0);
		} 
		else if (scrollY < maxHeight) {
			scrollY=maxHeight;
			scrollTo(maxHeight, 0);
		}

		scroller.calcLocationY();
		scroller.hide();
	}
	
	protected int getMaxHeight() {
		return Math.abs(this.getOffsetHeight()-getWidget().getOffsetHeight())*-1;
	}

	@Override
	public void onResize() {
		if(getWidget()!=null) {
			maxHeight = getMaxHeight();
			reboundScroll();
			scroller.calcLocationY();
		}
	}
}
