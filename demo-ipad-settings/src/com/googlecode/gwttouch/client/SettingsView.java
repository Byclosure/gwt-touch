package com.googlecode.gwttouch.client;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface SettingsView extends AcceptsOneWidget, IsWidget {

	void setPresenter(Presenter listener);
	void setWindowTitle(String title);
	void setCategoryList(List<SettingsCategory> list);
	void setSelectedCategory(SettingsCategory category);
	
	public interface Presenter {
		void goTo(Place place);
		void onCategorySelected(SettingsCategory selected);
	}
}
