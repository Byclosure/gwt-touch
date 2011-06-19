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
import com.google.gwt.user.client.Window;
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
	HTMLPanel header;
	@UiField
	FlowPanel hoursListContainer;
	@UiField
	HTMLPanel hoursList;
	@UiField
	FlowPanel listContainer;
	@UiField
	HTMLPanel list;
	
	Map<String,String> hoursListStyleProperties = new HashMap<String, String>();
	
	private IPhoneScroller hoursIScroll;
	private IPhoneScroller iScroll;
	
	private native void prepareIScrollWidth(Element wrapper, int deltaWidth) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var width = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenWidth()();
			width = width + deltaWidth; 
			wrapper.style.width = width + 'px';
		};
		jsFixHeight();
		$wnd.addEventListener(
				'onorientationchange' in $wnd ? 'orientationchange' : 'resize',
				jsFixHeight, false);
	}-*/;
	
	private native void prepareIScrollHeight(Element wrapper, int deltaHeight) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var height = @com.googlecode.gwttouch.client.ui.SafariViewImpl::getAvailableScreenHeight()();
			height = height + deltaHeight; 
			wrapper.style.height = height + 'px';
		};
		jsFixHeight();
		$wnd.addEventListener(
				'onorientationchange' in $wnd ? 'orientationchange' : 'resize',
				jsFixHeight, false);
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

		
		// Hours List
		String hoursStyle = "position:relative;z-index:1;overflow:hidden;height:30px;";
		hoursListContainer.getElement().setAttribute("style", hoursStyle);
		prepareIScrollWidth(hoursListContainer.getElement(), -279);
		
		/*
		IPhoneScrollerConfig hoursIScrollConfig = IPhoneScrollerConfig.getDefault();
		// config.setBounce(true);
		hoursIScrollConfig.setBounceLock(false);
		hoursIScrollConfig.setSnap(false);
		hoursIScrollConfig.setMomentum(true);
		hoursIScrollConfig.setHScrollbar(false);
		hoursIScrollConfig.setVScrollbar(false);
		hoursIScrollConfig.setHScroll(true);
		hoursIScrollConfig.setVScroll(false);
		hoursIScroll = new IPhoneScroller(hoursList, hoursIScrollConfig);
		*/
		
		// Programming Grid
		String style = "position:relative;z-index:1;overflow:hidden;height:100px;";
		listContainer.getElement().setAttribute("style", style);
		prepareIScrollHeight(listContainer.getElement(), -40);
		prepareIScrollWidth(listContainer.getElement(), -279);
		IPhoneScrollerConfig config = IPhoneScrollerConfig.getDefault();
		// config.setBounce(true);
		config.setBounceLock(false);
		config.setSnap(false);
		config.setMomentum(true);
		config.setHScrollbar(false);
		config.setVScrollbar(false);
		config.setHScroll(true);
		config.setVScroll(true);
		
		final Element headerElement = header.getElement();
		PositionCallback pc = new PositionCallback() {
			public void setPosition(int x, int y) {
				headerElement.setInnerText(x + " " + y);
				// hoursIScroll.scrollTo(x, 0);
			}

			@Override
			public void setStyle(String key, String value) { /* Fazer a fun��o s� dedicada ao elemento de dentro */
				hoursListStyleProperties.put(key, value);
				hoursList.getElement().setAttribute("style", styleString(hoursListStyleProperties));
			}
			
			/* Criar callback para o elemento de fora com o mesmo principio */
		};
		iScroll = new IPhoneScroller(list, config, pc);
	}
	
	public static String styleString(Map<String,String> properties) {
		String result = "";
		for(Entry<String, String> pair : properties.entrySet()) {
			String key = pair.getKey();
			String value = null;
			if(pair.getValue().matches(".*translate3d.*")) {
				value = pair.getValue().replaceAll("(.+)\\s*,\\s*(.+)\\s*,\\s*(.+)\\s*", "$1,0,$3"); 
			} else {
				value = pair.getValue();
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
		// hoursIScroll.refresh();
		iScroll.refresh();
	}
}
