package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class StorePlace extends Place {

	private String token = null;
	
	public StorePlace() {
		this("");
	}

	public StorePlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<StorePlace> {

		@Override
		public String getToken(StorePlace place) {
			return place.token;
		}

		@Override
		public StorePlace getPlace(String token) {
			return new StorePlace(token);
		}

	}
}
