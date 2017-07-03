package algorithm;

import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class BugTwo {
	private static final double RANGE_FAILURE = 0.5;
	private static final double BOUNDARY_MAX = 1.2;
	private static final double BOUNDARY_MIN = 1.0;
	private static final double OPPOSITE_BOUNDARY = 1.0;
	private static final double CENTER_DIST = 1.0;
	private static final double ROTATION_OFFSET = 0.1;
	private Position hitPosition = new Position();
	private boolean justHit;
	private String boundaryDirection;
	private boolean goalReached = false;
	private boolean failure = false;
	private boolean already_touched=false;
	private double rect_arc;
	private double arcToGoal;
	private String rotateTo=Movements.ROTATE_RIGHT;

	public void run() {

		
		rect_arc = calculateGoalRect();

		while (!goalReached && !failure) {
			System.out.println("\t Motion to Goal Phase");
			motionToGoal();
			if (goalReached)
				break;
			if (failure) {
				break;
			}
			System.out.println("\t Boundary Following Phase");
			boundaryFollowing();
		}

		if (goalReached) {
			System.out.println("Goal!\n");
		} else if (failure) {
			System.out.println("There is no solution!\n");
		}
	}

	private double calculateGoalRect() {
		Position tmp = Mediator.getMed().getActualPosition();
		double numerator = Mediator.getMed().getGoal().getY() - tmp.getY();
		double denominator = Mediator.getMed().getGoal().getX() - tmp.getX();

		return Math.atan2(numerator, denominator); 
	}

	private void motionToGoal() {

		rotateToGoal(rotateTo);

		while (true) {
			if (isGoalReached()) {
				goalReached = true;
				break;
			}

			if (Mediator.getMed().getCentralInfo().getMinDistance() < CENTER_DIST) {
					boundaryDirection = decideWhereToGo();
					break;
			}else {
				Mediator.getMed().goStraight();
			}

		}
	}

	private String decideWhereToGo(){
		double val=Math.random();
		if (val > 0.5) {
			return "r";
		} else {
			return "l";
		}
	}
	
	private void boundaryFollowing() {
		hitPosition.setPosition(Mediator.getMed().getActualPosition());
		justHit = true;
		already_touched=true;
		while (true) {

			if (isGoalReached()) {
				goalReached = true;
				break;
			}

			Position tmp = Mediator.getMed().getActualPosition();
			if (already_touched == false && isInRange(tmp.getX(), hitPosition.getX(), RANGE_FAILURE) && isInRange(tmp.getY(), hitPosition.getY(), RANGE_FAILURE)) {
				
				failure = true;
				break;
			}
			
			if(already_touched){
				already_touched=(isInRange(tmp.getX(), hitPosition.getX(), RANGE_FAILURE) || isInRange(tmp.getY(), hitPosition.getY(), RANGE_FAILURE));
			}

			if (!justHit && isOnTheRect() && tmp.getDistanceFromGoal() < hitPosition.getDistanceFromGoal()) {
				rotateTo=rotationDirection();
				//System.out.println("boundary Direction: "+boundaryDirection+" rotationFound: "+rotateTo);
				if(!((rotateTo.equals(Movements.ROTATE_RIGHT)&&boundaryDirection.equals("r"))||(rotateTo.equals(Movements.ROTATE_LEFT)&&boundaryDirection.equals("l")))){
					break;
				}
			}

			if (justHit) {

				if (!isOnTheRect()) {
					justHit = false;
				}

			}
			if (boundaryDirection.equals("l")) {
				if (Mediator.getMed().getCentralInfo().getMinDistance() < CENTER_DIST||Mediator.getMed().getRightInfo().getMinDistance() < OPPOSITE_BOUNDARY) {
					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.ROTATE_RIGHT);
				} else if (Mediator.getMed().getLeftInfo().getMinDistance() < BOUNDARY_MIN) {

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_RIGHT);

				} else if (Mediator.getMed().getLeftInfo().getMinDistance() > BOUNDARY_MAX) {

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_LEFT);
				} else {

					Mediator.getMed().goStraight();
				}
			} else if (boundaryDirection.equals("r")) {
				if (Mediator.getMed().getCentralInfo().getMinDistance() < CENTER_DIST||Mediator.getMed().getLeftInfo().getMinDistance() < OPPOSITE_BOUNDARY) {
					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.ROTATE_LEFT);
				} else if (Mediator.getMed().getRightInfo().getMinDistance() < BOUNDARY_MIN) {

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_LEFT);

				} else if (Mediator.getMed().getRightInfo().getMinDistance() > BOUNDARY_MAX) {

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_RIGHT);
				} else {

					Mediator.getMed().goStraight();
				}
			} else {
				System.out.println("Errore");
			}
		}
	}

	private String rotationDirection(){
		double newArcToGoal;
		double myArc;
		Position tmp = new Position();
		tmp.setPosition(Mediator.getMed().getActualPosition());
		if (arcToGoal < 0) {
			newArcToGoal = arcToGoal + 2 * Math.PI;
		} else {
			newArcToGoal = arcToGoal;
		}
		if (tmp.getRadiants() < 0) {
			myArc = tmp.getRadiants() + 2 * Math.PI;
		} else {
			myArc = tmp.getRadiants();
		}
		if (myArc > 0 && myArc < Math.PI) {
			if (myArc < newArcToGoal && newArcToGoal < myArc + Math.PI) {
				return Movements.ROTATE_LEFT;
			} else {
				return Movements.ROTATE_RIGHT;
			}
		} else {
			if (myArc - Math.PI < newArcToGoal && newArcToGoal < myArc) {
				return Movements.ROTATE_RIGHT;
			} else {
				return Movements.ROTATE_LEFT;
			}
		}
	}
	
	
	private void rotateToGoal(String direction) {
		arcToGoal = calculateGoalRect();
		Mediator.getMed().rotateTo(arcToGoal, direction);
	}

	private boolean isOnTheRect() {
		Position tmp = Mediator.getMed().getActualPosition();
		boolean output;
		double y = tmp.getY();
		double x = tmp.getX();

		double numerator = (Mediator.getMed().getGoal().getY() - y);
		double denominator = (Mediator.getMed().getGoal().getX() - x);
		double arc = Math.atan2(numerator, denominator);
		output = (isInRange(Math.abs(arc), Math.abs(rect_arc), 0.1));
		return output;
	}

	private boolean isGoalReached() {

		if (Mediator.getMed().getDistanceFromGoal() < 1)
			return true;

		return false;
	}

	private boolean isInRange(double actual, double expected, double error) {
		boolean evaluateLower = actual > (expected - error);
		boolean evaluateUpper = actual < (expected + error);
		return (evaluateLower && evaluateUpper);

	}
}
