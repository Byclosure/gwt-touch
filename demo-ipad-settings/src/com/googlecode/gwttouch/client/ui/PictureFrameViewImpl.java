package com.googlecode.gwttouch.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class PictureFrameViewImpl extends ResizeComposite implements PictureFrameView {

	private static PictureFrameViewImplUiBinder uiBinder = GWT
			.create(PictureFrameViewImplUiBinder.class);

	interface PictureFrameViewImplUiBinder extends
			UiBinder<Widget, PictureFrameViewImpl> {
	}
	
	@SuppressWarnings("unused")
	private Presenter listener;

	public PictureFrameViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
