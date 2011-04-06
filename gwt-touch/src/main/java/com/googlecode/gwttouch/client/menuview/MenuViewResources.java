package com.googlecode.gwttouch.client.menuview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface MenuViewResources extends ClientBundle {

	public static final MenuViewResources INSTANCE =
		GWT.create(MenuViewResources.class);
	
	@Source("MenuView.css")
	@CssResource.NotStrict
	MenuViewCssResource css();
	
	@Source("Chevron.png")
	ImageResource chevron();
	
	@Source("Disclosure.png")
	ImageResource disclosure();
	
	@Source("SwitchOn.png")
	ImageResource switchOn();
	
	@Source("SwitchOff.png")
	ImageResource switchOff();
	
	@Source("Checkmark.png")
	ImageResource checkmark();
	
	interface MenuViewCssResource extends CssResource {

	}
}
