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
		startPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
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
			identifySecondXAndOther("forward");
		}else if(checkActual(Mediator.getMed().getBackInfo())){
			identifySecondXAndOther("backward");
		}else{
			otherPosition.setPosition(startPosition);
			Mediator.getMed().rotateOf(Math.PI/2, Movements.ROTATE_LEFT);
			if(checkActual(Mediator.getMed().getCentralInfo())){
				identifySecondXAndOther("forward");
			}else if(checkActual(Mediator.getMed().getBackInfo())){
				identifySecondXAndOther("backward");
			}
		}
			
		
		return GoalCoordinatesCalculator.findGoal(positionX1, positionX2, otherPosition);
//		while(true){
//		}
	}
	
	private void identifySecondXAndOther(String direction){
		positionX1.setPosition(startPosition);
		if(direction.equals("forward"))
			Mediator.getMed().goStraight(0.8);
		else
			Mediator.getMed().goBack(0.8);
		positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		Mediator.getMed().rotateOf(Math.PI/2, Movements.ROTATE_LEFT);
		if(checkActual(Mediator.getMed().getCentralInfo())){
			Mediator.getMed().goStraight(0.8);
			otherPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		}else if(checkActual(Mediator.getMed().getBackInfo())){
			Mediator.getMed().goBack(0.8);
			otherPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		}else{
			//torna indietro checkando
		}
	}
	
	private void identifyXsPoints(String direction){
		if(direction.equals("forward"))
			Mediator.getMed().goStraight(0.8);
		else
			Mediator.getMed().goBack(0.8);		Mediator.getMed().goStraight(0.8);
		positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		if(checkActual(Mediator.getMed().getCentralInfo())){
			Mediator.getMed().goStraight(0.8);
			positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		}else if(checkActual(Mediator.getMed().getBackInfo())){
			Mediator.getMed().goBack(0.8);
			positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		}else{
			//torna indietro checkando in modo diverso
		}
	}
	
	private boolean checkActual(SensorInfo sensor){
		//eventualmente somma avanti e dietro
		return sensor.getMinDistance()>1.2;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
}
