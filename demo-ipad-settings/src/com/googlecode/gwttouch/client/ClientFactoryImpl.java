package com.googlecode.gwttouch.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.googlecode.gwttouch.client.ui.GeneralView;
import com.googlecode.gwttouch.client.ui.GeneralViewImpl;
import com.googlecode.gwttouch.client.ui.NotificationsView;
import com.googlecode.gwttouch.client.ui.NotificationsViewImpl;
import com.googlecode.gwttouch.client.ui.PictureFrameView;
import com.googlecode.gwttouch.client.ui.PictureFrameViewImpl;
import com.googlecode.gwttouch.client.ui.SafariViewImpl;
import com.googlecode.gwttouch.client.ui.WifiView;
import com.googlecode.gwttouch.client.ui.WifiViewImpl;

public class ClientFactoryImpl implements ClientFactory {
	
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final SettingsViewImpl settingsView = new SettingsViewImpl();
	private static final NotificationsView notificationsView = new NotificationsViewImpl();
	private static final WifiView wifiView = new WifiViewImpl();
	private static final GeneralView generalView = new GeneralViewImpl();
	private static final PictureFrameView pictureFrameView = new PictureFrameViewImpl();
	private static final SafariViewImpl safariView = new SafariViewImpl();
	
	public ClientFactoryImpl() {
		
	}
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public SettingsViewImpl getSettingsView() {
		return settingsView;
	}

	@Override
	public NotificationsView getNotificationsView() {
		return notificationsView;
	}

	@Override
	public WifiView getWifiView() {
		return wifiView;
	}

	@Override
	public GeneralView getGeneralView() {
		return generalView;
	}

	@Override
	public PictureFrameView getPictureFrameView() {
		return pictureFrameView;
	}
	
	@Override
	public SafariViewImpl getSafariView() {
		return safariView;
	}
}
