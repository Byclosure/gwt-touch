package com.googlecode.gwttouch.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwttouch.client.resources.Resources;

public class Channel extends Composite {
	private static ChannelImplUiBinder uiBinder = GWT.create(ChannelImplUiBinder.class);

	interface ChannelImplUiBinder extends UiBinder<Widget, Channel> {
	}
	
	@UiField(provided=true)
	Label nameLabel;
	
	@UiField(provided=true)
	Label numberLabel;
	
	@UiField(provided=true)
	Image logoLabel;
	
	public Channel() {
		this("0", "##TODO##", Resources.INSTANCE.channel1());
	}
	
	@UiConstructor
	public Channel(String number, String name, ImageResource logo) {
		setNumber(number);
		setName(name);
		setLogo(logo);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setName(String name) {
		this.nameLabel = new InlineLabel(name);
	}
	
	public void setLogo(ImageResource image) {
		this.logoLabel = new Image(image);
	}
	
	public void setNumber(String number) {
		this.numberLabel = new InlineLabel(number + ".");
	}
}
