package com.googlecode.gwttouch.client;

import java.util.List;

import us.synx.wc.client.ui.IPhoneScroller;
import us.synx.wc.client.ui.IPhoneScroller.IPhoneScrollerConfig;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.gwttouch.client.ui.SimpleResizePanel;

public class SettingsViewImpl extends ResizeComposite {

	private static SettingsViewUiBinder uiBinder = GWT
			.create(SettingsViewUiBinder.class);

	interface SettingsViewUiBinder extends UiBinder<Widget, SettingsViewImpl> {
	}
	

	
	static final class SettingsCategoryCell extends AbstractCell<SettingsCategory> {
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				SettingsCategory value, SafeHtmlBuilder sb) {
			
			sb.appendHtmlConstant("<div class='touchListViewItem'>");
			sb.appendHtmlConstant("<div>");
			sb.appendHtmlConstant("<img src=\""+value.getImage().getURL()+"\" style=\"margin-top: 10px !IMPORTANT; margin-right: 10px !IMPORTANT; float: left;\">");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</div></div>");
		}
	}

	//@UiField TouchPanel touchPanel;
	@UiField SimpleResizePanel centerPanel;	

	SingleSelectionModel<SettingsCategory> selectionModel;
	ListDataProvider<SettingsCategory> dataProvider =
		new ListDataProvider<SettingsCategory>();
	
	private IPhoneScroller iScroll;
	
	private native void prepareIScroll(Element wrapper, int deltaHeight) /*-{
		var javaObj = this;
		var jsFixHeight = function() {
			var height = @com.googlecode.gwttouch.client.SettingsViewImpl::getAvailableScreenHeight()();
			height = height + deltaHeight; 
			wrapper.style.height = height + 'px';
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
	
	public SettingsViewImpl() {

		initWidget(uiBinder.createAndBindUi(this));
/*
		String style = "position:relative;z-index:1;overflow:hidden;height:100px;";
		listContainer.getElement().setAttribute("style", style);
		prepareIScroll(listContainer.getElement(), -40);
		
		IPhoneScrollerConfig config = IPhoneScrollerConfig.getDefault();
		// config.setBounce(true);
		config.setBounceLock(false);
		config.setSnap(false);
		config.setMomentum(true);
		config.setHScrollbar(false);
		config.setVScrollbar(true);
		config.setHScroll(false);
		config.setVScroll(true);
		iScroll = new IPhoneScroller(list, config);

		selectionModel = new SingleSelectionModel<SettingsCategory>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				onCategroySelected(
						selectionModel.getSelectedObject());
			}
		});

		list.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(list);
		*/
	}
	
}
