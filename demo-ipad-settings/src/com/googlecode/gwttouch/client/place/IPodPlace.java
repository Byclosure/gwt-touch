package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class IPodPlace extends Place {

	private String token = null;
	
	public IPodPlace() {
		this("");
	}

	public IPodPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<IPodPlace> {

		@Override
		public String getToken(IPodPlace place) {
			return place.token;
		}

		@Override
		public IPodPlace getPlace(String token) {
			return new IPodPlace(token);
		}

	}
}
