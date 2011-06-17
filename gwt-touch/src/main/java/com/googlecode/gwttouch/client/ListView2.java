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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.googlecode.gwttouch.client.listview.ListViewResources;

public class ListView2<T> extends Composite implements HasData<T> {

	
	
	static class ListViewCell<T> extends AbstractCell<T> {
		@Override
		public void render(Context context, T value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
		        return;
			}
		      
			sb.appendHtmlConstant("<div class='touchListViewItem'>");
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

	protected final HTML listPanel;
	protected final Cell<T> cell;
	protected List<? extends T> values;
	protected SelectionModel<? super T> selectionModel;

	public ListView2() {
		this(new ListViewCell<T>());
	}
	
	public ListView2(Cell<T> cell) {
		
		//ensure style sheet loaded
		ListViewResources.INSTANCE.css().ensureInjected();
		
		this.cell = cell;
		
		// create the panel that will hold the list items
		this.listPanel = new HTML();//Document.get().createDivElement();
		this.listPanel.setStyleName("touchListView");
//		this.listPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
//		this.listPanel.getElement().getStyle().setTop(0, Unit.PX);
//		this.listPanel.getElement().getStyle().setLeft(0, Unit.PX);
//		this.listPanel.getElement().getStyle().setRight(0, Unit.PX);
		
		//initialize the widget
		this.initWidget(this.listPanel);
		
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
//		case Event.ONTOUCHEND:
		case Event.ONCLICK :
//			Window.alert("clicked!");
//			if(e.getRelatedEventTarget()==null)
//				return;
			
//			Window.alert("clicked!");
			
			Element target = com.google.gwt.user.client.Element.as(e.getEventTarget());
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
//		default: Window.alert(e.getType());
		}
		super.onBrowserEvent(e);
	}
	
	
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
