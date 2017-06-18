package algorithm;

import com.sun.java.swing.plaf.motif.MotifEditorPaneUI;

import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class BugTwo {
	private static final double BOUNDARY_MAX = 1.8;
	private static final double BOUNDARY_MIN = 1.3;
	private static final double CENTER_DIST = 1.6;
	private static final double ROTATION_OFFSET = 0.1;
	//TODO: errore nel rotateOf if quinto, problema nella svolta angolare.
	private Position hitPosition= new Position();
	private boolean justHit;
	private String boundaryDirection;
	private SensorInfo boundarySensorInfo;
	private String boundaryRotationDirection;
	private String antiBoundaryRotationDirection;
	private boolean goalReached = false;
	private boolean failure = false;
	private double rect_m;
	private double rect_q;
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

			if(Mediator.getMed().getCentralInfo().getMinDistance()<CENTER_DIST) {
				if(Mediator.getMed().getCentralInfo().getRightValue() > Mediator.getMed().getCentralInfo().getLeftValue()) {

					boundaryDirection = "l";
					break;
				} else {

					boundaryDirection = "r";
					break;
				}
				
			} else if(Mediator.getMed().getLeftInfo().getMinDistance()<1.8 || Mediator.getMed().getCentralInfo().getLeftValue()<CENTER_DIST) {

				boundaryDirection = "l";
				break;
			} else if(Mediator.getMed().getRightInfo().getMinDistance()<1.8 || Mediator.getMed().getCentralInfo().getRightValue()<CENTER_DIST) {

				boundaryDirection = "r";
				break;
			} else {
				rotateToGoal();
				Mediator.getMed().goStraight();
			}
			
		}
	}
	
	private void boundaryFollowing() {
		
		System.out.println("Sono il codice di DAVIDE-- bound rot dir= "+ boundaryRotationDirection);
		System.out.println("Sono il codice di DAVIDE-- calcolo hit position");
		hitPosition.setPosition(Mediator.getMed().getActualPosition());
		System.out.println("Sono il codice di DAVIDE-- hit position= "+hitPosition);

	    justHit = true;
		System.out.println("Sono il codice di DAVIDE-- justHit= "+justHit);

		
		System.out.println("Sono il codice di DAVIDE-- entro nel while...");

		while(true) {
//			System.out.println("\tSono il codice di DAVIDE-- check se ho trovato il Goal!");

			if(isGoalReached()) {
				goalReached = true;
				break;
			}
			
//			System.out.println("\tSono il codice di DAVIDE-- non l'ho trovato, mi tocca ancora camminare");

			if(justHit == false && hitPosition.equals(Mediator.getMed().getActualPosition())) {
//				System.out.println("\tSono il codice di DAVIDE-- primo if");

				failure = true;
				break;
			}
			
			System.out.println("Exit from While? ");
			if(!justHit && isOnTheRect()) {
				System.out.println("yes");
//				System.out.println("\tSono il codice di DAVIDE-- secondo if");

				break;
			}
			System.out.println("no");
			
			if (justHit) {
//				System.out.println("\tSono il codice di DAVIDE-- terzo if");
				System.out.println("\tActualPos= "+Mediator.getMed().getActualPosition()+" hitPos= "+hitPosition);
//				if (!Mediator.getMed().getActualPosition().equals(hitPosition)) {
//					System.out.println("\tSono il codice di DAVIDE-- quarto if");

				if((!isInRange(Mediator.getMed().getActualPosition().getX(), hitPosition.getX(),0.1))||(!isInRange(Mediator.getMed().getActualPosition().getY(), hitPosition.getY(),0.1))){
					justHit = false;
				}
			}
//			System.out.println("STO PER FARE IL MIN DIST");
			if (boundaryDirection.equals("l")) {
				if (Mediator.getMed().getCentralInfo().getMinDistance()<CENTER_DIST) {
//					System.out.println("\tSono il codice di DAVIDE-- L quinto if  "+Mediator.getMed().getCentralInfo().getMinDistance());

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.ROTATE_RIGHT);
				} else if (Mediator.getMed().getLeftInfo().getMinDistance()<BOUNDARY_MIN) {
//					System.out.println("\tSono il codice di DAVIDE-- sesto if");

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_RIGHT);

				} else if (Mediator.getMed().getLeftInfo().getMinDistance()>BOUNDARY_MAX) {
//					System.out.println("\tSono il codice di DAVIDE-- L settimo if  "+Mediator.getMed().getLeftInfo().getMinDistance());

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_LEFT);
				} else {
//					System.out.println("\tSono il codice di DAVIDE-- ottavo if");

					Mediator.getMed().goStraight();
				}	
			} else if (boundaryDirection.equals("r")) {
				if (Mediator.getMed().getCentralInfo().getMinDistance()<CENTER_DIST) {
//					System.out.println("\tSono il codice di DAVIDE-- R quinto if  "+Mediator.getMed().getCentralInfo().getMinDistance());

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.ROTATE_LEFT);
				} else if (Mediator.getMed().getRightInfo().getMinDistance()<BOUNDARY_MIN) {
//					System.out.println("\tSono il codice di DAVIDE-- sesto if");

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_LEFT);

				} else if (Mediator.getMed().getRightInfo().getMinDistance()>BOUNDARY_MAX) {
//					System.out.println("\tSono il codice di DAVIDE-- R settimo if  "+Mediator.getMed().getRightInfo().getMinDistance());

					Mediator.getMed().rotateOf(ROTATION_OFFSET, Movements.TURN_RIGHT);
				} else {
//					System.out.println("\tSono il codice di DAVIDE-- ottavo if");

					Mediator.getMed().goStraight();
				}
			} else {
				System.out.println("Errore");
			}	
		}
	}
	
	private void rotateToGoal() {
		
		mToGoal = (Mediator.getMed().getGoal().getY() - Mediator.getMed().getActualPosition().getY())/(Mediator.getMed().getGoal().getX() - Mediator.getMed().getActualPosition().getX());
		Mediator.getMed().rotateTo(Math.atan(mToGoal), Movements.ROTATE_RIGHT);
	}

	private boolean isOnTheRect() {
		double y = Mediator.getMed().getActualPosition().getY();
		double x = Mediator.getMed().getActualPosition().getX();
		
		double m = (Mediator.getMed().getGoal().getY() - y)/(Mediator.getMed().getGoal().getX() - x);
		double q = y- m*x;
		
		System.out.println("r_m= "+ rect_m+" r_q= "+rect_q);
		System.out.println("m= "+ m+" q= "+ q);
		boolean output=(isInRange(m, rect_m, 0.2)&&isInRange(q, rect_q, 0.2));
		return output;
//		return (y>(rect_m*x+rect_q)-0.5) && (y<(rect_m*x+rect_q)+0.5);
	}
	
	private boolean isGoalReached() {
		
		if (Mediator.getMed().getDistanceFromGoal()<1) return true;

		return false;
	}
	
	private boolean isInRange(double actual, double expected, double error){
		boolean evaluateLower=actual>(expected-error);
		boolean evaluateUpper=actual<(expected+error);
		//System.out.println("actual= "+actual+ "is in range of +- 0.2 from "+expected+ "?"+(evaluateLower&&evaluateUpper));
		return (evaluateLower&&evaluateUpper);
		
	}
}
