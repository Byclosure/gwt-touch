package com.googlecode.gwttouch.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SettingsEntryPoint implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		OrientationAwareLayoutPanel.get().add(new TestPanel());
		
	}
}
