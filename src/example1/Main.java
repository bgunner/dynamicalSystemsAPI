package example1;
import java.io.IOException;

import dynamicalSystems.*;

public class Main {
	
	
	
	public static void main(String [] args){
		
		/* The source code using the dynamicalSystems API must be surrounded
		 * by a try/catch, because may occur a IllegalArgumentException 
		 */
		
		try {
			
			/*
			 * Firstly, is needed construct the model's elements.
			 * Here, are constructed the Converters
			 * Birth Rate initializes with the value 0.125
			 * Average LifeTime initializes with the value 8
			 */
			Converter c1 = new Converter("Birth Rate",0.125);
			Converter c2 = new Converter("Average LifeTime",8);
			
			/* Here, we construct the Flows		
			 */
			Flow f1 = new Flow("Births");
			Flow f2 = new Flow("Deaths");
			
			/*Finally, we construct now the Stock 
			 * Population has 1000 as it initial value 
			 */
			Stock s1 = new Stock("Rabbit Population",1000);
			
			/*After constructs all the model's elements,
			 * we must connect each one with its determinants.
			 * A determinant is one element which influences the value of another
			 * The constants, don't need to be connected ( Note that c1 and c2 are constants)		
			 */
			
			 f1.insertDeterminant(s1);
			 f1.insertDeterminant(c1);
			 
			 f2.insertDeterminant(s1);
			 f2.insertDeterminant(c2);
			 
			 /* For the Stocks, we don't insert determinants.
			  * Indeed, we insert either inFlows or OutFlows. 
			  */
			s1.insertInflow(f1);
			s1.insertOutflow(f2);
			
			/*Now, the elements are connected with their relative determinants,
			 * we are going to create the relationships (equations) for each one.		
			 */
			
			/*Creating a Relationship for the Flow 'Births'
			 * 
			 * births = Rabbit Population * birth rate			 
			 */
			
			f1.connect(s1,Relationship.PRODUCT,c1);
			
			/*Creating now, the relationship for the Flow 'Deaths'
			 * 
			 * deaths = Rabbit Population / average lifetime
			 */
			
			f2.connect(s1,Relationship.DIVIDED,c2);	
			
			/*The relationship for Stocks is automatically created
			 * when you insert the in and out flows. 
			 */
			
			/* Now, your source code model is finished.
			 * All you got to do is create a Simulation object and 
			 * call the 'start' method to run the simulation		 
			 */
			
			Simulation simulation = new Simulation();
			int finalTime = 30,initialTime = 0;
			simulation.start(Simulation._0125, initialTime, finalTime);
			
			/*Saving the result of simulation,
			 * in a file on same directory, we're using 
			 * 'result.txt' is any name you want give to your file			 
			 */
			Simulation.sendToFile("result.txt");
			
		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
		
		
	}
	
	

}
