package dynamicalSystems;

/**An IfThenElse object is formed by a Condition,and Two possible solution.
 * <p>
 * <b>condition</b> is the Condition to be evaluated.
 * <p>
 * <b>solutionCaseTrue</b> is the first Entity. If the condition is true,the ifThenElse result,
 * is the solutionCaseTrue's value.
 * <p>
 * <b>solutionCaseFalse</b> is the second Entity.If the condition is false, the ifThenElse result,
 *  is the solutionCaseFalse's value.
 * 
 * @author Wellington Bruno
 *
 */
public class IfThenElse extends Entity{
	private Condition condition;
	private Entity solutionCaseTrue;
	private Entity solutionCaseFalse;
	
	/**Constructs a IfthenElse object
	 * 
	 * @param condition is the Condition to be evaluated.	 
	 * @param solution1 is the Entity to be returned, if the condition is true
	 * @param solution2 is the Entity to be returned, if the condition is false
	 * 
	 * @see Condition
	 */
	public IfThenElse (Condition condition, Entity solution1,Entity solution2){
		this.condition = condition;
		solutionCaseTrue = solution1;
		solutionCaseFalse = solution2;
	}
	/** Default Constructor
	 * 
	 */
	public IfThenElse(){
		
	}
	/** 
	 * @return the Entity's value according the Condition.
	 */
	protected double getResult(){
		if (condition.isValid()){
			if(solutionCaseTrue instanceof IfThenElse)
				return ((IfThenElse) solutionCaseTrue).getResult();
			return solutionCaseTrue.getValue();
		}
		if(solutionCaseFalse instanceof IfThenElse)
			return ((IfThenElse) solutionCaseFalse).getResult();
		return solutionCaseFalse.getValue();
	}
	
	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Entity getSolutionCaseTrue() {
		return solutionCaseTrue;
	}

	public void setSolutionCaseTrue(Entity solutionCaseTrue) {
		this.solutionCaseTrue = solutionCaseTrue;
	}

	public Entity getSolutionCaseFalse() {
		return solutionCaseFalse;
	}

	public void setSolutionCaseFalse(Entity solutionCaseFalse) {
		this.solutionCaseFalse = solutionCaseFalse;
	}
	public double getValue() {			
		return getResult();
	}		
	protected void setValue(double value) {			
		
	}
	

}
