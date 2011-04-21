package com.google.gwt.touch.client;

import com.google.gwt.event.dom.client.HasScrollHandlers;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Implemented by widgets that support scrolling.
 */
public interface HasScrolling extends HasHorizontalScrolling,
    HasVerticalScrolling, HasScrollHandlers, IsWidget {

}
