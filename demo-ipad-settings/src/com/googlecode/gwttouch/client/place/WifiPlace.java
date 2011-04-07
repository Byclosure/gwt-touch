package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WifiPlace extends Place {

	private String token = null;
	
	public WifiPlace() {
		this("");
	}

	public WifiPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<WifiPlace> {

		@Override
		public String getToken(WifiPlace place) {
			return place.token;
		}

		@Override
		public WifiPlace getPlace(String token) {
			return new WifiPlace(token);
		}

	}
}
