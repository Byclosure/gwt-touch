package com.googlecode.gwttouch.client.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import us.synx.wc.client.ui.IPhoneScroller;
import us.synx.wc.client.ui.IPhoneScroller.IPhoneScrollerConfig;
import us.synx.wc.client.ui.IPhoneScroller.PositionCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class SafariViewImpl extends ResizeComposite implements SafariView {

	private static SafariViewImplUiBinder uiBinder = GWT
			.create(SafariViewImplUiBinder.class);

	interface SafariViewImplUiBinder extends UiBinder<Widget, SafariViewImpl> {
	}

	private Presenter listener;

	@UiField
	FlowPanel hoursListContainer;
	@UiField
	HTMLPanel hoursList;
	@UiField
	HTMLPanel header;
	@UiField
	FlowPanel listContainer;
	@UiField
	HTMLPanel list;
	@UiField
	FlowPanel channelContainer;
	@UiField
	HTMLPanel channelList;


	private Map<String,String> innerStyleProperties = new HashMap<String, String>();
	private IPhoneScroller iScroll;

	private native void prepareIScrollHeight(Element wrapper, int deltaHeight) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var height = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenHeight()();
			height = height + deltaHeight;
			wrapper.style.height = height + 'px';
		};
		$wnd.addEventListener(
				'onorientationchange' in $wnd ? 'orientationchange' : 'resize',
				jsFixHeight, false);
		jsFixHeight();
	}-*/;
	
	private native void prepareIScrollWidth(Element wrapper, int deltaWidth) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var width = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenWidth()();
			width = width + deltaWidth;
			wrapper.style.width = width + 'px';
		};
		$wnd.addEventListener(
				'onorientationchange' in $wnd ? 'orientationchange' : 'resize',
				jsFixHeight, false);
		jsFixHeight();
	}-*/;

	public static int getAvailableScreenHeight() {
		return getAvailableScreenHeight2();
	}

	private static native int getAvailableScreenHeight2() /*-{
		// On Mobile Safari the clientHeight depends on whether the app
		// is loaded from the HomeScreen on inside the browser (due to the space
		// used up by the location bar)
		// Reference:
		// http://stackoverflow.com/questions/2738766/iphone-webapps-is-there-a-way-to-detect-how-it-was-loaded-home-screen-vs-safari
		var orientationSupported = ("onorientationchange" in $wnd);
		var landscape = !orientationSupported || ($wnd.orientation == 90)
				|| ($wnd.orientation == -90);
		var standalone = ($wnd.navigator.standalone);
		var screenWidth = 1024;
		var screenHeight = 768;
		var topIOSMenuHeight = 20;
		var browserMenuHeight = 60;
		if (landscape) {
			if (standalone) {
				return screenHeight - topIOSMenuHeight;
			} else {
				return screenHeight - topIOSMenuHeight - browserMenuHeight;
			}
		} else {
			if (standalone) {
				return screenWidth - topIOSMenuHeight;
			} else {
				return screenWidth - topIOSMenuHeight - browserMenuHeight;
			}
		}
	}-*/;

	public static int getAvailableScreenWidth() {
		return getAvailableScreenWidth2();
	}

	private static native int getAvailableScreenWidth2() /*-{
		// On Mobile Safari the clientHeight depends on whether the app
		// is loaded from the HomeScreen on inside the browser (due to the space
		// used up by the location bar)
		// Reference:
		// http://stackoverflow.com/questions/2738766/iphone-webapps-is-there-a-way-to-detect-how-it-was-loaded-home-screen-vs-safari
		var orientationSupported = ("onorientationchange" in $wnd);
		var landscape = !orientationSupported || ($wnd.orientation == 90)
				|| ($wnd.orientation == -90);
		var standalone = ($wnd.navigator.standalone);
		var screenWidth = 1024;
		var screenHeight = 768;
		if (landscape) {
			return screenWidth;
		} else {
			return screenHeight;
		}
	}-*/;

	public SafariViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		// Channel List
		String channelStyle = "position:relative;z-index:1;overflow:hidden;";
		channelContainer.getElement().setAttribute("style", channelStyle);
		
		// Hours List
		String hoursStyle = "position:relative;z-index:1;overflow:hidden;height:30px;";
		hoursListContainer.getElement().setAttribute("style", hoursStyle);
		prepareIScrollWidth(hoursListContainer.getElement(), 0); /* Full screen width */

		String style = "position:relative;z-index:1;overflow:hidden;height:100px";
		listContainer.getElement().setAttribute("style", style);
		prepareIScrollHeight(listContainer.getElement(), -45-30); /* -header.getOffsetHeight()-listContainer.getElement().getOffsetHeight() */
		prepareIScrollWidth(listContainer.getElement(), -155); /* 155 for the left channel grid */

		PositionCallback pc = new PositionCallback() {
			@Override
			public void setInnerStyle(String key, String value) {
				innerStyleProperties.put(key, value);
				hoursList.getElement().setAttribute("style", styleString(innerStyleProperties, FilterTransition.XX));
				channelList.getElement().setAttribute("style", styleString(innerStyleProperties, FilterTransition.YY));
			}
		};

		IPhoneScrollerConfig config = IPhoneScrollerConfig.getDefault();
		// config.setBounce(true);
		config.setBounceLock(false);
		config.setSnap(false);
		config.setMomentum(true);
		config.setHScrollbar(false);
		config.setVScrollbar(false);
		config.setHScroll(true);
		config.setVScroll(true);

		config.setDesktopCompatibility(true);

		iScroll = new IPhoneScroller(list, config, pc);
	}
	
	private static enum FilterTransition {NONE, YY, XX};
	
	private static String styleString(Map<String, String> properties, FilterTransition filter) {
		String result = "";
		for (Entry<String, String> pair : properties.entrySet()) {
			String key = pair.getKey();
			String value = null;
			// pair.getValue().matches(".*translate3d.*"))
			switch (filter) {
			case XX:
				if (pair.getValue().matches(".*translate3d.*")) {
					value = pair.getValue().replaceAll(
							"\\((.+)\\s*,\\s*(.+)\\s*,\\s*(.+)\\s*\\)", "($1,0,$3)");
				} else {
					value = pair.getValue();
				}
				break;
			case YY:
				if (pair.getValue().matches(".*translate3d.*")) {
					value = pair.getValue().replaceAll(
							"\\((.+)\\s*,\\s*(.+)\\s*,\\s*(.+)\\s*\\)", "(0,$2,$3)");
				} else {
					value = pair.getValue();
				}
				break;
			default: // same as NONE
				value = pair.getValue();
				break;
			}
			result += key + ":" + value + "; ";
		}
		return result;
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void refreshIScroll() {
		iScroll.refresh();
	}
}
