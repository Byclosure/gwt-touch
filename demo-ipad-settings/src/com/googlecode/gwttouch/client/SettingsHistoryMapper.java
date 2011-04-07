package com.googlecode.gwttouch.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.googlecode.gwttouch.client.place.GeneralPlace;
import com.googlecode.gwttouch.client.place.IPodPlace;
import com.googlecode.gwttouch.client.place.MailPlace;
import com.googlecode.gwttouch.client.place.NotesPlace;
import com.googlecode.gwttouch.client.place.NotificationsPlace;
import com.googlecode.gwttouch.client.place.PhotosPlace;
import com.googlecode.gwttouch.client.place.PictureFramePlace;
import com.googlecode.gwttouch.client.place.SafariPlace;
import com.googlecode.gwttouch.client.place.StorePlace;
import com.googlecode.gwttouch.client.place.VideoPlace;
import com.googlecode.gwttouch.client.place.WallpaperPlace;
import com.googlecode.gwttouch.client.place.WifiPlace;

@WithTokenizers({ 
	WifiPlace.Tokenizer.class,
	NotificationsPlace.Tokenizer.class,
	WallpaperPlace.Tokenizer.class,
	PictureFramePlace.Tokenizer.class,
	GeneralPlace.Tokenizer.class,
	MailPlace.Tokenizer.class,
	SafariPlace.Tokenizer.class,
	IPodPlace.Tokenizer.class,
	VideoPlace.Tokenizer.class,
	PhotosPlace.Tokenizer.class,
	NotesPlace.Tokenizer.class,
	StorePlace.Tokenizer.class
})
public interface SettingsHistoryMapper extends PlaceHistoryMapper {

}
