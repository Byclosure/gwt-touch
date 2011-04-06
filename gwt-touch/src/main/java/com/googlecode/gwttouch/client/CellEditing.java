package com.googlecode.gwttouch.client;

/**
 * The editing control used by a cell.
 * @author Brad Rydzewski
 */
public enum CellEditing {

	/**
	 * The cell has no editing control. This is the
	 * default value.
	 */
	NONE,
	
	/**
	 * The cell has the insert editing control; this
	 * control is a green circle enclosing a plus sign.
	 */
	INSERT,
	
	/**
	 * The cell has the delete editing control; this
	 * control is a red circle enclosing a minus sign.
	 */
	DELETE;
}
