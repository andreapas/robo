package algorithm;

import algorithm.mediator.Mediator;

public class BugTwo {
	
	Mediator mediator = null;
	boolean motion = true;
	boolean boundary = false;
	boolean goal = false;
	
	public BugTwo() {
		
		mediator = Mediator.getMed();
	}
	
	public void run() {
				
		while(true) {
			
			if(isGoalReached()) {
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
		
		System.out.println("Finito, yeeeeee!");
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

	private boolean motionToGoal() {
	
		try {
			
			mediator.goStraight();
			if (mediator.getRightInfo()[10] < 2.0 || mediator.getLeftInfo()[10] < 2.0 || mediator.getCentralInfo()[10] < 2.0) {
				return false;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	private boolean boundaryFollowing() {
		
		return true;
	}
}
