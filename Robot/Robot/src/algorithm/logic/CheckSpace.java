package algorithm.logic;

import algorithm.GoalCoordinatesCalculator;
import algorithm.Position;
import algorithm.SensorInfo;
import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class CheckSpace {

	private static final double AMOUNT_TO_MOVE = 0.8;
	private Position startPosition = new Position();
	private Position positionX1 = new Position();
	private Position positionX2 = new Position();
	private Position otherPosition = new Position();
	private static CheckSpace checker = new CheckSpace();

	public CheckSpace() {
		startPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
	}

	public static CheckSpace getChecker() {
		return checker;
	}

	public Position findGoal() {
//		System.out.println("Find Goal... forse");
		if (startPosition.getRadiants() > 0.1 || startPosition.getRadiants() < -0.1) {
			if (startPosition.getRadiants() > 0) {
//				System.out.println("Rotating right... forse");

				Mediator.getMed().rotateTo(0, Movements.ROTATE_RIGHT);
//				System.out.println("Agg fornut... forse");

			} else {
//				System.out.println("Rotating left... forse");

				Mediator.getMed().rotateTo(0, Movements.ROTATE_LEFT);
//				System.out.println("Agg fornut... forse");

			}
		}

		if (checkActual(Mediator.getMed().getCentralInfo())) {
//			System.out.println("primo if... forse");

			identifySecondXAndOther("forward");
		} else if (checkActual(Mediator.getMed().getBackInfo())) {
//			System.out.println("secondo if... forse");

			identifySecondXAndOther("backward");
		} else {
//			System.out.println("terzo if... forse");

			otherPosition.setPosition(startPosition);
			Mediator.getMed().rotateOf(Math.PI / 2, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
//				System.out.println("quarto if... forse");

				identifyXsPoints("forward");
			} else if (checkActual(Mediator.getMed().getBackInfo())) {
				System.out.println("quinto if... forse");

				identifyXsPoints("backward");
			}
		}
//		System.out.println("fanculo ho finito. Se non funziono sono cazzi di Davide!");
		
		return GoalCoordinatesCalculator.findGoal(positionX1, positionX2, otherPosition);
	}

	private void identifySecondXAndOther(String direction) {
		positionX1.setPosition(startPosition);
		if (direction.equals("forward"))
			Mediator.getMed().goStraight(0.8);
		else
			Mediator.getMed().goBack(0.8);
		positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		Mediator.getMed().rotateOf(Math.PI / 2, Movements.ROTATE_LEFT);
		if (checkActual(Mediator.getMed().getCentralInfo())) {
			Mediator.getMed().goStraight(0.8);
			otherPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		} else if (checkActual(Mediator.getMed().getBackInfo())) {
			Mediator.getMed().goBack(0.8);
			otherPosition.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
		} else {
			Mediator.getMed().rotateOf(Math.PI / 2, Movements.ROTATE_RIGHT);
			checkBack("forward");
			Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
			Mediator.getMed().rotateOf(Math.PI / 2, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(Mediator.getMed().getCentralInfo().getMinDistance() - 0.2);
				otherPosition.setPosition(Mediator.getMed().getActualPosition(),
						Mediator.getMed().getDistanceFromGoal());
			} else {
				Mediator.getMed().goBack(Mediator.getMed().getCentralInfo().getMinDistance() - 0.2);
				otherPosition.setPosition(Mediator.getMed().getActualPosition(),
						Mediator.getMed().getDistanceFromGoal());
			}
		}
	}

	private void checkBack(String dir) {
		SensorInfo sensor;
		if (dir.equals("forward")) {
			while ((!checkActual(Mediator.getMed().getLeftInfo()) || !checkActual(Mediator.getMed().getLeftInfo()))
					&& checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
			}
			if (!checkActual(Mediator.getMed().getCentralInfo())) {
				checkBack("backward");
			}
		} else {
			while ((!checkActual(Mediator.getMed().getLeftInfo()) || !checkActual(Mediator.getMed().getLeftInfo()))
					&& checkActual(Mediator.getMed().getBackInfo())) {
				Mediator.getMed().goBack(AMOUNT_TO_MOVE);
			}
			if (!checkActual(Mediator.getMed().getBackInfo())) {
				System.out.println("ERRORE: NON SO PIU' DOVE ANDARE");
			}
		}

	}

	private void identifyXsPoints(String direction) {
		if (direction.equals("forward")) {
			while (checkActual(Mediator.getMed().getCentralInfo())) {
				if (checkActual(Mediator.getMed().getLeftInfo()) || checkActual(Mediator.getMed().getRightInfo())) {
					Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				}
			}
			Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			} else {
				Mediator.getMed().goBack(AMOUNT_TO_MOVE);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goBack(AMOUNT_TO_MOVE);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			}
		} else {
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_RIGHT);
			while (checkActual(Mediator.getMed().getCentralInfo())) {
				if (checkActual(Mediator.getMed().getLeftInfo()) || checkActual(Mediator.getMed().getRightInfo())) {
					Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				}
			}
			Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goStraight(AMOUNT_TO_MOVE);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			} else {
				Mediator.getMed().goBack(AMOUNT_TO_MOVE);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goBack(AMOUNT_TO_MOVE);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			}
		}
	}

	private boolean checkActual(SensorInfo sensor) {
		return sensor.getMinDistance() > 1.2;
	}

	public Position getStartPosition() {
		return startPosition;
	}
}
