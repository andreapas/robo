package algorithm.proxyMovements;

import java.util.HashMap;

public class ProxyMovement {

	
	public static String STRAIGHT_MOVEMENT="go_straight";
	public static String STOP="stop";
	public static String TURN_ON_YOURSELF="rotate";
	private IMovement movement;
	
	public ProxyMovement() {
		movement=new StopMovement();
	}
	
	public HashMap<String, Double> move(){
		return movement.move();
	}
	
	public void selectMovementType(String movementType){
		if(movementType.equals(STRAIGHT_MOVEMENT)){
			movement= new StraightRun();
		}else if(movementType.equals(STOP)){
			movement=new StopMovement();
		}else if(movementType.equals(TURN_ON_YOURSELF)){
			movement=new RotateOnYourSelf();
		}
	}
}
