package dynamicalSystems;


import java.util.Iterator;

/**
 * This class manages how the system entities,
 * are interrelated.
 * For example : There are two entities 'a' and 'b' connected by a operator '+'.
 * this indicates that to calculate the result of that relationship,
 * will be done, a + b ( or b + a ).It depends on the precedence. The first parameter,
 * is the first entity to be considered.
 * May be a relationship among elements, or between one element and other relationship,
 * or even a relationship among relationships. On this last case,
 * the relations more internals, are considered firstly.
 * 
 * @author Wellington Bruno 
 *  
 */
public class Relationship extends Entity{
	/**The first Entity of the relationship 
	 */
	private Entity object1;
	/**The second Entity of the relationship 
	 */
	private Entity object2;
	
	/** This attribute indicates which the relation between two entities	
	 * 
	 */
	private char relation;	
	/**
	 * Factor1 and factor2 are the multipliers for the entities.
	 * the result is calculate like 
	 * (factor1 * object1)+(factor2 * object2) for example.
	 * 
	 */
	private double factor1 =1;
	private double factor2 =1;		
	
	/** When the relationship equation is an if_then_else equation,this attribute must be adjusted.
	 * you shall do it, for doing something like :
	 *  
	 * element.setIfThenElse(new IfThenElse(Condition,result1,result2));
	 * 
	 * and after it, doing
	 * 
	 * element.connect(Relationship.IF_THEN_ELSE);	  
	 */
	protected IfThenElse ifThenElse;
	/**Constant that indicates that the relationship is a LookUp. When the relationship is a lookUp,
	 * the out value,is find for a given table.	  
	 */
	public static final char WITH_LOOKUPS ='l';
	/**Indicates that the relationship must return the maximum value between which was given. 
	 */
	public static final char MAX ='>';
	/**Indicates that the relationship must return the minimum value between which was given.
	 */
	public static final char MIN='<';
	/**Indicates that element of this relationship is equals to the element passed by parameter	 
	 */
	public static final char EQUALS ='=';
	/**Indicates that there is an IfThenElse condition for the equation of this relationship	 
	 */
	public static final char IF_THEN_ELSE ='?';
	/**Indicates that the result of relationship, is the product of elements passed by parameter	  
	 */
	public static final char PRODUCT ='*';
	/**Indicates that the result of relationship, is the quotient of elements passed by parameter	  
	 */
	public static final char DIVIDED ='/';
	/**Indicates that the result of relationship, is the sum of elements passed by parameter	  
	 */
	public static final char SUM ='+';
	/**Indicates that the result of relationship, is the subtraction of elements passed by parameter	  
	 */
	public static final char MINUS ='-';
	
	/**Sometimes,the user may want the result of relationship be an integer value.
	 * This boolean tell if the relationship value is or not is an integer.
	 * <p>
	 * If the result must be an integer value,this attribute value must be true.
	 * This way,if the value is a double, it will be the integer part.	 
	 */
	private boolean valueAsInteger = false;
	
	/** This constructor shall be used when the relationship is either an IF_THEN_ELSE,
	 * or WITH_LOOKUPS, or EQUALS.
	 * <p>
	 * @param relation The way how the Entities are interrelated
	 * 
	 * @throws IllegalArgumentException If the parameter 'relation' is not a valid char
	 */
	public Relationship(Entity entity , char relation) throws IllegalArgumentException{
		if (relation != Relationship.WITH_LOOKUPS && relation != Relationship.EQUALS
				&& relation != Relationship.IF_THEN_ELSE)
			throw new IllegalArgumentException ("The relationship to this connection is not accepted");
		setRelation(relation);
		this.object1 = entity;	
		if(entity instanceof IfThenElse)
			this.ifThenElse = (IfThenElse)entity;
		calculateResult();
		
	}	
	/**Constructs a relationship with the default factors (multipliers)
	 * 
	 * @param ob1 The first Entity of relationship
	 * @param relation  The way how the entities are interrelated. 
	 * The user may use the constants available on this class, or enter a valid char that indicates
	 * what to do with the Entities.
	 * @param ob2 The second Entity of relationship
	 * @throws IllegalArgumentException If the parameter 'relation' is not a valid char
	 */
	public Relationship(Entity ob1, char relation, Entity ob2)throws IllegalArgumentException{
		if (relation == Relationship.EQUALS	|| relation == Relationship.IF_THEN_ELSE)
			throw new IllegalArgumentException ("The relationship for this connection is not accepted");
		this.object1 = ob1;
		this.object2 = ob2;
		setRelation(relation);
		calculateResult();
	}
	
	/**Constructs a relationship with the multipliers different than default
	 * For example,to do something like 
	 * <p>
	 * (2 *object1) + (3 * object2)
	 * <p>
	 *  you shall do new Relationship (2,object1,'+',3,object2);
	 * 
	 * @param f1 The multiplier for object1 value
	 * @param ob1 The first entity of relationship
	 * @param relation The way how the entities are interrelated. 
	 * The user may use the constants available on this class, or enter a valid char that indicates
	 * what to do with the Entities.
	 * @param f2 The multiplier for object2 value
	 * @param ob2 The second entity of relationship
	 * @throws IllegalArgumentException If the parameter 'relation' is not a valid char
	 */
	public Relationship(double f1,Entity ob1,char relation,double f2, Entity ob2)throws IllegalArgumentException{
		if (relation == Relationship.WITH_LOOKUPS || relation == Relationship.EQUALS
				|| relation == Relationship.IF_THEN_ELSE)
			throw new IllegalArgumentException ("The relationship for this connection is not accepted");
		this.object1 = ob1;
		this.object2 = ob2;
		factor1 = f1;
		factor2 = f2;
		setRelation(relation);
		calculateResult();
	}
	
	/**
	 * Default constructor 
	 * 
	 */
	public Relationship(){
		
	}	
	/** This method calculates, the relationship's equation result and saves it
	 * 
	 */
	protected void calculateResult(){
		if(this.relation == Relationship.IF_THEN_ELSE){
			setValue(Math.round (ifThenElse.getResult() * 100000.0) / 100000.0);
			return;
		}		
		double r = 0.0,r2 = 0.0 ;
		if (object1 instanceof Relationship){
			r = ((Relationship) object1).getValue();			
			if (object2 instanceof Relationship){
				r2 = ((Relationship) object2).getValue();
			}
			else if (object2 instanceof Element){
				r2 = ((Element) object2).getValue();
			}
			else if(object2 instanceof IfThenElse){
				r2 = ((IfThenElse)object2).getResult();
			}
		}
		
		else if (object1 instanceof Element){
			r = ((Element) object1).getValue();
			if (object2 instanceof Relationship){
				r2 = ((Relationship) object2).getValue();
			}
			else if (object2 instanceof Element){
				r2 = ((Element) object2).getValue();
			}
			else if(object2 instanceof IfThenElse){
				r2 = ((IfThenElse)object2).getResult();
			}
		}
		else if (object1 instanceof IfThenElse){
			r = ((IfThenElse)object1).getResult();
			if (object2 instanceof Relationship){
				r2 = ((Relationship) object2).getValue();
			}
			else if (object2 instanceof Element){
				r2 = ((Element) object2).getValue();
			}
			else if(object2 instanceof IfThenElse){
				r2 = ((IfThenElse)object2).getResult();
			}
		}
		setValue(Math.round( relation(r,r2) * 100000.0 ) / 100000.0);
								
	}
	
	
	private double relation(Double a, Double b){
		double result=0.0;
		switch(relation){
		case('+'):
			result =  (factor1 * a) + (factor2 * b);
			break;
		case('-'):
			result =  (factor1 * a) - (factor2 * b);
			break;
		case('/'):
			result =  (factor1 * a) / (factor2 * b);
			break;
		case('*'):
			result =  (factor1 * a) * (factor2 * b);
			break;
		case('<'):
			result =  getMin(a,b);
			break;
		case('>'):
			result =  getMax(a,b);
			break;		
		case('='):
			result = a;
			break;
		case('l'):
			result = getValueOnLookUp();
			break;
		}
		return result;
	}
	
	/**Gets the relationship's value
	 * 
	 */
	public double getValue() {
		this.calculateResult();		
		if(valueAsInteger){		
			if(value + 0.02 >= ((int)value)+1){
				setValue(((int)value)+1 ) ;	
				return value;	
			}
			setValue((int)value ) ;			
			return value;			
		}				
		return value;
	}	
	
	/**
	 * Sets the relationship value
	 * 
	 * @param resultado The value to be saved
	 */	
	protected void setValue(double resultado) {
		this.value = resultado;
	}	
	
	private  double getMax(double a, double b){
		if (a > b)
			return a;
		return b;
	}
	private double getMin(double a, double b){
		if (a < b)
			return a;
		return b;
	}	
	
	/**
	 * @return The object1 value 
	 */
	public Entity getObject1() {
		return object1;
	}
	
	protected void setObject1(Entity object1) {
		this.object1 = object1;
	}
	public Entity getObject2() {
		return object2;
	}
	protected void setObject2(Entity object2) {
		this.object2 = object2;
	}
	public char getRelation() {
		return relation;
	}	
	/**
	 * 
	 * @return the IfThenElse equation for the relationship
	 */
	public IfThenElse getIfThenElse() {
		return ifThenElse;
	}
	
	protected void setIfThenElse(IfThenElse ifThenElse) {
		this.ifThenElse = ifThenElse;
	}
	/**Sets the relation if it is a valid char
	 * 
	 * @param relation The way how the entities are interrelated. 
	 * @throws IllegalArgumentException If the parameter 'relation' is not a valid char
	 */
	protected void setRelation(char relation) throws IllegalArgumentException{
		if (relation != '+' && relation != '-' && relation != '*' && relation != '/'
			         && relation != '<'&& relation != '>' && relation != '?' && relation != '='
			        	 && relation != 'l')
			throw new IllegalArgumentException("the relationship informed is not valid") ;
		else
			this.relation = relation;
	}
	
	private double getValueOnLookUp(){
		double input;
		Entity e = null;
		if (object1 instanceof Element)
			 e = (Element) object1;
		else if (object1 instanceof Relationship)
			e = (Relationship) object1;
		input = e.getValue();	
		Element e2 = (Element) object2;
		LookUp look = e2.getLookUp();  
		if (input > look.getMaximumX())
			input = look.getMaximumX();
		if (input < look.getMinimumX())
			input = look.getMinimumX();
			
		Iterator <Row>i = look.getTable().iterator();		
		Row previous = null;
		int cont = 0;
		while(i.hasNext()){
			Row row = (Row) i.next();
			if(row.getInput() == input ){
				return row.getOutput();							
			}
			else if(cont != 0){
				if(input < row.getInput())
					return makesInterpolation(previous,row,input);				
			}
			previous = row;
			cont++;
		}
		
		return 0;
		
	}
	private double makesInterpolation(Row prev, Row next,double input){
		double a = next.getOutput() - prev.getOutput();
		double b = next.getInput() - prev.getInput();
		double inclination = a/b;
		double result = ( inclination * (input - prev.getInput()))+ prev.getOutput();		
		return result;
	}
	
	/**Set the relationship value as an integer value.
	 * Case it be a double, it will be the integer part.
	 */
	public void toInteger(){
		this.valueAsInteger = true;
		
	}

}
