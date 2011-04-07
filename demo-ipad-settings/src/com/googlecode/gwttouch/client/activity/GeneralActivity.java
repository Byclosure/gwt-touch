package com.googlecode.gwttouch.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwttouch.client.ClientFactory;
import com.googlecode.gwttouch.client.ui.GeneralView;

public class GeneralActivity extends AbstractActivity implements GeneralView.Presenter {

	private final ClientFactory clientFactory;
	private GeneralView view;

	public GeneralActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getGeneralView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);	
	}

}
