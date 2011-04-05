package com.googlecode.gwttouch.client;


import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AnimatedLayout;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.LayoutCommand;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class OrientationAwareLayoutPanel extends ComplexPanel implements
		AnimatedLayout, RequiresResize, ProvidesResize {

	private static OrientationAwareLayoutPanel singleton;
	
	public static OrientationAwareLayoutPanel get() {
		if (singleton == null) {
			singleton = new OrientationAwareLayoutPanel();
			RootPanel.get().add(singleton);
		}
		return singleton;
	}
	
	
	private final Layout layout;
	private final LayoutCommand layoutCmd;

	/**
	 * Creates an empty layout panel.
	 */
	private OrientationAwareLayoutPanel() {
		setElement(Document.get().createDivElement());
		layout = new Layout(getElement());
		layoutCmd = new LayoutCommand(layout);

		//add resize
	    Window.addResizeHandler(new ResizeHandler() {
	        public void onResize(ResizeEvent event) {
	        	OrientationAwareLayoutPanel.this.onResize();
	        }
	    });
	    
		//add orientation change
	    this.addOrientationHandler();

	    if(Window.Navigator.getUserAgent().toLowerCase().contains("android")) {
	    	RootPanel.getBodyElement().addClassName("android");
	    }

	}
	
	
	
	public Orientation getOrientation() {
		return getPortrait().equals("portrait") ? Orientation.Portrait : Orientation.Landscape;
	}
	
	
	private final native String getPortrait() /*-{
		
        if ($wnd.hasOwnProperty('orientation')) {
        	return Math.abs($wnd.orientation) == 90 ? 'landscape' : 'portrait';
        }

		return ($wnd.innerWidth > $wnd.innerHeight) ? 'landscape' : 'portrait';
	}-*/;
	
	private final native void addOrientationHandler() /*-{
		$wnd.addEventListener('orientationchange', new function(){
			this.@com.bradrydzewski.gwt.touch.client.OrientationAwareLayoutPanel::onOrientation();
		}, false);
	}-*/;
	
//	private final native void updateBodySize() /*-{
//		$doc.body.setSize($wnd.innerWidth, $wnd.innerHeight);
//	}-*/;
//	
	//on orientation change:
	//body.setHeight(body.getWidth());
	


	/**
	 * Adds a widget to this panel.
	 * 
	 * <p>
	 * By default, each child will fill the panel. To build more interesting
	 * layouts, set child widgets' layout constraints using
	 * {@link #setWidgetLeftRight(Widget, double, Style.Unit, double, Style.Unit)}
	 * and related methods.
	 * </p>
	 * 
	 * @param widget
	 *            the widget to be added
	 */
	@Override
	public void add(Widget widget) {
		insert(widget, getWidgetCount());
	}

	public void animate(int duration) {
		animate(duration, null);
	}

	public void animate(final int duration, final AnimationCallback callback) {
		layoutCmd.schedule(duration, callback);
	}

	public void forceLayout() {
		layoutCmd.cancel();
		layout.layout();
		onResize();
	}

	/**
	 * Gets the container element wrapping the given child widget.
	 * 
	 * @param child
	 * @return the widget's container element
	 */
	public Element getWidgetContainerElement(Widget child) {
		assertIsChild(child);
		return getLayer(child).getContainerElement();
	}

	/**
	 * Inserts a widget before the specified index.
	 * 
	 * <p>
	 * By default, each child will fill the panel. To build more interesting
	 * layouts, set child widgets' layout constraints using
	 * {@link #setWidgetLeftRight(Widget, double, Style.Unit, double, Style.Unit)}
	 * and related methods.
	 * </p>
	 * 
	 * <p>
	 * Inserting a widget in this way has no effect on the DOM structure, but
	 * can be useful for other panels that wrap LayoutPanel to maintain
	 * insertion order.
	 * </p>
	 * 
	 * @param widget
	 *            the widget to be inserted
	 * @param beforeIndex
	 *            the index before which it will be inserted
	 * @throws IndexOutOfBoundsException
	 *             if <code>beforeIndex</code> is out of range
	 */
	public void insert(Widget widget, int beforeIndex) {
		// Detach new child.
		widget.removeFromParent();

		// Logical attach.
		getChildren().insert(widget, beforeIndex);

		// Physical attach.
		Layer layer = layout.attachChild(widget.getElement(), widget);
		widget.setLayoutData(layer);

		// Adopt.
		adopt(widget);

		animate(0);
	}

	public void onOrientation() {
		RootPanel.getBodyElement().removeClassName("portrait");
		RootPanel.getBodyElement().removeClassName("landscape");
		RootPanel.getBodyElement().addClassName(getOrientation().name().toLowerCase());

		//updateBodySize();
		onResize();


	}
	
	
//	// Set viewport
//    if (jQTSettings.fixedViewport) {
//        hairExtensions += '<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"/>';
//    }
//
//    // Set full-screen
//    if (jQTSettings.fullScreen) {
//        hairExtensions += '<meta name="apple-mobile-web-app-capable" content="yes" />';
//        if (jQTSettings.statusBar) {
//            hairExtensions += '<meta name="apple-mobile-web-app-status-bar-style" content="' + jQTSettings.statusBar + '" />';
//        }
//    }	
	
	public void onResize() {
		//Window.alert("onResize: "+getOrientation());
		for (Widget child : getChildren()) {
			if (child instanceof RequiresResize) {
				((RequiresResize) child).onResize();
			}
		}
	}

	@Override
	public boolean remove(Widget w) {
		boolean removed = super.remove(w);
		if (removed) {
			layout.removeChild((Layer) w.getLayoutData());
		}
		return removed;
	}

	/**
	 * Sets the child widget's bottom and height values.
	 * 
	 * @param child
	 * @param bottom
	 * @param bottomUnit
	 * @param height
	 * @param heightUnit
	 */
	public void setWidgetBottomHeight(Widget child, double bottom,
			Unit bottomUnit, double height, Unit heightUnit) {
		assertIsChild(child);
		getLayer(child).setBottomHeight(bottom, bottomUnit, height, heightUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's horizontal position within its layer.
	 * 
	 * @param child
	 * @param position
	 */
	public void setWidgetHorizontalPosition(Widget child, Alignment position) {
		assertIsChild(child);
		getLayer(child).setChildHorizontalPosition(position);
		animate(0);
	}

	/**
	 * Sets the child widget's left and right values.
	 * 
	 * @param child
	 * @param left
	 * @param leftUnit
	 * @param right
	 * @param rightUnit
	 */
	public void setWidgetLeftRight(Widget child, double left, Unit leftUnit,
			double right, Unit rightUnit) {
		assertIsChild(child);
		getLayer(child).setLeftRight(left, leftUnit, right, rightUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's left and width values.
	 * 
	 * @param child
	 * @param left
	 * @param leftUnit
	 * @param width
	 * @param widthUnit
	 */
	public void setWidgetLeftWidth(Widget child, double left, Unit leftUnit,
			double width, Unit widthUnit) {
		assertIsChild(child);
		getLayer(child).setLeftWidth(left, leftUnit, width, widthUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's right and width values.
	 * 
	 * @param child
	 * @param right
	 * @param rightUnit
	 * @param width
	 * @param widthUnit
	 */
	public void setWidgetRightWidth(Widget child, double right, Unit rightUnit,
			double width, Unit widthUnit) {
		assertIsChild(child);
		getLayer(child).setRightWidth(right, rightUnit, width, widthUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's top and bottom values.
	 * 
	 * @param child
	 * @param top
	 * @param topUnit
	 * @param bottom
	 * @param bottomUnit
	 */
	public void setWidgetTopBottom(Widget child, double top, Unit topUnit,
			double bottom, Unit bottomUnit) {
		assertIsChild(child);
		getLayer(child).setTopBottom(top, topUnit, bottom, bottomUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's top and height values.
	 * 
	 * @param child
	 * @param top
	 * @param topUnit
	 * @param height
	 * @param heightUnit
	 */
	public void setWidgetTopHeight(Widget child, double top, Unit topUnit,
			double height, Unit heightUnit) {
		assertIsChild(child);
		getLayer(child).setTopHeight(top, topUnit, height, heightUnit);
		animate(0);
	}

	/**
	 * Sets the child widget's vertical position within its layer.
	 * 
	 * @param child
	 * @param position
	 */
	public void setWidgetVerticalPosition(Widget child, Alignment position) {
		assertIsChild(child);
		getLayer(child).setChildVerticalPosition(position);
		animate(0);
	}

	/**
	 * Shows or hides the given widget and its layer. This method explicitly
	 * calls {@link UIObject#setVisible(boolean)} on the child widget and
	 * ensures that its associated layer is shown/hidden.
	 * 
	 * @param child
	 * @param visible
	 */
	public void setWidgetVisible(Widget child, boolean visible) {
		assertIsChild(child);
		getLayer(child).setVisible(visible);
		child.setVisible(visible);
		animate(0);
	}

	@Override
	protected void onLoad() {
		
		

		layout.onAttach();
		layout.fillParent();
		onOrientation();
	}

	@Override
	protected void onUnload() {
		layout.onDetach();
	}

	/**
	 * Gets the {@link Layout} instance associated with this widget. This is
	 * made package-protected for use by {@link RootLayoutPanel}.
	 * 
	 * @return this widget's layout instance
	 */
	Layout getLayout() {
		return layout;
	}

	private void assertIsChild(Widget widget) {
		assert (widget == null) || (widget.getParent() == this) : "The specified widget is not a child of this panel";
	}

	private Layout.Layer getLayer(Widget child) {
		assert child.getParent() == this : "The requested widget is not a child of this panel";
		return (Layout.Layer) child.getLayoutData();
	}
}
