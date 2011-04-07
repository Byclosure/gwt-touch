package com.googlecode.gwttouch.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class WifiViewImpl extends ResizeComposite implements WifiView {

	private static WifiViewImplUiBinder uiBinder = GWT
			.create(WifiViewImplUiBinder.class);

	interface WifiViewImplUiBinder extends UiBinder<Widget, WifiViewImpl> {
	}

	@SuppressWarnings("unused")
	private Presenter listener;

	public WifiViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}


}
