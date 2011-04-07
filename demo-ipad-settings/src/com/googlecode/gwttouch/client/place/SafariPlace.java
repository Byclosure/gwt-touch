package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SafariPlace extends Place {

	private String token = null;
	
	public SafariPlace() {
		this("");
	}

	public SafariPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<SafariPlace> {

		@Override
		public String getToken(SafariPlace place) {
			return place.token;
		}

		@Override
		public SafariPlace getPlace(String token) {
			return new SafariPlace(token);
		}

	}
}
