package com.googlecode.gwttouch.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwttouch.client.ui.SimpleResizePanel;

public class SettingsViewImpl extends ResizeComposite implements SettingsView {

	private static SettingsViewUiBinder uiBinder = GWT
			.create(SettingsViewUiBinder.class);

	interface SettingsViewUiBinder extends UiBinder<Widget, SettingsViewImpl> {
	}
	

	@UiField SimpleResizePanel centerPanel;
	
	public SettingsViewImpl() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	public void onCategroySelected(SettingsCategory category) {
	}

	@Override
	public void setWidget(IsWidget w) {
		centerPanel.setWidget(w);
	}

	@Override
	public void setPresenter(Presenter listener) {
	}

	@Override
	public void setWindowTitle(String title) {
		Window.setTitle(title);
	}

	@Override
	public void setSelectedCategory(SettingsCategory category) {
	}

	@Override
	public void setCategoryList(List<SettingsCategory> list) {
	}
}
