package com.googlecode.gwttouch.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface SafariView extends IsWidget {

	void setPresenter(Presenter listener);
	
	public interface Presenter {
		void goTo(Place place);
	}

	public void refreshIScroll();
}

