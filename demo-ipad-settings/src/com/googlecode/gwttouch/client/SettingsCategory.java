package com.googlecode.gwttouch.client;

import com.google.gwt.resources.client.ImageResource;

public class SettingsCategory {
	private String name;
	private ImageResource image;
	public SettingsCategory(String name, ImageResource img) {
		this.name = name;
		this.image = img;
	}
	public String getName() {
		return name;
	}
	public ImageResource getImage() {
		return image;
	}
}
