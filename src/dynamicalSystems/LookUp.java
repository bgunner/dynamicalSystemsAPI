package dynamicalSystems;

import java.util.ArrayList;

/**A lookUp table is a table containing several lines.
 * Each line contains two pair of points, meaning the in value, and its respective out value.
 * 
 * @author Wellington Bruno
 *
 */

public class LookUp {	
	private double minimumX;
	private double maximumX;		
	private ArrayList <Row> table;	
	
	/**Constructs a default lookUp table 
	 */
	public LookUp(){
		table = new ArrayList <Row>();
	}

	protected double getMinimumX() {
		return minimumX;
	}	
	protected double getMaximumX() {
		return maximumX;
	}
	public ArrayList<Row> getTable() {
		return table;
	}
	/**Adjust the LookUp table
	 * 
	 * @param table The lookUp table to be adjusted
	 */
	public void setTable(ArrayList<Row> table) {
		this.table = table;
	}

	protected void setMinimumX(double minimumX) {
		this.minimumX = minimumX;
	}

	protected void setMaximumX(double maximumX) {
		this.maximumX = maximumX;
	}
	
	

	
}