package dynamicalSystems;
import java.util.ArrayList;
import java.util.Iterator;

/**A Stock uses the input or the output given by flows (inflows,outFlows),
 * to add or to subtract its values.
 * Only flows can be connected to stocks.
 * <p>
 * When you want the stock to be connected to Converters,or other Stocks,
 * you shall use converters and set them as stocks.
 * 
 * @see Converter#asStock
 * 
 * @author Wellington Bruno
 *
 */

public class Stock extends Element{	
	private ArrayList <Flow> inFlow;
	private ArrayList <Flow> outFlow;
	
	/**Constructs a Stock containing just a name,without a initial Value.
	 * In this case, the value will be zero.
	 * 
	 * @param name The Stock's name
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Stock(String name)throws IllegalArgumentException{
		super(name);
		inFlow = new ArrayList <Flow>();
		outFlow = new ArrayList <Flow>();		
	}
	/**Constructs a Stock containing a name, and a initial Value.
	 * 
	 * @param name The Stock's name
	 * @param v The Stock's initial Value
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Stock(String name,double v)throws IllegalArgumentException{
		super(name,v);
		inFlow = new ArrayList <Flow>();
		outFlow = new ArrayList <Flow>();		
	}
	protected void update(){
		if(Simulation.currentTime != Simulation.initialTime && this.released){		
			double value;		
			value = sumValues(inFlow)- sumValues(outFlow);		
			value = (this.getValue()+ (value * Element.dt));	
			value = (Math.round( value * 100000.0 ) ) / 100000.0;
			setValue(value);
			
		}
		this.released = false;
		
		Iterator <Flow>i = inFlow.iterator();
		Flow flow;
		while (i.hasNext()){
			flow = (Flow)i.next();
			if (flow.getDeterminants().contains(this)&& flow.released){				
				flow.update();						
			}	
			else if ((!flow.getDeterminants().contains(this))&& flow.released){ 			
				flow.update();						
			}
		}
			
		i = outFlow.iterator();		
		while (i.hasNext()){
			flow = (Flow)i.next();
			if (flow.getDeterminants().contains(this)&& flow.released ){ 				
				flow.update();						
			}	
			else if ((!flow.getDeterminants().contains(this))&& flow.released){ 			
				flow.update();						
			}			
		}	
	}
	protected double sumValues(ArrayList <Flow> list){
		Iterator <Flow>i = list.iterator();
		double result = 0.0;
		Flow flow;
		while (i.hasNext()){
			flow = (Flow)i.next();
			result += flow.getValue();
		}
		return result;
	}
	
	/**Inserts a Flow to the inFlow and the determinants lists
	 * 
	 * @param f The Flow to be inserted
	 */
	public void insertInflow(Flow f){
		inFlow.add(f);
		determinants.add(f);
	}
	/**Inserts a Flow to the outFlow and the determinants lists
	 * 
	 * @param f The Flow to be inserted
	 */
	public void insertOutflow(Flow f){
		outFlow.add(f);
		determinants.add(f);
	}
	public ArrayList<Flow> getInFlow() {
		return inFlow;
	}
	public void setInFlow(ArrayList<Flow> inFlow) {
		this.inFlow = inFlow;
	}
	public ArrayList<Flow> getOutFlow() {
		return outFlow;
	}
	public void setOutFlow(ArrayList<Flow> outFlow) {
		this.outFlow = outFlow;
	}
	
	
}
