package com.googlecode.gwttouch.client;

import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class IndexedListView<T> extends ListView<T> {

	public IndexedListView() {
		super();
	}
	
	public IndexedListView(Cell<T> cell) {
		super(cell);
	}

	@Override
	public void redraw() {
		
		//we should sort the list
		Collections.sort(values, new Comparator<T>(){
			@Override
			public int compare(T o1, T o2) {
				if(o1==null) return -1;
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		String group = "";
		String valueFirstLetter = group;
		
		for (int i = 0; i < values.size(); i++) {
			
			T value = values.get(i);
			
			if (value==null || value.toString().isEmpty()) {
				valueFirstLetter = "";
			} else {
				valueFirstLetter = value.toString().substring(0, 1);
			}
			
			if(!group.equalsIgnoreCase(valueFirstLetter)) {
				group = valueFirstLetter.toUpperCase();
				sb.appendHtmlConstant("<div class='touchListView-Group'>"+group+"</div>");
			}
			
			if(selectionModel!=null && selectionModel.isSelected(value)) {
				sb.appendHtmlConstant("<div class='touchListViewItemWrapper-Selected' __id='"+i+"'>");
			} else {
				sb.appendHtmlConstant("<div class='touchListViewItemWrapper' __id='"+i+"'>");
			}
			

			
			Context context = new Context(i, 0, null);
			cell.render(context, value, sb);
			sb.appendHtmlConstant("</div>");
		}
		
		listPanel.setHTML(sb.toSafeHtml());
	}
}
