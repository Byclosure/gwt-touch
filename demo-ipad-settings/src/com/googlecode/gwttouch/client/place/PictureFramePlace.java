package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PictureFramePlace extends Place {

	private String token = null;
	
	public PictureFramePlace() {
		this("");
	}

	public PictureFramePlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<PictureFramePlace> {

		@Override
		public String getToken(PictureFramePlace place) {
			return place.token;
		}

		@Override
		public PictureFramePlace getPlace(String token) {
			return new PictureFramePlace(token);
		}

	}
}
