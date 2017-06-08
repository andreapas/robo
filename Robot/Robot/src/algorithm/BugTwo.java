package algorithm;

import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class BugTwo {
	
	private Position hitPosition;
	private boolean justHit;
	private SensorInfo boundarySensorInfo;
	private String boundaryRotationDirection;
	private String antiBoundaryRotationDirection;
	private boolean goalReached = false;
	private boolean failure = false;
	private double rect_m;
	private double rect_q;
	private double y;
	private double x;
	private double mToGoal;
	
	
	public void run() {

		calculateGoalRect();
		
		while(!goalReached && !failure) {
			System.out.println("Sono il codice di DAVIDE-- inizio il motion");
			motionToGoal();
			System.out.println("Sono il codice di DAVIDE-- if verificato? "+ goalReached);
			if(goalReached) break;
			System.out.println("Sono il codice di DAVIDE-- inizio il boundary");
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
		
		rect_m = (Mediator.getMed().getGoal().getY() - Mediator.getMed().getActualPosition().getY())/(Mediator.getMed().getGoal().getX() - Mediator.getMed().getActualPosition().getX());
		rect_q = Mediator.getMed().getActualPosition().getY()-rect_m*Mediator.getMed().getActualPosition().getX();
	}

	private void motionToGoal() {
		
		while(true) {
			
			if(isGoalReached()) {
				goalReached = true;
				break;
			}
			
			if(Mediator.getMed().getCentralInfo().getMinDistance()<1.8) {
				if(Mediator.getMed().getCentralInfo().getRightValue() > Mediator.getMed().getCentralInfo().getLeftValue()) {
					boundarySensorInfo = Mediator.getMed().getLeftInfo();
					boundaryRotationDirection = Movements.ROTATE_RIGHT;
					antiBoundaryRotationDirection = Movements.ROTATE_LEFT;
					break;
				} else {
					boundarySensorInfo = Mediator.getMed().getRightInfo();
					boundaryRotationDirection = Movements.ROTATE_LEFT;
					antiBoundaryRotationDirection = Movements.ROTATE_RIGHT;
					break;
				}
			} else if(Mediator.getMed().getLeftInfo().getMinDistance()<1.8 || Mediator.getMed().getCentralInfo().getLeftValue()<1.8) {
				boundarySensorInfo = Mediator.getMed().getLeftInfo();
				boundaryRotationDirection = Movements.ROTATE_RIGHT;
				antiBoundaryRotationDirection = Movements.ROTATE_LEFT;
				break;
			} else if(Mediator.getMed().getRightInfo().getMinDistance()<1.8 || Mediator.getMed().getCentralInfo().getRightValue()<1.8) {
				boundarySensorInfo = Mediator.getMed().getRightInfo();
				boundaryRotationDirection = Movements.ROTATE_LEFT;
				antiBoundaryRotationDirection = Movements.ROTATE_RIGHT;
				break;
			} else {
				rotateToGoal();
				Mediator.getMed().goStraight();
			}
			
		}
	}
	
	private void boundaryFollowing() {
		
		System.out.println("Sono il codice di DAVIDE-- calcolo hit position");
		hitPosition = Mediator.getMed().getActualPosition();
		System.out.println("Sono il codice di DAVIDE-- hit position= "+hitPosition);

	    justHit = true;
		System.out.println("Sono il codice di DAVIDE-- justHit= "+justHit);

		
		System.out.println("Sono il codice di DAVIDE-- entro nel while...");

		while(true) {
			System.out.println("\tSono il codice di DAVIDE-- check se ho trovato il Goal!");

			if(isGoalReached()) {
				goalReached = true;
				break;
			}
			
			System.out.println("\tSono il codice di DAVIDE-- non l'ho trovato, mi tocca ancora camminare");

			if(justHit == false && hitPosition.equals(Mediator.getMed().getActualPosition())) {
				System.out.println("\tSono il codice di DAVIDE-- primo if");

				failure = true;
				break;
			}
			
			if(!justHit && isOnTheRect()) {
				System.out.println("\tSono il codice di DAVIDE-- secondo if");

				break;
			}
			
			if (justHit) {
				System.out.println("\tSono il codice di DAVIDE-- terzo if");

				if (!Mediator.getMed().getActualPosition().equals(hitPosition)) {
					System.out.println("\tSono il codice di DAVIDE-- quarto if");

					justHit = false;
				}
			}
			System.out.println("STO PER FARE IL MIN DIST");
			if (Mediator.getMed().getCentralInfo().getMinDistance()<2.0) {
				System.out.println("\tSono il codice di DAVIDE-- quinto if  "+Mediator.getMed().getCentralInfo().getMinDistance());

				Mediator.getMed().rotateOf(0.1, boundaryRotationDirection);
			} else if (boundarySensorInfo.getMinDistance()<1.3) {
				System.out.println("\tSono il codice di DAVIDE-- sesto if");

				Mediator.getMed().rotateOf(0.1, boundaryRotationDirection);

			} else if (boundarySensorInfo.getMinDistance()>1.8) {
				System.out.println("\tSono il codice di DAVIDE-- settimo if  "+Mediator.getMed().getCentralInfo().getMinDistance());

				Mediator.getMed().rotateOf(0.1, antiBoundaryRotationDirection);
			} else {
				System.out.println("\tSono il codice di DAVIDE-- ottavo if");

				Mediator.getMed().goStraight();
			}		
		}
	}
	
	private void rotateToGoal() {
		
		mToGoal = (Mediator.getMed().getGoal().getY() - Mediator.getMed().getActualPosition().getY())/(Mediator.getMed().getGoal().getX() - Mediator.getMed().getActualPosition().getX());
		Mediator.getMed().rotateTo(Math.atan(mToGoal), Movements.ROTATE_RIGHT);
	}

	private boolean isOnTheRect() {
		
		y = Mediator.getMed().getActualPosition().getY();
		x = Mediator.getMed().getActualPosition().getX();
		return (y>(rect_m*x+rect_q)-0.5) && (y<(rect_m*x+rect_q)+0.5);
	}
	
	private boolean isGoalReached() {
		
		if (Mediator.getMed().getDistanceFromGoal()<1) return true;

		return false;
	}
}
