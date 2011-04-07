package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GeneralPlace extends Place {

	private String token = null;
	
	public GeneralPlace() {
		this("");
	}

	public GeneralPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<GeneralPlace> {

		@Override
		public String getToken(GeneralPlace place) {
			return place.token;
		}

		@Override
		public GeneralPlace getPlace(String token) {
			return new GeneralPlace(token);
		}

	}
}
