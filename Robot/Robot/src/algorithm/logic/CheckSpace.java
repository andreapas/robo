package algorithm.logic;

import algorithm.Position;
import algorithm.mediator.Mediator;

public class CheckSpace {

	private Position startPosition;
	private static CheckSpace checker= new CheckSpace();
	
	
	public CheckSpace() {
	
	}
	
	public static CheckSpace getChecker() {
		return checker;
	}
	
	public boolean startChecking(){
		//TODO: fare qualcosa. Un po' come elisa quando programma: roba inutile, ma che fa perdere tempo
		startPosition= Mediator.getMed().getActualPosition();
		return false;
//		while(true){
//		}
	}
	
}
