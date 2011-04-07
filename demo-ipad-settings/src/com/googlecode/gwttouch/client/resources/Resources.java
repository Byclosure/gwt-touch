package com.googlecode.gwttouch.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	public static final Resources INSTANCE =
		GWT.create(Resources.class);
	
	@Source("wallpaper-beach.png")
	ImageResource wallpaperBeach();

	@Source("wallpaper-flowers.gif")
	ImageResource wallpaperFlowers();

	@Source("wallpaper-palmtree.jpg")
	ImageResource wallpaperPalmTree();

	@Source("wallpaper-sunset.png")
	ImageResource wallpaperSunset();

	/** Settings Icons **/
	
	@Source("settings-wifi.png")
	ImageResource settingsWifi();
	
	@Source("settings-notifications.png")
	ImageResource settingsNotifications();
	
	@Source("settings-location.png")
	ImageResource settingsLocation();
	
	@Source("settings-cellular.png")
	ImageResource settingsCellular();
	
	@Source("settings-wallpaper.png")
	ImageResource settingsWallpaper();
	
	@Source("settings-pictureframe.png")
	ImageResource settingsPictureFrame();
	
	@Source("settings-general.png")
	ImageResource settingsGeneral();
	
	@Source("settings-email.png")
	ImageResource settingsEmail();
	
	@Source("settings-safari.png")
	ImageResource settingsSafari();
	
	@Source("settings-ipod.png")
	ImageResource settingsIPod();
	
	@Source("settings-video.png")
	ImageResource settingsVideo();
	
	@Source("settings-photos.png")
	ImageResource settingsPhotos();
	
	@Source("settings-facetime.png")
	ImageResource settingsFaceTime();
	
	@Source("settings-notes.png")
	ImageResource settingsNotes();

	@Source("settings-appstore.png")
	ImageResource settingsAppstore();
	
	

}
