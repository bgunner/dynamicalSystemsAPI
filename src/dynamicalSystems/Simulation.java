package dynamicalSystems;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Simulation {
	public static final double _1 = 1;
	public static final double _05 = 0.5;
	public static final double _025 = 0.25;
	public static final double _0125 = 0.125;
	protected static double currentTime;
	protected static double initialTime;
	protected static double finalTime;
	
	/*number_of_letters � o n�mero de letras do maior nome dado a um elemento
	 * � usado para formata��o do arquivo, se o usu�rio desejar gravar os dados
	 * com isso, independente do tamanho dos nomes, o arquivo n�o ficar� bagun�ado.
	*/
	protected static int number_of_letters ;
	
	private static ArrayList <Element> elements;
	
	protected Simulation(int arg){
		number_of_letters = 0;
		elements = new ArrayList<Element>();
	}
	public Simulation(){
		
	}
	
	protected void insertElement(Element e){
		elements.add(e);
		int n = e.getName().length();
		if(n > number_of_letters)
			number_of_letters = n;
	}
	
				/* M�todo atualizaValores, pecorre todos os elementos
				 * da simulacao e insere em cada lista de valores, o valor atual do elemento 
				 * � �til porque se n�o for chamado, apenas o elemento que iniciou
				 * a simulacao ter� sua lista de valores atualizada
				 */
	private void updateAllValues(){
		ArrayList<Element> list = Simulation.getElements();
		Iterator <Element>i = list.iterator();
		Element e;		
		while(i.hasNext()){
			e = (Element)i.next();
			e.allValues.add(e.getValue());			
		}
	}
	
	public static ArrayList<Element> getElements() {
		return elements;
	}

	protected void setElements(ArrayList<Element> elements) {
		Simulation.elements = elements;
	}
	
	
	
				/*M�todo para salvar os dados da simula��o no arquivo
				 * 
				 */
	
	public static void sendToFile(String fileName)throws IOException{
		String result ="";
		ArrayList<Element> list = getElements();
		Iterator <Element>i = list.iterator();
		Element e;
		String buf= String.format("\n\n%"+number_of_letters+"s","Time");
		result = result.concat (buf);
		for (double k = 0 ; k <= finalTime ;k += Element.dt ){
			buf= String.format("%"+number_of_letters+"s",k);
			result = result.concat (buf);			
		}
		
		save(fileName,result);
		
		while(i.hasNext()){
			e = (Element)i.next();			
			sendElement(fileName,e);
		}
		
	}
	private static void sendElement(String file,Element e)throws IOException{
		String buf =null;
		String result = "";
		ArrayList <Double> list = e.getAllValues();
		Iterator <Double> i = list.iterator();
		double value;
		int index = 0 ;	
				
		buf= String.format("\n%"+Simulation.number_of_letters+"s",e.getName());
		result = result.concat(buf);
		
		while(i.hasNext()){
			value = (Double)i.next();
			buf = String.format("%"+Simulation.number_of_letters+"s",value);
			result = result.concat(buf);
			index ++;			
		}		
		
		save(file,result);
		
	}
	
					/*M�todo para liberar os elementos no fim 
					 * de cada itera��o. Isto �,
					 * marca cada elemento como liberado para permiti-los 
					 * atualizarem seus valores
					 */
	private void releaseElements(){
		ArrayList<Element> list = getElements();
		Iterator <Element>i = list.iterator();
		Element e;
		while(i.hasNext()){
			e = (Element)i.next();
			e.released = true;
		}
	}
	
					/*Esse m�todo � chamado para validar se o nome escolhido
					 * pelo usu�rio para determinado elemento est� repetido
					 * Caso verdadeiro, lan�a uma exce��o
					 */
	protected boolean checkName(String name)throws IllegalArgumentException{
		ArrayList<Element> list = getElements();
		Iterator <Element>i = list.iterator();
		Element e;
		while(i.hasNext()){
			e = (Element)i.next();			
			if((e.getName().compareToIgnoreCase(name))== 0){
				throw new IllegalArgumentException("The given name is duplicated");
			}
		}
		return true;
	}
					/*Esse m�todo garante que no fim de cada itera��o,
					 * todo elemento presente na simulacao ter� seu valor
					 * atualizado. 
					 */
	
	private void completeSimulation(){
		ArrayList<Element> list = Simulation.getElements();
		Iterator <Element>i = list.iterator();
		Element e;		
		while(i.hasNext()){
			e = (Element)i.next();				
			if(e.released){				
				      e.update();
			}
		}
	}
					/*M�todo que inicia a Simulacao do modelo
					 * 
					 */
	public void start(double dt, int tempIn, int finalT)throws IllegalArgumentException{
		if (dt != _1 && dt != _05 && dt != _025 && dt != _0125 ){
			throw new IllegalArgumentException("The time interval should be one of the static final already defined");
		}
		Element.dt=dt;	
		initialTime = tempIn;	
		finalTime = finalT;		
		Element e = Simulation.getElements().get(0);
		
		for(currentTime =initialTime ; currentTime<=finalT ;currentTime+=dt){
			e.released = false;			
			e.update();
			completeSimulation();
			updateAllValues();
			releaseElements();			
		}		
	}	
	
	private static void save(String file, String content)throws IOException {
		FileWriter fw = new FileWriter(file,true);
		fw.write(content);
	    fw.close();
	}
	
	public static String carry(String fileName)throws FileNotFoundException, IOException {
		File file = new File(fileName);

		if (! file.exists()) {
			return null;
		}

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuffer bufSaida = new StringBuffer();

		String line;
		while( (line = br.readLine()) != null ){
			bufSaida.append(line + "\n");
		}
		br.close();
		return bufSaida.toString();
	}
}