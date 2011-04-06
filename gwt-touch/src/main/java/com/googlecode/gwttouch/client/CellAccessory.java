package com.googlecode.gwttouch.client;

/**
 * The type of standard accessory control used by a cell.
 * @author Brad Rydzewski
 */
public enum CellAccessory {

	NONE,
	
	/**
	 * You use the disclosure indicator when selecting a
	 * cell results in the display of another table view
	 * reflecting the next level in the data-model hierarchy.
	 */
	DISCLOSURE_INDICATOR,
	
	/**
	 * You use the detail disclosure button when selecting
	 * a cell results in a detail view of that item (which
	 * may or may not be a table view).
	 */
	DETAIL_DISCLOSURE,
	
	/**
	 *  You use a checkmark when a touch on a row results
	 *  in the selection of that item. This kind of table view
	 *  is known as a selection list, and it is analogous to a
	 *  pop-up list. Selection lists can limit selections to
	 *  one row, or they can allowed multiple rows with
	 *  checkmarks.
	 */
	CHECKMARK;
}
