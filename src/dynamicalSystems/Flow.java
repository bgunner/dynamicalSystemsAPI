package dynamicalSystems;

import java.util.ArrayList;
import java.util.Iterator;

/** A Flow is an Element that is interrelated with the Stocks,
 * meaning that something is being subtracted/added from/to Stock.
 * <p>
 * A flow may also be interrelated with converters or even with other flows.
 * 
 * @author Wellington Bruno
 *
 */

public class Flow extends Element{				
	
	/**Constructs a Flow containing just a name,without a initial Value.
	 * In this case, the value will be zero.
	 * 
	 * @param name The Flow's name
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Flow(String name)throws IllegalArgumentException{
		super(name);		
		
	}
	/**Constructs a Flow containing a name, and a initial Value.
	 * 
	 * @param name The Flow's name
	 * @param v The Flow's initial Value
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Flow(String name,double value)throws IllegalArgumentException{
		super(name,value);		
		
	}
	protected void update(){		
		
		Iterator <Element>i = determinants.iterator();
		Element element;
		int indice = 0;
		while (i.hasNext()){
			element = (Element)i.next();			
				if (element.getDeterminants().contains(this)){
					this.released = false;	
					if (element.released )
						element.update();
					determinants.get(indice).setValue(element.getValue());
					indice ++;					
				}
				else if (!element.getDeterminants().contains(this)){
					this.released = false;					
					element.update();
					determinants.get(indice).setValue(element.getValue());
					indice ++;					
				}				
		}
		
		if (relationship != null){
			relationship.calculateResult();	
			double value = (relationship.getValue());						 
			this.value = (Math.round( value * 100000.0 ) ) / 100000.0;			
		}	   
	    this.released = false;		
	}
	
	/**Connects two Entities with the default multipliers,and tells which is the relation between them. 
	 * 
	 * @param a The first entity to be used by the relationship
	 * @param type The type of relationship. Must be one of the available characters in {@link Relationship}
	 * @param b The second entity to be used by the relationship
	 * @throws IllegalArgumentException If the type given is not a valid character.
	 * <p>
	 * To see the valid characters,
	 * 
	 * @see {@link Relationship#DIVIDED}
	 * @see {@link Relationship#MAX}
	 * @see {@link Relationship#MIN}
	 * @see {@link Relationship#MINUS}
	 * @see {@link Relationship#PRODUCT}
	 * @see {@link Relationship#SUM}	 
	 */
	public void connect (Entity a, char type, Entity b)throws IllegalArgumentException{
		Relationship r = new Relationship (a,type,b);		
		setRelationship(r);
	}
	/**Connects two Entities with the multipliers different than default,and tells which is the relation between them. 
	 * 
	 * @param fa The multiplier's value to the first Entity
	 * @param a The first Entity to be used by the relationship
	 * @param type The type of relationship. Must be one of the available characters in {@link Relationship}
	 * @param fb The multiplier's value to the second Entity
	 * @param b The second Entity to be used by the relationship
	 * @throws IllegalArgumentException If the type given is not a valid character.
	 * <p>
	 * To see the valid characters,
	 * 
	 * @see {@link Relationship#DIVIDED}
	 * @see {@link Relationship#MAX}
	 * @see {@link Relationship#MIN}
	 * @see {@link Relationship#MINUS}
	 * @see {@link Relationship#PRODUCT}
	 * @see {@link Relationship#SUM}
	 * 
	 */
	public void connect (double fa,Entity a,char type,double fb, Entity b)throws IllegalArgumentException{
		Relationship r = new Relationship (fa,a,type,fb,b);
		setRelationship(r);
	}
	/**Connects an Element and tells which is the relation.
	 * 
	 * @param a The element to be used by the Relationship
	 * @param op The type of relationship. Must be one of the available characters in {@link Relationship}
	 * @throws IllegalArgumentException If the type given is not a valid character.
	 * 
	 * To see, the valid characters to use this method,
	 * 
	 * @see {@link Relationship#EQUALS} 
	 * @see {@link Relationship#WITH_LOOKUPS}
	 */
	public void connect(Entity a,char op) throws IllegalArgumentException{
		Relationship r;
		if(op == Relationship.WITH_LOOKUPS){
			r = new Relationship(a,op,this);	
		}
		else{
			r = new Relationship(a,op);	
		}
		setRelationship(r);
	}
	/**Connects an Element using IfThenElse
	 * 
	 * @param op The character relative to {@link Relationship#IF_THEN_ELSE}
	 * @throws IllegalArgumentException If the type given is not a valid character.
	 * 
	 */
	public void connect(char op) throws IllegalArgumentException{
		if(op != Relationship.IF_THEN_ELSE)
			throw new IllegalArgumentException("The relationship to this connection should be IF_THEN_ELSE");
		Relationship r = new Relationship(this.getIfThenElse(),op);
		setRelationship(r);
	}
	/**Inserts a determinant in some element's determinant list. 
	 * 
	 * @param e The element to be inserted
	 * 
	 * @see Element#determinants
	 */
	public void insertDeterminant(Element e){
		this.determinants.add(e);
	}	
	
	
	

}
