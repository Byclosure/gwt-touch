package com.googlecode.gwttouch.client;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

public class ListView<T> extends ResizeComposite implements HasData<T> {

	static class ListViewCell<T> extends AbstractCell<T> {
		@Override
		public void render(Context context, T value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
		        return;
			}
		      
			sb.appendHtmlConstant("<div class='touchListViewItem'>");
//			sb.append(context.getIndex());
			sb.appendHtmlConstant("<div>");
			sb.appendEscaped(value.toString());
			sb.appendHtmlConstant("</div></div>");

		}
	}

	private final Scheduler.ScheduledCommand redrawCommand = new Scheduler.ScheduledCommand() {
		@Override
		public void execute() {
			redraw();
		}
	};

	private final HTML listPanel;
	private final Cell<T> cell;
	private final TouchPanel root;
	private List<? extends T> values;
	private SelectionModel<? super T> selectionModel;
//	private int selectedIndex = -1;

	public ListView() {
		this(new ListViewCell<T>());
	}
	
	public ListView(Cell<T> cell) {
		this.root = new TouchPanel();
		this.cell = cell;
		
		//initialize the widget
		this.initWidget(root);

		// create the panel that will hold the list items
		this.listPanel = new HTML();//Document.get().createDivElement();
		this.listPanel.setStyleName("touchListView");
		this.listPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		this.listPanel.getElement().getStyle().setTop(0, Unit.PX);
		this.listPanel.getElement().getStyle().setLeft(0, Unit.PX);
		this.listPanel.getElement().getStyle().setRight(0, Unit.PX);
		this.root.add(listPanel);
		
		this.sinkEvents(Event.ONCLICK);
	}

	public void redraw() {
		
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		
		for (int i = 0; i < values.size(); i++) {

			T value = values.get(i);
			
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

	@Override
	public SelectionModel<? super T> getSelectionModel() {
		return selectionModel;
	}

	@Override
	public void setRowData(int start, List<? extends T> values) {
		this.values = values;
		redrawCommand.execute();
	}

	@Override
	public void setSelectionModel(SelectionModel<? super T> selectionModel) {
		
		
		
		this.selectionModel = selectionModel;
		if(selectionModel!=null) {
			this.selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					redraw();
				}
			});
		}
	}

	@Override
	public int getRowCount() {
		return (values == null) ? 0 : values.size();
	}

	@Override
	public void onLoad() {
		super.onLoad();
		redrawCommand.execute();
	}

	
	@Override
	public void onBrowserEvent(Event e) {
		switch(e.getTypeInt()) {
		case Event.ONCLICK :

			if(e.getRelatedEventTarget()==null)
				return;
			
			Element target = com.google.gwt.user.client.Element.as(e.getRelatedEventTarget().cast());
			if(target!=null && DOM.isOrHasChild(listPanel.getElement(), (com.google.gwt.user.client.Element) target)){
				Element itemElement = target;
				while(itemElement!=null) {
					if(itemElement.hasAttribute("__id")) {
						int i = Integer.valueOf(itemElement.getAttribute("__id"));
						if(selectionModel!=null && i<values.size()) {
							selectionModel.setSelected(values.get(i), true);
						}
						break;
					} else
						itemElement = itemElement.getParentElement();
				}
			}
			
		break;
		}
		super.onBrowserEvent(e);
	}
	
//	protected Element getEventTargetCell(Event event) {
//	    Element td = DOM.eventGetTarget(event);
//	    for (; td != null; td = DOM.getParent(td)) {
//	      // If it's a TD, it might be the one we're looking for.
//	      if (DOM.getElementProperty(td, "tagName").equalsIgnoreCase("td")) {
//	        // Make sure it's directly a part of this table before returning
//	        // it.
//	        Element tr = DOM.getParent(td);
//	        Element body = DOM.getParent(tr);
//	        if (body == bodyElem) {
//	          return td;
//	        }
//	      }
//	      // If we run into this table's body, we're out of options.
//	      if (td == bodyElem) {
//	        return null;
//	      }
//	    }
//	    return null;
//	  }
	
	 	
	
	// Unsupported method required by HasData interface
	@Override
	public HandlerRegistration addCellPreviewHandler(
			CellPreviewEvent.Handler<T> handler) {
		return super.addHandler(handler, CellPreviewEvent.getType());
	}

	@Override
	public void setVisibleRangeAndClearData(Range range,
			boolean forceRangeChangeEvent) {
		// throw new UnsupportedOperationException(NOT_SUPPORTED);
		if (forceRangeChangeEvent)
			redrawCommand.execute();
	}

	@Override
	public HandlerRegistration addRangeChangeHandler(Handler handler) {
		return super.addHandler(handler, RangeChangeEvent.getType());
	}

	@Override
	public HandlerRegistration addRowCountChangeHandler(
			RowCountChangeEvent.Handler handler) {
		return super.addHandler(handler, RowCountChangeEvent.getType());
	}

	@Override
	public T getVisibleItem(int indexOnPage) {
		return values.get(indexOnPage);
	}

	@Override
	public int getVisibleItemCount() {
		return (values == null) ? 0 : values.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<T> getVisibleItems() {
		return (Iterable<T>) values.iterator();
	}

	@Override
	public Range getVisibleRange() {
		return new Range(0, Integer.MAX_VALUE);
		// return new Range(0,(values==null)?0:values.size());
	}

	@Override
	public void setVisibleRange(int start, int length) {
		// throw new UnsupportedOperationException(NOT_SUPPORTED);
	}

	@Override
	public void setVisibleRange(Range range) {
		// throw new UnsupportedOperationException(NOT_SUPPORTED);
	}

	@Override
	public boolean isRowCountExact() {
		return true;
	}

	@Override
	public void setRowCount(int count) {
		// throw new UnsupportedOperationException(NOT_SUPPORTED);
	}

	@Override
	public void setRowCount(int count, boolean isExact) {
		// throw new UnsupportedOperationException(NOT_SUPPORTED);
	}
}
