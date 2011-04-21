package com.google.gwt.touch.client;

/**
 * Implemented by widgets that support vertical scrolling.
 */
public interface HasVerticalScrolling {

  /**
   * Get the maximum position of vertical scrolling. This is usually the
   * <code>scrollHeight - clientHeight</code>.
   * 
   * @return the maximum vertical scroll position
   */
  int getMaximumVerticalScrollPosition();

  /**
   * Get the minimum position of vertical scrolling.
   * 
   * @return the minimum vertical scroll position
   */
  int getMinimumVerticalScrollPosition();

  /**
   * Gets the vertical scroll position.
   * 
   * @return the vertical scroll position, in pixels
   */
  int getVerticalScrollPosition();

  /**
   * Sets the vertical scroll position.
   * 
   * @param position the new vertical scroll position, in pixels
   */
  void setVerticalScrollPosition(int position);
}