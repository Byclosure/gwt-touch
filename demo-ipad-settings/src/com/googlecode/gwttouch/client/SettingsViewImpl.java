package com.googlecode.gwttouch.client;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.gwttouch.client.ui.SimpleResizePanel;

public class SettingsViewImpl extends ResizeComposite implements SettingsView {

	private static SettingsViewUiBinder uiBinder = GWT
			.create(SettingsViewUiBinder.class);

	interface SettingsViewUiBinder extends UiBinder<Widget, SettingsViewImpl> {
	}
	

	
	static final class SettingsCategoryCell extends AbstractCell<SettingsCategory> {
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				SettingsCategory value, SafeHtmlBuilder sb) {
			
			sb.appendHtmlConstant("<div class='touchListViewItem'>");
			sb.appendHtmlConstant("<div>");
			sb.appendHtmlConstant("<img src=\""+value.getImage().getURL()+"\" style=\"margin-top: 10px !IMPORTANT; margin-right: 10px !IMPORTANT; float: left;\">");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</div></div>");
		}
	}

	@UiField SimpleResizePanel centerPanel;
	@UiField(provided=true) ListView<SettingsCategory> list =
		new ListView<SettingsCategory>(new SettingsCategoryCell());
	

	SingleSelectionModel<SettingsCategory> selectionModel;
	ListDataProvider<SettingsCategory> dataProvider =
		new ListDataProvider<SettingsCategory>();
	
	private Presenter listener;
	
	public SettingsViewImpl() {

		initWidget(uiBinder.createAndBindUi(this));

		selectionModel = new SingleSelectionModel<SettingsCategory>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				onCategroySelected(
						selectionModel.getSelectedObject());
			}
		});

		list.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(list);
	}
	
	public void onCategroySelected(SettingsCategory category) {
		listener.onCategorySelected(category);
	}

	@Override
	public void setWidget(IsWidget w) {
		centerPanel.setWidget(w);
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setWindowTitle(String title) {
		Window.setTitle(title);
	}

	@Override
	public void setSelectedCategory(SettingsCategory category) {
		selectionModel.setSelected(category, true);
	}

	@Override
	public void setCategoryList(List<SettingsCategory> list) {
		dataProvider.setList(list);
	}
}
