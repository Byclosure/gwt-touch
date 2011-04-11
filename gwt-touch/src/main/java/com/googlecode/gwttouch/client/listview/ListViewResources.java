package com.googlecode.gwttouch.client.listview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ListViewResources extends ClientBundle {

	public static final ListViewResources INSTANCE =
		GWT.create(ListViewResources.class);

	@Source("ListView.css")
	@CssResource.NotStrict
	ListViewCssResource css();

	interface ListViewCssResource extends CssResource {

	}
}
