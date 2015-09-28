package example2;
import java.io.IOException;

import dynamicalSystems.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try{
			
			/*
			 * Firstly, we construct the Elements of the model
			 */
			
			Converter birthRate = new Converter("Birth Rate",0.23);
			Converter averageLifeTime = new Converter("Average lifeTime",8);
			Converter initialPopulation = new Converter("Initial Population",1000);
			Converter carryingCapacity = new Converter("Carrying Capacity",1000);
			Converter effectOfRabbit = new Converter("Effect of rabbit crowding on deaths");
			
			Flow births = new Flow ("Births");
			Flow deaths = new Flow ("Deaths");
			
			Stock rabbitPopulation = new Stock("Rabbit Population",initialPopulation.getValue());
			
			/*
			 * We are going to insert the elements determinants now.
			 */
			
			effectOfRabbit.insertDeterminant(rabbitPopulation);
			effectOfRabbit.insertDeterminant(carryingCapacity);
			
			births.insertDeterminant(birthRate);
			births.insertDeterminant(rabbitPopulation);
			
			deaths.insertDeterminant(averageLifeTime);
			deaths.insertDeterminant(rabbitPopulation);
			deaths.insertDeterminant(effectOfRabbit);
			
			rabbitPopulation.insertInflow(births);
			rabbitPopulation.insertOutflow(deaths);
			
			/*
			 * We are going to connect now, the elements.Constants don't need to be connected 
			 */
			
			/*
			 * The object 'effectOfRabbit',needs a LookUp function.
			 * Here,it's showed how to create this function
			 */
			
			// creates the LookUp object
			LookUp lookUp = new LookUp();
			
			//inserts now, the rows to the lookUp table
			lookUp.getTable().add(new Row(0,0.9));
			lookUp.getTable().add(new Row(1,1));
			lookUp.getTable().add(new Row(2,1.2));
			lookUp.getTable().add(new Row(3,1.5));
			lookUp.getTable().add(new Row(4,2));
			
			/* now, we just connect the determinants of 'effectOfRabbit'
			   Note that the input to the LookUp function is 'Rabbit Population/carrying capacity'
			   So, we have to create a relationship containing this equation, and then,
			   connect to 'effectOfRabbit'
			*/
			effectOfRabbit.setLookUp(lookUp);
			Relationship r1 = new Relationship(rabbitPopulation,Relationship.DIVIDED,carryingCapacity);
			effectOfRabbit.connect(r1,Relationship.WITH_LOOKUPS);
			
			/*
			 * Connecting the Flows now
			 */
			births.connect(rabbitPopulation, Relationship.PRODUCT, birthRate);
			
			/*
			 * The equation for deaths is (Rabbit Population / average lifetime)*effect of rabbit crowding on deaths
			 * So, we have to create a relationship for (Rabbit Population / average lifetime) and then
			 * connect this relationship to the effect of rabbit crowding on deaths
			 */
			
			Relationship r2 = new Relationship(rabbitPopulation,Relationship.DIVIDED,averageLifeTime);
			deaths.connect(r2,Relationship.PRODUCT,effectOfRabbit);
			
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
			
		}catch(IllegalArgumentException inv){
			inv.printStackTrace();
		}catch (IOException io) {
			io.printStackTrace();
		}
	}

}
