package algorithm.proxyMovements;

import java.util.HashMap;

public class ProxyMovement {

	private IMovement movement;
	
	public ProxyMovement() {
		movement=new StopMovement();
	}
	
	public HashMap<String, Double> move(){
		return movement.move();
	}
	
	public void selectMovementType(String movementType){
		if(movementType.equals(Movements.STRAIGHT_MOVEMENT)){
			movement= new StraightRun();
		}else if(movementType.equals(Movements.STOP)){
			movement=new StopMovement();
		}else if(movementType.equals(Movements.TURN_ON_YOURSELF)){
			movement=new RotateOnYourSelf();
		}
	}
}
