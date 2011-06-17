package com.googlecode.gwttouch.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwttouch.client.ClientFactory;
import com.googlecode.gwttouch.client.ui.NotificationsView;
import com.googlecode.gwttouch.client.ui.SafariView;

public class SafariActivity extends AbstractActivity implements SafariView.Presenter {

	private final ClientFactory clientFactory;
	private SafariView view;

	public SafariActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getSafariView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
		this.view.refreshIScroll();
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);	
	}

}
