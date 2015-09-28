package dynamicalSystems;

import java.util.Comparator;

/**A Row is one line in the lookUp table
 * For each input, there is an output.
 * Implements Comparator because each object created is inserted 
 * in the Lookup ordered by maximum input.
 * 
 * @author Wellington Bruno
 */
public class Row implements Comparator <Row>{
	private double input ;
	private double output;
	
	/**Constructs a Row object with a input value and its relative output
	 * 
	 * @param input The input Value
	 * @param output The output value for the given input
	 */
	public Row(double input, double output){
		setInput(input);
		setOutput(output);
	}
	
	/**
	 * Default Constructor
	 */
	public Row(){
		
	}
	/**Sets the Row input
	 * 
	 * @param in The input to be adjusted
	 */
	public void setInput(double in){
		this.input = in;
	}
	/**Sets the Row output
	 * 
	 * @param in The output to be adjusted
	 */
	public void setOutput(double out){
		this.output = out;
	}
	/**
	 * 
	 * @return the Row's input
	 */
	public double getInput(){
		return input;
	}
	/**
	 * 
	 * @return the Row's output
	 */
	public double getOutput(){
		return output;
	}
	
	/**Method extended from Comparator.	
	 */
	public int compare(Row r1, Row r2) {
		double d1 = r1.getInput();
		double d2 = r2.getInput();
		if (d1==d2)
			return 0;		
		else if(d1>d2)
			return 1;
		else 
			return -1;
		}
	
}
