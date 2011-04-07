package com.googlecode.gwttouch.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwttouch.client.activity.GeneralActivity;
import com.googlecode.gwttouch.client.activity.NotificationActivity;
import com.googlecode.gwttouch.client.activity.WifiActivity;
import com.googlecode.gwttouch.client.place.GeneralPlace;
import com.googlecode.gwttouch.client.place.NotificationsPlace;
import com.googlecode.gwttouch.client.place.WifiPlace;
import com.googlecode.gwttouch.client.resources.Resources;

public class SettingsActivity extends AbstractActivity implements ActivityMapper,SettingsView.Presenter {

	private ClientFactory clientFactory;
	private SettingsView view;
	@SuppressWarnings("unused")
	private EventBus eventBus;
	private ActivityManager activityManager;
	private List<SettingsCategory> categoryList;

	public SettingsActivity(ClientFactory clientFactory, EventBus eventBus) {
		this.clientFactory = clientFactory;
		this.eventBus = eventBus;
		this.activityManager = new ActivityManager(this, eventBus);
	
		categoryList =
			new ArrayList<SettingsCategory>();
		categoryList.add(new SettingsCategory("Wi-Fi",Resources.INSTANCE.settingsWifi()));
		categoryList.add(new SettingsCategory("Notifications",Resources.INSTANCE.settingsNotifications()));
//		categoryList.add(new SettingsCategory("Location Services",Resources.INSTANCE.settingsLocation()));
//		categoryList.add(new SettingsCategory("Cellular Data",Resources.INSTANCE.settingsCellular()));
		categoryList.add(new SettingsCategory("Brightness & Wallpaper",Resources.INSTANCE.settingsWallpaper()));
		categoryList.add(new SettingsCategory("Picture Frame",Resources.INSTANCE.settingsPictureFrame()));
		categoryList.add(new SettingsCategory("General",Resources.INSTANCE.settingsGeneral()));
		categoryList.add(new SettingsCategory("Mail, Contacts, Calendars",Resources.INSTANCE.settingsEmail()));
		categoryList.add(new SettingsCategory("Safari",Resources.INSTANCE.settingsSafari()));
		categoryList.add(new SettingsCategory("iPod",Resources.INSTANCE.settingsIPod()));
		categoryList.add(new SettingsCategory("Video",Resources.INSTANCE.settingsVideo()));
		categoryList.add(new SettingsCategory("Photos",Resources.INSTANCE.settingsPhotos()));
//		categoryList.add(new SettingsCategory("FaceTime",Resources.INSTANCE.settingsFaceTime()));
		categoryList.add(new SettingsCategory("Notes",Resources.INSTANCE.settingsNotes()));
		categoryList.add(new SettingsCategory("Store",Resources.INSTANCE.settingsAppstore()));
		
		
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);	
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getSettingsView();
		view.setPresenter(this);
		//TODO: figure out why I get an exception if I setList after I setWidgets
		view.setCategoryList(categoryList);
		panel.setWidget(view.asWidget());
		activityManager.setDisplay(view);
	}

	@Override
	public void onCategorySelected(SettingsCategory category) {
		if("Wi-Fi".equals(category.getName())) {
			goTo(new WifiPlace());
		} else if("Notifications".equals(category.getName())) {
			goTo(new NotificationsPlace());
		} else if("General".equals(category.getName())) {
			goTo(new GeneralPlace());
		}
	}

	@Override
	public Activity getActivity(Place place) {
		
		Activity activity = null;

		if(place instanceof WifiPlace) {
			activity = new WifiActivity(clientFactory);
			view.setSelectedCategory(categoryList.get(0));
		} else if(place instanceof NotificationsPlace) {
			activity = new NotificationActivity(clientFactory);
			view.setSelectedCategory(categoryList.get(1));
		} else {
			activity = new GeneralActivity(clientFactory);
			view.setSelectedCategory(categoryList.get(4));
		}

		return activity;
	}

}
