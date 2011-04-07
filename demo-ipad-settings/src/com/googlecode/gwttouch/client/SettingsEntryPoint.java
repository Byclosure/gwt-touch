package com.googlecode.gwttouch.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.googlecode.gwttouch.client.place.GeneralPlace;
import com.googlecode.gwttouch.client.ui.SimpleResizePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SettingsEntryPoint implements EntryPoint {

	private Place defaultPlace = new GeneralPlace();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		/*
		 * Create a base panel of type HasSingleWidget and add to RootPanel.
		 * Required because Activities (GWT's MVP framework) require a widget
		 * to implement HasSignleWidget
		 */
		SimpleResizePanel resizableRoot = new SimpleResizePanel();
		OrientationAwareLayoutPanel.get().add(resizableRoot);
		
		// Create the ClientFactory, EventBus, etc
		ClientFactory clientFactory = new ClientFactoryImpl();
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		SettingsHistoryMapper historyMapper = GWT.create(SettingsHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		//create the main activity manager / application panel
		SettingsActivity activityMapper = new SettingsActivity(clientFactory, eventBus);

		//this will load the default page, kick off the mapper code
		activityMapper.start(resizableRoot, eventBus);
		
		// Goes to place represented on URL or default place
		historyHandler.handleCurrentHistory();
	}

}
