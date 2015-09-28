package dynamicalSystems;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**An Element is the principal part of a Dynamic System.
 * A element is either a flow, or a stock, or a converter.
 * These elements are connected by relationships, that indicates 
 * how the elements are interrelated(the equations).
 * 
 * @author Wellington Bruno
 *
 */

public abstract class Element extends Entity{
	/**The object's name 
	 */
	protected String name;		
	
	/**During the simulation, the element may have different values.
	 * This attribute holds all the values that a particular element has,
	 * during the simulation 
	 */
	protected ArrayList <Double> allValues;
	
	/** A determinant is one Element that determines the value of other element.
	 * For example, elementA = elementB + elementC. Then,elementB and elementC are determinants of elementA.
	 * To calculate an element's value, is calculate before the value of its determinants. Because of this,
	 * it's very important tell which are the element's determinants, to avoid incorrect results on simulation.
	 * 
	 */
	protected ArrayList <Element> determinants;					
	
	/** Indicates that to calculate the element's value,
	 * must be done an analysis, and the value depends on this analysis.
	 * In an IfThenElse equation, if the Condition is true,the result will be the first parameter
	 * Case the condition is false, the result will be the second parameter.
	 * To adjust this attribute, the user shall call the method setIfThenElse();
	 */
	protected IfThenElse ifThenElse = new IfThenElse();
	/**Indicates if the element is or not is released to update.
	 * This attribute helps on update a element only once,for time interval. When the element is updated,
	 * this attribute receives the value false.When the time interval ends up, this attribute is released to
	 * update again. 
	 */
	protected boolean released = true; 
	
	/**relationship save the way how the determinants are connected.
	 * For example,given two determinants,what is the equation between them ? 
	 */
	protected Relationship relationship = null; 
	/**
	 * The time variation. Indicates if the time interval is either 1,or 0.5,or 0.25,or 0.125 .
	 */
	protected static double dt; 	
	/** This static attribute,store all elements created in the simulation 
	 * and will be useful to manage them (block them,release them),and update their values 
	 */
	protected static Simulation simulation = null;
	
	/** Allow create a table, indicating the output given a input.
	 * When the element's result depends on a table,must be used the attribute lookup.
	 * To insert values on the table, must be used Row objects.
	 * After the table (lookUp)is ready to be used, must be done something like :
	 * 
	 * element.setLookup(objectLookUp);
	 * element.connect(elementDeterminant,Relationship.WITH_LOOKUPS);
	 * 
	 */
	protected LookUp lookUp = new LookUp();
	
	/**Constructs an Element without a initial value.
	 * 
	 * @param name The element's name
	 * @throws IllegalArgumentException If the element's name is already being used
	 */
	public Element (String name)throws IllegalArgumentException{
		setName(name);
		allValues = new ArrayList<Double>();
		determinants = new ArrayList <Element>();
		
		if(simulation == null)
			simulation = new Simulation(0);
		simulation.insertElement(this);
		
		
	}
	/** Constructs an Element with a initial value
	 * 
	 * @param name The element's name
	 * @param value The element's initial Value
	 * @throws IllegalArgumentException If the element's name is already being used
	 */
	public Element (String name,double value)throws IllegalArgumentException{
		setName(name);
		setValue(value);
		allValues = new ArrayList<Double>();
		determinants = new ArrayList <Element>();		
		if(simulation == null)
			simulation = new Simulation(0);
		simulation.insertElement(this);		
		
	}
	
	/**Update the element's value.
	 * Each element, may update just once at each time interval 				
	 */
	protected abstract void update();
	
	/** 
	 * @return The element's name
	 */
	public String getName() {
		return name;
	}
	/**Adjust the element's name
	 * 
	 * @param name New name to be adjusted
	 * @throws IllegalArgumentException If the new element's name is already being used
	 */
	public void setName(String name)throws IllegalArgumentException {
		if(simulation != null){
			if (simulation.checkName(name))
				this.name = name;
		}
		else
			this.name = name;	
	}
	/**
	 * @return The element's value
	 */
	public double getValue() {
		return value;
	}
	/**Adjust the element's initial value
	 * 
	 * @param initialValue Value to be adjust to the element
	 */
	public void setInitialValue(double initialValue) {
		this.value = initialValue;		
	}	
	protected void setValue(double value){
		this.value= value;
	}
	/** Allow us get all the element's value in all time intervals
	 * 
	 * @return A list containing all the element's values
	 */
	public ArrayList<Double> getAllValues() {
		return allValues;
	}
	/** 
	 * @return A list containing all the element's determinants
	 */
	public ArrayList<Element> getDeterminants() {
		return determinants;
	}
	/** 
	 * @return the element's relationship
	 */
	public Relationship getRelationship() {
		return relationship;
	}
	/** Adjust the element's relationship
	 * 
	 * @param relationship The new Relationship to be adjusted
	 */ 
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}	
	/** 
	 * @return The ifThenElse equation
	 */
	public IfThenElse getIfThenElse() {
		return ifThenElse;
	}
	/**Adjust the ifThenElse equation to the element
	 * 
	 * @param ite The new IfThenElse equation to be adjusted
	 */
	public void setIfThenElse(IfThenElse ite){
		this.ifThenElse = ite;
	}
	
	/**Allow adjust the lookup table to the element
	 * 
	 * @param lookUp The Lookup object to be adjusted
	 */
	public void setLookUp(LookUp lookUp){		
		this.lookUp = lookUp;
	}
	/**
	 * 
	 * @return The lookup table stored by the element
	 */
	public LookUp getLookUp(){		
		Collections.sort(lookUp.getTable(),new Row());
		lookUp.setMaximumX(lookUp.getTable().get(lookUp.getTable().size()-1).getInput());
		lookUp.setMinimumX(lookUp.getTable().get(0).getInput());		
		return lookUp;
	}
	
	
}
