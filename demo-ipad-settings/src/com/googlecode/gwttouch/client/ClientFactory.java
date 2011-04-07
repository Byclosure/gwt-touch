package com.googlecode.gwttouch.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.googlecode.gwttouch.client.ui.GeneralView;
import com.googlecode.gwttouch.client.ui.NotificationsView;
import com.googlecode.gwttouch.client.ui.WifiView;

public interface ClientFactory {

	EventBus getEventBus();
	PlaceController getPlaceController();
	SettingsView getSettingsView();
	NotificationsView getNotificationsView();
	WifiView getWifiView();
	GeneralView getGeneralView();
}
