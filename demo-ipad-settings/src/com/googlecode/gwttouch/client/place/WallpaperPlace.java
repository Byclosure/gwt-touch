package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WallpaperPlace extends Place {

	private String token = null;
	
	public WallpaperPlace() {
		this("");
	}

	public WallpaperPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<WallpaperPlace> {

		@Override
		public String getToken(WallpaperPlace place) {
			return place.token;
		}

		@Override
		public WallpaperPlace getPlace(String token) {
			return new WallpaperPlace(token);
		}

	}
}
