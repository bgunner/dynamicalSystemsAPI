package example3;
import java.io.IOException;

import dynamicalSystems.*;

public class Main {

	public static void main(String[] args) {
		
		try{
					/*
					 * Firstly, we construct the Elements of the model
					 */
		
			Converter taskSize = new Converter("Task Size",180);
			Converter taskIsActive = new Converter("Task Is Active");
			Converter maxWorkAccomplishment = new Converter("Max Work Accomplishment",8);			
			
			Flow workAccomplishment = new Flow("Work Accomplishment");
			
			Stock workToDo = new Stock("Work To Do",taskSize.getValue());
			Stock workDone = new Stock("Work Done",0);
			
					/*
					 * Inserting now the element's determinants					 
					 */
			
			taskIsActive.insertDeterminant(taskSize);
			taskIsActive.insertDeterminant(workDone);
			
			workAccomplishment.insertDeterminant(maxWorkAccomplishment);
			workAccomplishment.insertDeterminant(taskIsActive);
			
			workToDo.insertOutflow(workAccomplishment);
			workDone.insertInflow(workAccomplishment);
			
					/*
					 * Now, we create the relationships among the elements
					 */
			
			/*
			 * The taskIsActive object, uses an IfThenElse condition.
			 * Here, is explained how to create relationships containing IfThenElse.
			 */
			
			Condition condition = new Condition(workDone,Condition.MINOR,taskSize);
			
			/*
			 * We create now, the complete IfThenElse object. Note that, the second and third parameters 
			 * are constants (1,0). After it, we just connect the ifThenElse object to the taskIsActive object.
			 */
			IfThenElse ifThenElse = new IfThenElse(condition,new Converter("Converter1",1),new Converter("Converter0",0));			
			taskIsActive.setIfThenElse(ifThenElse);
			taskIsActive.connect(Relationship.IF_THEN_ELSE);
			
			workAccomplishment.connect(maxWorkAccomplishment,Relationship.PRODUCT,taskIsActive);
			

			/*The relationship for Stocks is automatically created
			 * when you insert the in and out flows. 
			 */
			
			/* Now, your source code model is finished.
			 * All you got to do is create a Simulation object and 
			 * call the 'start' method to run the simulation		 
			 */
			
			Simulation simulation = new Simulation();
			int finalTime = 100,initialTime = 0;
			simulation.start(Simulation._0125, initialTime, finalTime);
			
			/*Saving the result of simulation,
			 * in a file on same directory, we're using 
			 * 'result.txt' is any name you want give to your file				 
			 */
			Simulation.sendToFile("result.txt");
			
		}catch(IllegalArgumentException inv){
			inv.printStackTrace();
		}catch (IOException io) {
			io.printStackTrace();
		}
		                                
		
	}

}
