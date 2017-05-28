package algorithm.logic;

import algorithm.GoalCoordinatesCalculator;
import algorithm.Position;
import algorithm.SensorInfo;
import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class CheckSpace {

	private Position startPosition= new Position();
	private Position positionX1= new Position();
	private Position positionX2= new Position();
	private Position otherPosition= new Position();
	private static CheckSpace checker= new CheckSpace();
	
	
	public CheckSpace() {
		startPosition.setPosition(Mediator.getMed().getActualPosition());
	}
	
	public static CheckSpace getChecker() {
		return checker;
	}
	
	public Position findGoal(){
		if(startPosition.getRadiants()>0.1|| startPosition.getRadiants()<-0.1){
			if(startPosition.getRadiants()>0)
				Mediator.getMed().rotateTo(0, Movements.ROTATE_RIGHT);
			else 
				Mediator.getMed().rotateTo(0, Movements.ROTATE_LEFT);
		}
		if(checkActual(Mediator.getMed().getCentralInfo())){
			Mediator.getMed().goStraight(0.8);
			positionX1.setPosition(startPosition);
			positionX2.setPosition(Mediator.getMed().getActualPosition());
			Mediator.getMed().rotateOf(Math.PI/2, Movements.ROTATE_LEFT);
			if(checkActual(Mediator.getMed().getCentralInfo())){
				Mediator.getMed().goStraight(0.8);
				otherPosition.setPosition(Mediator.getMed().getActualPosition());
			}
		}
			
		
		return GoalCoordinatesCalculator.lastTry(positionX1, positionX2, otherPosition);
//		while(true){
//		}
	}
	
	private boolean checkActual(SensorInfo sensor){
		if(sensor.getMinDistance()>1.2)
			return true;
		return false;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
}
