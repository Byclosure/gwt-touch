package com.googlecode.gwttouch.client.ui;

import us.synx.wc.client.ui.IPhoneScroller;
import us.synx.wc.client.ui.IPhoneScroller.IPhoneScrollerConfig;

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

	@SuppressWarnings("unused")
	private Presenter listener;
	
	@UiField
	FlowPanel listContainer;
	@UiField
	HTMLPanel list;
	
	private IPhoneScroller iScroll;
	
	private native void prepareIScroll(Element wrapper, int deltaHeight, int deltaWidth) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var height = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenHeight()();
			height = height + deltaHeight; 
			wrapper.style.height = height + 'px';
			var width = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenWidth()();
			width = width + deltaWidth; 
			wrapper.style.width = width + 'px';
		};
		jsFixHeight();
		$wnd.addEventListener(
				'onorientationchange' in $wnd ? 'orientationchange' : 'resize',
				jsFixHeight, false);
		// The following is present in the iScroll horizontal example
		// but this would mess up the regular ClickHandlers across the application.
		// $doc.addEventListener('touchstart', function(e){ e.preventDefault(); }, false);
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
		var landscape = !orientationSupported || ($wnd.orientation == 90) || ($wnd.orientation == -90);
		var standalone = ($wnd.navigator.standalone);
		var screenWidth = 1024;
		var screenHeight = 768;
		var topIOSMenuHeight = 20;
		var browserMenuHeight = 60;
		if (landscape) {
			if (standalone) {
				return screenHeight - topIOSMenuHeight;
			}
			else {
				return screenHeight - topIOSMenuHeight - browserMenuHeight;
			}
		} else {
			if (standalone) {
				return screenWidth - topIOSMenuHeight;
			}
			else {
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
		var landscape = !orientationSupported || ($wnd.orientation == 90) || ($wnd.orientation == -90);
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

		String style = "position:relative;z-index:1;overflow:hidden;height:100px;";
		listContainer.getElement().setAttribute("style", style);
		
		prepareIScroll(listContainer.getElement(), -40, -279);
		
		IPhoneScrollerConfig config = IPhoneScrollerConfig.getDefault();
		// config.setBounce(true);
		config.setBounceLock(false);
		config.setSnap(false);
		config.setMomentum(true);
		config.setHScrollbar(false);
		config.setVScrollbar(false);
		config.setHScroll(true);
		config.setVScroll(true);
		iScroll = new IPhoneScroller(list, config);
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
