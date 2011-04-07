package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NotesPlace extends Place {

	private String token = null;
	
	public NotesPlace() {
		this("");
	}

	public NotesPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<NotesPlace> {

		@Override
		public String getToken(NotesPlace place) {
			return place.token;
		}

		@Override
		public NotesPlace getPlace(String token) {
			return new NotesPlace(token);
		}

	}
}
