package dynamicalSystems;

/**This class represents the condition to a given IfThenElse object.
 * A Condition may be created using elements,relationships, or even another condition
 * 
 * @author Wellington Bruno
 */
public class Condition extends Entity{
	
		/** 'op' is the operator that indicates which is the comparison to be done 
		 */
		protected char op;
		/**e1 is the first Entity to be compared.
		 * May be either an element,or a relationship, or another Condition
		 */
		protected Entity e1;
		/** e2 is the second Entity to be compared.
		 * May be either an element,or a relationship, or another Condition
		 */
		protected Entity e2;
		
		/**Sometimes, you may want negate some condition. This attribute represents the Not Function.
		 * If you want to negate some condition,
		 *  you shall turn this true,calling the method negateCondition()	  
		 */
		protected boolean negate = false;
		/** Indicates that the comparison to be done, is if entity1 <= entity2
		 */
		public static final char MINOR_EQUAL = 'l' ;
		/** Indicates that the comparison to be done, is if entity1 >= entity2
		 */
		public static final char LARGER_EQUAL = 'm' ;
		/** Indicates that the comparison to be done, is if entity1 = entity2
		 */
		public static final char EQUALS_TO = '=' ;
		/** Indicates that the comparison to be done, is if entity1 != entity2
		 */
		public static final char DIFFERENT = '#' ;
		/** Indicates that the comparison to be done, is if entity1 < entity2
		 */
		public static final char MINOR = '<';
		/** Indicates that the comparison to be done, is if entity1 > entity2
		 */
		public static final char LARGER = '>';
		/**Constructs a Condition object 
		 * 
		 * @param e1  The first Entity to be evaluated
		 * @param op  The condition to be evaluated
		 * @param e2  The second Entity to be evaluated
		 * @throws IllegalArgumentException If 'op' is not a valid char
		 */
		public Condition(Entity e1,char op,Entity e2 )throws IllegalArgumentException{
			setOp(op);
			this.e1 = e1;
			this.e2 = e2;
			
		}
		
		private void setOp(char op)throws IllegalArgumentException{
			if(op != '>' && op != '<' && op != '='&& op != '|'&& op != '&' && op != 'm'&& op != 'l'
				&& op != '#'){
				throw new IllegalArgumentException("The operator to this condition is not valid");
			}
			this.op = op;
		}

		
		public double getValue() {			
			return 0;
		}		
		protected void setValue(double value) {			
			
		}
		
		/** This method evaluates if the condition is valid.
		 *  It means that the method evaluates if the condition is true or false.
		 * 
		 * @return True if the condition is true, or false, if the condition is false
		 */
		protected boolean isValid(){
			
			if (this.e1 instanceof Element){				
					return isTrue(this);
			}
			else if(this.e1 instanceof Relationship){
				return isTrue(this);
			}
			else if (this.e1 instanceof Condition){
				boolean a = ((Condition) this.e1).isValid();
				if (this.op == '|' && a){
				     return true;
				}
				else if (this.op == '|' && ! a){
					return ((Condition) this.e2).isValid();
				}
				else if (this.op == '&'){
					return a && ((Condition) this.e2).isValid();					
				}
				
			}
			return false;
		}
		private boolean isTrue(Condition b){
			if(b.op == '>'){
				if (b.e1.getValue() > b.e2.getValue())
					if (negate)
					   return false ;
					else
						return true;
				else
					if (negate)
						   return true ;
						else
							return false;					
			}
			else if (b.op == '<'){
				if (b.e1.getValue() < b.e2.getValue())
					if (negate)
						   return false ;
						else
							return true;
					else
						if (negate)
							   return true ;
							else
								return false;	
			}
			else if (b.op == 'l') {
				if (b.e1.getValue() <= b.e2.getValue())
					if (negate)
						   return false ;
						else
							return true;
					else
						if (negate)
							   return true ;
							else
								return false;	
			}	
			else if (b.op == 'm') {
				if (b.e1.getValue() >= b.e2.getValue())
					if (negate)
						   return false ;
						else
							return true;
					else
						if (negate)
							   return true ;
							else
								return false;	
			}	
			else if (b.op == '='){
				if (b.e1.getValue() == b.e2.getValue())
					if (negate)
						   return false ;
						else
							return true;
					else
						if (negate)
							   return true ;
							else
								return false;	
			}
			else{
				if (b.e1.getValue() != b.e2.getValue())
					if (negate)
						   return false ;
						else
							return true;
					else
						if (negate)
							   return true ;
							else
								return false;	
			}
		}
		/**
		 * When this method is called for a given condition, 
		 * the condition is negated.
		 */
		public void negateCondition(){
			this.negate = true;
		}
		
	}