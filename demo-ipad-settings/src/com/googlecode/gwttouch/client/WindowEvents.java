package com.googlecode.gwttouch.client;

import com.google.gwt.dom.client.Element;

/**
 * Class with some handy event listeners
 * 
 * @author davide
 * 
 */
public class WindowEvents {

	public interface OrientationChangeHandler {
		abstract void onOrientationChange(int orientation);
	}

	public native static boolean supportsOrientationChange() /*-{
		return "onorientationchange" in $wnd;
	}-*/;

	public native static void addOrientationListener(OrientationChangeHandler handler) /*-{
		if (@com.googlecode.gwttouch.client.WindowEvents::supportsOrientationChange()) {
			var callback = function() {
				handler.@com.googlecode.gwttouch.client.WindowEvents$OrientationChangeHandler::onOrientationChange(I)($wnd.orientation);
			}
			$wnd.addEventListener("orientationchange", callback, false);
		}
	}-*/;

	public native static Integer getOrientation() /*-{
		if (@com.googlecode.gwttouch.client.WindowEvents::supportsOrientationChange()) {
			return $wnd.orientation;
		}
		return null;
	}-*/;

}
