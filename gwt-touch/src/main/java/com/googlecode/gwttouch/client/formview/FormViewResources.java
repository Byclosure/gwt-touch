package com.googlecode.gwttouch.client.formview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface FormViewResources extends ClientBundle {

	public static final FormViewResources INSTANCE =
		GWT.create(FormViewResources.class);
	
	@Source("FormView.css")
	@CssResource.NotStrict
	FormViewCssResource css();
	
	interface FormViewCssResource extends CssResource {

	}
}
