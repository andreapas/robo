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
		}else if(movementType.equals(Movements.ROTATE_LEFT)){
			movement=new RotateLeft();
		}else if(movementType.equals(Movements.ROTATE_RIGHT)){
			movement=new RotateRight();
		}else if(movementType.equals(Movements.BACK)){
			movement=new GoBack();
		}
	}
}
