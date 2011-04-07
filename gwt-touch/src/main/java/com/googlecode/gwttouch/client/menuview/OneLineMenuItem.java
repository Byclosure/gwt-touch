package com.googlecode.gwttouch.client.menuview;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.googlecode.gwttouch.client.CellAccessory;

public class OneLineMenuItem extends Composite
	implements HasText {

	protected FlowPanel root = new FlowPanel();
	protected Label textLabel = new Label();
	protected Label subTextLabel = new Label();
	protected SimplePanel imagePanel;
	
	public OneLineMenuItem() {
		initWidget(root);
		root.setStyleName("gwtTouch-OneLineMenuItem");
		
		textLabel.setStyleName("gwtTouch-OneLineMenuItem-TextLabel");
		subTextLabel.setStyleName("gwtTouch-OneLineMenuItem-SubTextLabel");
		

		root.add(subTextLabel);
		root.add(textLabel);
	}
	
	@Override
	public String getText() {
		return textLabel.getText();
	}

	@Override
	public void setText(String text) {
		textLabel.setText(text);
	}

	public String getSubText() {
		return subTextLabel.getText();
	}

	public void setSubText(String text) {
		subTextLabel.setText(text);
	}
	
	public void setImage(ImageResource imageRes) {
		this.setImage(new Image(imageRes.getURL()));
	}
	
	private void setImage(Image image) {
		if(imagePanel==null) {
			imagePanel = new SimplePanel();
			imagePanel.setStyleName("gwtTouch-MenuItem-Image");
			root.addStyleName("gwtTouch-MenuItem-HasImage");
		}
		imagePanel.getElement().getStyle().setBackgroundImage("url(\""+image.getUrl()+"\")");
		root.add(imagePanel);
	
	}
	
	public void setAccessory(CellAccessory accessory) {
		if(accessory==CellAccessory.DISCLOSURE_INDICATOR) {
			root.addStyleName("gwtTouch-MenuItem-Chevron");
		} else if (accessory==CellAccessory.DETAIL_DISCLOSURE) {
			root.addStyleName("gwtTouch-MenuItem-Disclosure");
		} else if (accessory==CellAccessory.CHECKMARK) {
			root.addStyleName("gwtTouch-MenuItem-Checkmark");
		} else {
			root.setStyleName("gwtTouch-OneLineMenuItem");
		}
	}
}
