package algorithm;

import algorithm.mediator.Mediator;

public class BugTwo {
	
	Mediator mediator = null;
	Position hitPosition;
	boolean justHit;
	
	public BugTwo() {
		
		mediator = Mediator.getMed();
	}
	
	public void run() {
				
		while(true) {
			
			if(isGoalReached()) {
				//raggiunto goal
				break;
			}
			
			try {
				if(mediator.getCentralInfo()[10]<1.8) {
					if(mediator.getCentralInfo()[0] > mediator.getCentralInfo()[20]) {
						//leftBoundaryFollowing();
					} else {
						//rightBoundaryFollowing();
					}
				} else if(mediator.getLeftInfo()[10]<1.8 || mediator.getCentralInfo()[20]<1.8) {
					//leftBoundaryFollowing();
				} else if(mediator.getLeftInfo()[10]<1.8 || mediator.getCentralInfo()[20]<1.8) {
					//rightBoundaryFollowing();
				} else {
					mediator.goStraight();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void leftBoundaryFollowing() {
		
        try {
			hitPosition = mediator.getActualPosition();
	        justHit = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			
			try {

				if(isGoalReached()) {
					//raggiunto goal
					break;
				}
				
				if(justHit == false && hitPosition.equals(mediator.getActualPosition())) {
					//fallimento
					break;
				}
				
				if(justHit) {
					if (!mediator.getActualPosition().equals(hitPosition)) {
						justHit = false;
					}
				}
				
				//Qui ci va il boundary ecc...
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private boolean isGoalReached() {
		
		try {
			if (mediator.getDistanceFromGoal()<1) return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
