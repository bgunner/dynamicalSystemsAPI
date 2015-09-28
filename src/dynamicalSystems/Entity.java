package dynamicalSystems;

/** A Entity is either an Element, or a Relationship, or an IfThenElse. 
 * 
 * @author Wellington Bruno
 */
public abstract class Entity {
	
	
	protected double value ;	
	
	/**Default constructor
	 * 
	 */
	public Entity(){
		
	}
	
	
	public abstract double getValue() ;

	protected abstract void setValue(double value) ;
	
	
	
	

}
