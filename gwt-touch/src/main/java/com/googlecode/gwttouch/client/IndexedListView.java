package com.googlecode.gwttouch.client;

import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;

/**
 * An extension of the {@link ListView} that displays data sorted and
 * grouped together, helping users quickly find what they need.
 * 
 * The index consists of a column of entries (usually letters in an alphabet) that
 * floats on the right edge of the screen. Users tap (or drag to) an index entry to
 * reveal the corresponding area in the list.
 * 
 * TODO: enabled the column of entries floating on the right of the list
 * TODO: need to intercept scrolling events to fix a group header at the top of the list when scrolling
 * TODO: create an index provider, allowing developers to customize the index (ie not just alphabetical)
 * 
 * @author Brad Rydzewski
 */
public class IndexedListView<T> extends ListView<T> {

	static class IndexedListViewCell<T> extends AbstractCell<T> {
		@Override
		public void render(Context context, T value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
		        return;
			}
		      
			sb.appendHtmlConstant("<div class='gwtTouch-IndexedListView-Item'>");
			sb.appendHtmlConstant("<div>");
			sb.appendEscaped(value.toString());
			sb.appendHtmlConstant("</div></div>");

		}
	}
	


	public IndexedListView() {
		this(new IndexedListViewCell<T>());
	}
	
//	private HTML indexPanel = new HTML("");
	
	public IndexedListView(Cell<T> cell) {
		super(cell);
		listPanel.setStyleName("gwtTouch-IndexedListView");
//		indexPanel.setStyleName("gwtTouch-IndexedListView-Menu");

//		addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent arg0) {
//				System.out.println("CLICK!");
//				//scroll to
//				String text=arg0.getRelativeElement().getInnerText();
//				System.out.println(text);
//			}
//		});
		
//		indexPanel.setHTML("<div>A</div><div>B</div><div>C</div>");
//
//		//add the a-z bar to the root element
//		root.getElement().appendChild(
//				indexPanel.getElement());
	}
	


//	@Override
//	public void onBrowserEvent(Event e) {
//		
//		if(e.getTypeInt()==Event.ONCLICK && e.getRelatedEventTarget()!=null) {
//			Element target = (com.google.gwt.user.client.Element)com.google.gwt.user.client.Element.as(e.getRelatedEventTarget().cast());
//			if(indexPanel.getElement() != target &&
//					DOM.isOrHasChild(indexPanel.getElement(),target)) {
//				System.out.println(target.getInnerText());
//				
//				return;
//			} 
//		}
//		super.onBrowserEvent(e);
//	}



	/**
	 * Sorts, groups and renders each item in the list of values.
	 */
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
			
			//get the first letter of item T
			if (value==null || value.toString().isEmpty()) {
				valueFirstLetter = "";
			} else {
				valueFirstLetter = value.toString().substring(0, 1);
			}
			
			//did we encounter a new group, based on first letter of T?
			//if so, render the div that displays the group.
			if(!group.equalsIgnoreCase(valueFirstLetter)) {
				group = valueFirstLetter.toUpperCase();
				sb.appendHtmlConstant("<div class='gwtTouch-IndexedListView-Group'>"+group+"</div>");
			}
			
			if(selectionModel!=null && selectionModel.isSelected(value)) {
				sb.appendHtmlConstant("<div class='gwtTouch-IndexedListView-ItemWrapper-Selected' __id='"+i+"'>");
			} else {
				sb.appendHtmlConstant("<div class='gwtTouch-IndexedListView-ItemWrapper' __id='"+i+"'>");
			}
			

			
			Context context = new Context(i, 0, null);
			cell.render(context, value, sb);
			sb.appendHtmlConstant("</div>");
		}
		
		listPanel.setHTML(sb.toSafeHtml());
	}
}
