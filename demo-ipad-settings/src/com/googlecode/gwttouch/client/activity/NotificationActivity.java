package com.googlecode.gwttouch.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwttouch.client.ClientFactory;
import com.googlecode.gwttouch.client.ui.NotificationsView;

public class NotificationActivity extends AbstractActivity implements NotificationsView.Presenter {

	private final ClientFactory clientFactory;
	private NotificationsView view;

	public NotificationActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getNotificationsView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);	
	}

}
