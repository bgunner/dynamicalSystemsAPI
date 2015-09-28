package dynamicalSystems;
import java.util.ArrayList;
import java.util.Iterator;


/** A Converter is a auxiliary variable that holds some values
 * that will be used by Flows and others Converters, along the time intervals
 * 
 * @author Wellington Bruno 
 *
 */
public class Converter extends Element{	
	/**
	 * A converter usually is just a variable that holds its value at each time interval,
	 * without the need to know its last value.
	 * But sometimes, the Converter may act as a Stock,accumulating its value,
	 * depending on its last value.
	 * <p>
	 * In this moment, shall be used the Converter as stock
	 * 
	 * @see Converter#setAsStock()
	 */
	protected boolean asStock = false;
	/**Constructs a Converter containing just a name,without a initial Value.
	 * In this case, the value will be zero.
	 * 
	 * @param name The converter's name
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Converter(String name)throws IllegalArgumentException{
		super(name);	
		
	}
	/**Constructs a Converter containing a name, and a initial Value.
	 * 
	 * @param name The converter's name
	 * @param v The converter's initial Value
	 * @throws IllegalArgumentException If the chosen name is already being used
	 */
	public Converter(String name,double v)throws IllegalArgumentException{
		super(name,v);	
		
	}
	
	protected void update(){
		
		if(asStock && released){
			if (relationship != null ){
				relationship.calculateResult();			
				double value = relationship.getValue();	
				if(asStock){					
					value = (this.getValue()+ (value * Element.dt));
					value = (Math.round( value * 100000.0 ) ) / 100000.0;				
					setValue(value);
				}
			}
		}		
			
			Iterator <Element>i = determinants.iterator();
			Element element;	
			int index = 0 ;
			while (i.hasNext()){
		
				element = (Element)i.next();
				if (element.getDeterminants().contains(this)){ 
					this.released = false;	
					if (element.released )
						element.update();
					determinants.get(index).setValue(element.getValue());
					index ++;				
				}				
				else if (!element.getDeterminants().contains(this)){
					this.released = false;						
					element.update();
					determinants.get(index).setValue(element.getValue());
					index ++;					
				}				
			}
		
			
		if(! asStock){
			if (relationship != null ){
				relationship.calculateResult();			
				double value = relationship.getValue();	
				setValue ((Math.round( value * 100000.0 ) ) / 100000.0);			
			}
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
	/**Connects an Entity and tells which is the relation.
	 * 
	 * @param a The entity to be used by the Relationship
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
	public void setDeterminants(ArrayList<Element> determinants) {
		this.determinants = determinants;
	}
	/**
	 * Sets the Converter as a Stock
	 * 
	 * @see Converter#asStock
	 */
	public void setAsStock(){
		this.asStock = true;
	}


}
