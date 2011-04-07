package com.googlecode.gwttouch.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class NotificationsViewImpl extends ResizeComposite implements NotificationsView {

	private static NotificationsViewImplUiBinder uiBinder = GWT
			.create(NotificationsViewImplUiBinder.class);

	interface NotificationsViewImplUiBinder extends
			UiBinder<Widget, NotificationsViewImpl> {
	}
	private Presenter listener;

	public NotificationsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
}
