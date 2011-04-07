package com.googlecode.gwttouch.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class VideoPlace extends Place {

	private String token = null;
	
	public VideoPlace() {
		this("");
	}

	public VideoPlace(String token) {
		this.token = token;
	}

	public static class Tokenizer implements PlaceTokenizer<VideoPlace> {

		@Override
		public String getToken(VideoPlace place) {
			return place.token;
		}

		@Override
		public VideoPlace getPlace(String token) {
			return new VideoPlace(token);
		}

	}
}
