package com.googlecode.gwttouch.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class TestPanel extends ResizeComposite {

	private static TestPanelUiBinder uiBinder = GWT
			.create(TestPanelUiBinder.class);

	interface TestPanelUiBinder extends UiBinder<Widget, TestPanel> {
	}

	@UiField(provided=true) ListView<String> list = new ListView<String>();
	
	ListDataProvider<String> dataProvider;
	SingleSelectionModel<String> selectionModel;
	
	public TestPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		this.dataProvider = new ListDataProvider<String>();

		this.selectionModel = new SingleSelectionModel<String>();
		this.selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
//				Window.alert(selected);
			}
		});
		this.list.setSelectionModel(selectionModel);

		List<String> apptList = new ArrayList<String>();
		apptList.add("Aerosmith");
		apptList.add("Blue Oyster Cult");
		apptList.add("Creedence Clearwater Revival");
		apptList.add("Deep Purple");
		apptList.add("Eagles");
		apptList.add("Heart");
		apptList.add("Jimi Hendrix");
		apptList.add("Kansas");
		apptList.add("Led Zipplin");
		apptList.add("Lynyrd Skynyrd");
		apptList.add("Santana");
		
		this.dataProvider.addDataDisplay(list);
		this.dataProvider.setList(apptList);
	}

}
