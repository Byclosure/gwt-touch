package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MailPlace extends Place {

	private String token = null;
	
	public MailPlace() {
		this("");
	}

	public MailPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<MailPlace> {

		@Override
		public String getToken(MailPlace place) {
			return place.token;
		}

		@Override
		public MailPlace getPlace(String token) {
			return new MailPlace(token);
		}

	}
}
