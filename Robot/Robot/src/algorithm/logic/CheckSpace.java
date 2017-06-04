package algorithm.logic;

import algorithm.GoalCoordinatesCalculator;
import algorithm.Position;
import algorithm.SensorInfo;
import algorithm.mediator.Mediator;
import algorithm.proxyMovements.Movements;

public class CheckSpace {

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
		if (startPosition.getRadiants() > 0.1 || startPosition.getRadiants() < -0.1) {
			if (startPosition.getRadiants() > 0)
				Mediator.getMed().rotateTo(0, Movements.ROTATE_RIGHT);
			else
				Mediator.getMed().rotateTo(0, Movements.ROTATE_LEFT);
		}

		if (checkActual(Mediator.getMed().getCentralInfo())) {
			identifySecondXAndOther("forward");
		} else if (checkActual(Mediator.getMed().getBackInfo())) {
			identifySecondXAndOther("backward");
		} else {
			otherPosition.setPosition(startPosition);
			Mediator.getMed().rotateOf(Math.PI / 2, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				identifyXsPoints("forward");
			} else if (checkActual(Mediator.getMed().getBackInfo())) {
				identifyXsPoints("backward");
			}
		}

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
			Mediator.getMed().goStraight(0.4);
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
				Mediator.getMed().goStraight(0.4);
			}
			if (!checkActual(Mediator.getMed().getCentralInfo())) {
				checkBack("backward");
			}
		} else {
			while ((!checkActual(Mediator.getMed().getLeftInfo()) || !checkActual(Mediator.getMed().getLeftInfo()))
					&& checkActual(Mediator.getMed().getBackInfo())) {
				Mediator.getMed().goBack(0.4);
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
					Mediator.getMed().goStraight(0.4);
				}
			}
			Mediator.getMed().goStraight(0.4);
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(0.4);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goStraight(0.4);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			} else {
				Mediator.getMed().goBack(0.4);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goBack(0.4);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			}
		} else {
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_RIGHT);
			while (checkActual(Mediator.getMed().getCentralInfo())) {
				if (checkActual(Mediator.getMed().getLeftInfo()) || checkActual(Mediator.getMed().getRightInfo())) {
					Mediator.getMed().goStraight(0.4);
				}
			}
			Mediator.getMed().goStraight(0.4);
			Mediator.getMed().rotateOf(Math.PI, Movements.ROTATE_LEFT);
			if (checkActual(Mediator.getMed().getCentralInfo())) {
				Mediator.getMed().goStraight(0.4);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goStraight(0.4);
				positionX2.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
			} else {
				Mediator.getMed().goBack(0.4);
				positionX1.setPosition(Mediator.getMed().getActualPosition(), Mediator.getMed().getDistanceFromGoal());
				Mediator.getMed().goBack(0.4);
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
