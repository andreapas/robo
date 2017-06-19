package algorithm;

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
	private String lastTurn;
	private double mToGoal;
	private double last_m;
	
	
	public void run() {

		if(Math.random()>0.5){
			lastTurn="r";
		}else{
			lastTurn="l";
		}
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
		Position tmp= Mediator.getMed().getActualPosition();
		rect_m = (Mediator.getMed().getGoal().getY() - tmp.getY())/(Mediator.getMed().getGoal().getX() - tmp.getX());
	}

	private void motionToGoal() {
		
		rotateToGoal();
		
		while(true) {
			if(isGoalReached()) {
				goalReached = true;
				break;
			}

			if(Mediator.getMed().getCentralInfo().getMinDistance()<CENTER_DIST) {
				if(lastTurn.equals("r")) {

					boundaryDirection = "l";
					lastTurn="l";
					break;
				} else {
					lastTurn="r";
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
				//rotateToGoal();
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
			Position tmp= Mediator.getMed().getActualPosition();
			if(justHit == false && hitPosition.equals(tmp)) {
//				System.out.println("\tSono il codice di DAVIDE-- primo if");

				failure = true;
				break;
			}
			
			System.out.println("Exit from While? ");
			System.out.println("hitD= "+hitPosition.getDistanceFromGoal()+" actual= "+tmp.getDistanceFromGoal());
			if(!justHit && isOnTheRect() && tmp.getDistanceFromGoal() < hitPosition.getDistanceFromGoal()) {
				System.out.println("yes");
//				System.out.println("\tSono il codice di DAVIDE-- secondo if");

				break;
			}
			System.out.println("no");
			
			if (justHit) {
//				System.out.println("\tSono il codice di DAVIDE-- terzo if");
				System.out.println("\tActualPos= "+tmp+" hitPos= "+hitPosition);
//				if (!Mediator.getMed().getActualPosition().equals(hitPosition)) {
//					System.out.println("\tSono il codice di DAVIDE-- quarto if");

				if((!isInRange(tmp.getX(), hitPosition.getX(),0.1))||(!isInRange(tmp.getY(), hitPosition.getY(),0.1))){
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
		Position tmp= Mediator.getMed().getActualPosition();
		mToGoal = (Mediator.getMed().getGoal().getY() - tmp.getY())/(Mediator.getMed().getGoal().getX() - tmp.getX());
		if(tmp.getRadiants()>Math.atan(mToGoal)){
			Mediator.getMed().rotateTo(Math.atan(mToGoal), Movements.ROTATE_RIGHT);
		}else{
			Mediator.getMed().rotateTo(Math.atan(mToGoal), Movements.ROTATE_LEFT);
		}
	}

	private boolean isOnTheRect() {
		Position tmp = Mediator.getMed().getActualPosition();
		boolean output;
		double y = tmp.getY();
		double x = tmp.getX();
		
		double m = Math.abs((Mediator.getMed().getGoal().getY() - y)/(Mediator.getMed().getGoal().getX() - x));
		
		System.out.println("r_m= "+ rect_m);
		System.out.println("last m= "+last_m);
		System.out.println("actual m= "+ m);
		
		System.out.println("first condition (m>rect_m && rect_m>last_m): "+ (m>rect_m && rect_m>last_m));
		System.out.println("second condition (m<rect_m && rect_m<last_m): "+ (m<rect_m && rect_m<last_m));
		System.out.println("third condition isInrange: "+ isInRange(m, rect_m, 0.2));

		output=((m>rect_m && rect_m>last_m) || (m<rect_m && rect_m<last_m))||isInRange(m, rect_m, 0.2);
		last_m = m;
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
