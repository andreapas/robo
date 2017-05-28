package algorithm.logic;

import algorithm.Position;
import algorithm.mediator.Mediator;

public class CheckSpace {

	private Position startPosition;
	private static CheckSpace checker= new CheckSpace();
	
	
	public CheckSpace() {
		startPosition= Mediator.getMed().getActualPosition();
	}
	
	public static CheckSpace getChecker() {
		return checker;
	}
	
	public boolean startChecking(){
		//TODO: fare qualcosa. Un po' come elisa quando programma: roba inutile, ma che fa perdere tempo
		
		
		
		return false;
//		while(true){
//		}
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
}
