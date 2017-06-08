package algorithm;

import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class BugTwo {
	
	private Mediator mediator = null;
	private Position hitPosition;
	private boolean justHit;
	private SensorInfo boundarySensorInfo;
	private String boundaryRotationDirection;
	private boolean goalReached = false;
	private boolean failure = false;
	private double rect_m;
	private double rect_q;
	private double y;
	private double x;
	
	public BugTwo() {
		
		mediator = Mediator.getMed();
	}
	
	public void run() {

		calculateGoalRect();
		
		while(!goalReached && !failure) {
			
			motionToGoal();
			if(goalReached) break;
			boundaryFollowing();
		}
		
		if (goalReached) {
			System.out.println("Goal!\n");
		} else if (failure) {
			System.out.println("Fallito\n");
		} else {
			System.out.println("Boh\n");
		}
	}
	
	private void calculateGoalRect() {
		
		rect_m = (mediator.getGoal().getY() - mediator.getActualPosition().getY())/(mediator.getGoal().getX() - mediator.getActualPosition().getX());
		rect_q = mediator.getActualPosition().getY()-rect_m*mediator.getActualPosition().getX();
	}

	private void motionToGoal() {
		
		while(true) {
			
			if(isGoalReached()) {
				goalReached = true;
				break;
			}
			
			if(mediator.getCentralInfo().getMinDistance()<1.8) {
				if(mediator.getCentralInfo().getRightValue() > mediator.getCentralInfo().getRightValue()) {
					boundarySensorInfo = mediator.getLeftInfo();
					boundaryRotationDirection = Movements.ROTATE_RIGHT;
					break;
				} else {
					boundarySensorInfo = mediator.getRightInfo();
					boundaryRotationDirection = Movements.ROTATE_LEFT;
					break;
				}
			} else if(mediator.getLeftInfo().getMinDistance()<1.8 || mediator.getCentralInfo().getLeftValue()<1.8) {
				boundarySensorInfo = mediator.getLeftInfo();
				boundaryRotationDirection = Movements.ROTATE_RIGHT;
				break;
			} else if(mediator.getLeftInfo().getMinDistance()<1.8 || mediator.getCentralInfo().getRightValue()<1.8) {
				boundarySensorInfo = mediator.getRightInfo();
				boundaryRotationDirection = Movements.ROTATE_LEFT;
				break;
			} else {
				mediator.goStraight();
			}
			
		}
	}
	
	private void boundaryFollowing() {
		
		hitPosition = mediator.getActualPosition();
	    justHit = true;
		
		while(true) {
			
			if(isGoalReached()) {
				goalReached = true;
				break;
			}
			
			if(justHit == false && hitPosition.equals(mediator.getActualPosition())) {
				failure = true;
				break;
			}
			
			if(!justHit && isOnTheRect()) {
				break;
			}
			
			if (justHit) {
				if (!mediator.getActualPosition().equals(hitPosition)) {
					justHit = false;
				}
			}
			
			if (mediator.getCentralInfo().getMinDistance()<2.0) {
				mediator.rotateOf(0.3, boundaryRotationDirection);
			} else if (boundarySensorInfo.getMinDistance()<1.5) {
				mediator.rotateOf(0.3, boundaryRotationDirection);
			} else if (boundarySensorInfo.getMinDistance()>1.8) {
				mediator.rotateOf(0.3, boundaryRotationDirection);
			} else {
				mediator.goStraight();
			}		
		}
	}
	
	private boolean isOnTheRect() {
		
		y = mediator.getActualPosition().getY();
		x = mediator.getActualPosition().getX();
		return (y>(rect_m*x+rect_q)-0.5) && (y<(rect_m*x+rect_q)+0.5);
	}
	
	private boolean isGoalReached() {
		
		if (mediator.getDistanceFromGoal()<1) return true;

		return false;
	}
}
