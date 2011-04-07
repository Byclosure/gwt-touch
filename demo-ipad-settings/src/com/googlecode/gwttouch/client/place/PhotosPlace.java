package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PhotosPlace extends Place {

	private String token = null;
	
	public PhotosPlace() {
		this("");
	}

	public PhotosPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<PhotosPlace> {

		@Override
		public String getToken(PhotosPlace place) {
			return place.token;
		}

		@Override
		public PhotosPlace getPlace(String token) {
			return new PhotosPlace(token);
		}

	}
}
