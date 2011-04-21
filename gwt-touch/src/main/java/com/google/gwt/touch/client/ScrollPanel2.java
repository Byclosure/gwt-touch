/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.touch.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ListenerWrapper;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.ScrollListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesScrollEvents;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple panel that wraps its contents in a scrollable area.
 */
@SuppressWarnings("deprecation")
public class ScrollPanel2 extends SimplePanel implements
    RequiresResize, ProvidesResize, HasScrolling {


  private Element containerElem;

  /**
   * The scroller used to support touch events.
   */
  private TouchScroller touchScroller;

  protected int top = 0;
  protected int left = 0;
  
  /**
   * Creates an empty scroll panel.
   */
  public ScrollPanel2() {

    containerElem = DOM.createDiv();
    getElement().appendChild(containerElem);

    // Prevent IE standard mode bug when a AbsolutePanel is contained.
    DOM.setStyleAttribute(getElement(), "position", "relative");
    DOM.setStyleAttribute(containerElem, "position", "relative");

    // Hack to account for the IE6/7 scrolling bug described here:
    //   http://stackoverflow.com/questions/139000/div-with-overflowauto-and-a-100-wide-table-problem
    DOM.setStyleAttribute(getElement(), "zoom", "1");
    DOM.setStyleAttribute(containerElem, "zoom", "1");

    // Enable touch scrolling.
    setTouchScrollingDisabled(false);
  }

  /**
   * Creates a new scroll panel with the given child widget.
   * 
   * @param child the widget to be wrapped by the scroll panel
   */
  public ScrollPanel2(Widget child) {
    this();
    setWidget(child);
  }

  public HandlerRegistration addScrollHandler(ScrollHandler handler) {
    return addDomHandler(handler, ScrollEvent.getType());
  }

  /**
   * @deprecated Use {@link #addScrollHandler} instead
   */
  @Deprecated
  public void addScrollListener(ScrollListener listener) {
    ListenerWrapper.WrappedScrollListener.add(this, listener);
  }

  /**
   * Ensures that the specified item is visible, by adjusting the panel's scroll
   * position.
   * 
   * @param item the item whose visibility is to be ensured
   */
  public void ensureVisible(UIObject item) {
    Element scroll = getElement();
    Element element = item.getElement();
    ensureVisibleImpl(scroll, element);
  }

  /**
   * Gets the horizontal scroll position.
   * 
   * @return the horizontal scroll position, in pixels
   */
  public int getHorizontalScrollPosition() {
    return left;
  }

  
  public int getMaximumHorizontalScrollPosition() {
    return getWidget().getOffsetWidth();
  }

  public int getMaximumVerticalScrollPosition() {
    return getWidget().getOffsetHeight();
  }

  public int getMinimumHorizontalScrollPosition() {
    return 0;
  }

  public int getMinimumVerticalScrollPosition() {
    return 0;
  }






  /**
   * Gets the vertical scroll position.
   * 
   * @return the vertical scroll position, in pixels
   * @deprecated as of GWT 2.3, replaced by {@link #getVerticalScrollPosition()}
   */
  @Deprecated
  public int getScrollPosition() {
    return top;
  }

  public int getVerticalScrollPosition() {
    return getScrollPosition();
  }

  /**
   * Check whether or not touch based scrolling is disabled. This method always
   * returns false on devices that do not support touch scrolling.
   * 
   * @return true if disabled, false if enabled
   */
  public boolean isTouchScrollingDisabled() {
    return touchScroller == null;
  }

  public void onResize() {
    Widget child = getWidget();
    if ((child != null) && (child instanceof RequiresResize)) {
      ((RequiresResize) child).onResize();
    }
  }





  /**
   * Sets the horizontal scroll position.
   * 
   * @param position the new horizontal scroll position, in pixels
   */
  public void setHorizontalScrollPosition(int position) {
//	  left = position;
//	  getWidget().getElement().getStyle().setLeft(position*-1, Unit.PX);
  }

  /**
   * Sets the vertical scroll position.
   * 
   * @param position the new vertical scroll position, in pixels
   * @deprecated as of GWT 2.3, replaced by
   *             {@link #setVerticalScrollPosition(int)}
   */
  @Deprecated
  public void setScrollPosition(int position) {
	  top = position;
	  getWidget().getElement().getStyle().setTop(position*-1, Unit.PX);
  }



  /**
   * Set whether or not touch scrolling is disabled. By default, touch scrolling
   * is enabled on devices that support touch events.
   * 
   * @param isDisabled true to disable, false to enable
   * @return true if touch scrolling is enabled and supported, false if disabled
   *         or not supported
   */
  public boolean setTouchScrollingDisabled(boolean isDisabled) {
    if (isDisabled == isTouchScrollingDisabled()) {
      return isDisabled;
    }

    if (isDisabled) {
      // Detach the touch scroller.
      touchScroller.setTargetWidget(null);
      touchScroller = null;
    } else {
      // Attach a new touch scroller.
      touchScroller = TouchScroller.createIfSupported(this);
    }
    return isTouchScrollingDisabled();
  }

  public void setVerticalScrollPosition(int position) {
	  top = position;
    setScrollPosition(position);
  }


  @Override
  protected Element getContainerElement() {
    return containerElem;
  }

  private native void ensureVisibleImpl(Element scroll, Element e) /*-{
    if (!e)
      return; 

    var item = e;
    var realOffset = 0;
    while (item && (item != scroll)) {
      realOffset += item.offsetTop;
      item = item.offsetParent;
    }

    scroll.scrollTop = realOffset - scroll.offsetHeight / 2;
  }-*/;

}