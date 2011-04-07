package com.googlecode.gwttouch.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class GeneralViewImpl extends ResizeComposite implements GeneralView {

	private static GeneralViewImplUiBinder uiBinder = GWT
			.create(GeneralViewImplUiBinder.class);

	interface GeneralViewImplUiBinder extends UiBinder<Widget, GeneralViewImpl> {
	}

	@SuppressWarnings("unused")
	private Presenter listener;
	
	public GeneralViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
