package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NotificationsPlace extends Place {

	private String token = null;
	
	public NotificationsPlace() {
		this("");
	}

	public NotificationsPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<NotificationsPlace> {

		@Override
		public String getToken(NotificationsPlace place) {
			return place.token;
		}

		@Override
		public NotificationsPlace getPlace(String token) {
			return new NotificationsPlace(token);
		}

	}
}
