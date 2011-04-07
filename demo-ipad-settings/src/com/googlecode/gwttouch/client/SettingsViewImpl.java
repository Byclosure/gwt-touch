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

		
//		List<SettingsCategory> categoryList =
//			new ArrayList<SettingsCategory>();
//		categoryList.add(new SettingsCategory("Wi-Fi",Resources.INSTANCE.settingsWifi()));
//		categoryList.add(new SettingsCategory("Notifications",Resources.INSTANCE.settingsNotifications()));
////		categoryList.add(new SettingsCategory("Location Services",Resources.INSTANCE.settingsLocation()));
////		categoryList.add(new SettingsCategory("Cellular Data",Resources.INSTANCE.settingsCellular()));
//		categoryList.add(new SettingsCategory("Brightness & Wallpaper",Resources.INSTANCE.settingsWallpaper()));
//		categoryList.add(new SettingsCategory("Picture Frame",Resources.INSTANCE.settingsPictureFrame()));
//		categoryList.add(new SettingsCategory("General",Resources.INSTANCE.settingsGeneral()));
//		categoryList.add(new SettingsCategory("Mail, Contacts, Calendars",Resources.INSTANCE.settingsEmail()));
//		categoryList.add(new SettingsCategory("Safari",Resources.INSTANCE.settingsSafari()));
//		categoryList.add(new SettingsCategory("iPod",Resources.INSTANCE.settingsIPod()));
//		categoryList.add(new SettingsCategory("Video",Resources.INSTANCE.settingsVideo()));
//		categoryList.add(new SettingsCategory("Photos",Resources.INSTANCE.settingsPhotos()));
////		categoryList.add(new SettingsCategory("FaceTime",Resources.INSTANCE.settingsFaceTime()));
//		categoryList.add(new SettingsCategory("Notes",Resources.INSTANCE.settingsNotes()));
//		categoryList.add(new SettingsCategory("Store",Resources.INSTANCE.settingsAppstore()));

		list.setSelectionModel(selectionModel);
		dataProvider.addDataDisplay(list);
//		dataProvider.setList(categoryList);
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
