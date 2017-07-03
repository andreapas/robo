package algorithm;

import java.util.function.Function;

public class GoalCoordinatesCalculator {

	private static GoalCoordinatesCalculator gCoord = new GoalCoordinatesCalculator();

	public GoalCoordinatesCalculator() {
	}

	public static GoalCoordinatesCalculator getgCoord() {
		return gCoord;
	}

	private static Function<Position, Double> squareR = a -> Math.pow(a.getDistanceFromGoal(), 2);
	private static Function<Position, Double> squareX = a -> Math.pow(a.getX(), 2);
	private static Function<Position, Double> squareY = a -> Math.pow(a.getY(), 2);

	private static double k;
	private static double q;

	
	public static Position findGoal(Position P1, Position P2, Position P3){
		Position offsetVector= new Position(-P1.getX(), -P1.getY(), 0.0, 0.0);
		
		Position P1off=new Position(P1.getX()+offsetVector.getX(), P1.getY()+offsetVector.getY(), 0.0, 0.0);
		Position P2off=new Position(P2.getX()+offsetVector.getX(), P2.getY()+offsetVector.getY(), 0.0, 0.0);
		Position P3off=new Position(P3.getX()+offsetVector.getX(), P3.getY()+offsetVector.getY(), 0.0, 0.0);
		
		double xoffset=(squareR.apply(P1)-squareR.apply(P2)+squareX.apply(P2off))/(2*P2off.getX());
		double yoffset=((squareR.apply(P1)-squareR.apply(P3)+squareX.apply(P3off)+squareY.apply(P3off))/(2*P3off.getY()))-((P3off.getX()/P3off.getY())*xoffset);

		
		return new Position(xoffset-offsetVector.getX(), yoffset-offsetVector.getY(), 0, 0);
	}
	
	public static Position getGoalPosition(Position min, Position max) {

		k = (min.getY() - max.getY()) / (min.getX() / max.getX());
		q = (k * max.getX()) + max.getY();

		double xG1 = min.getX() - (min.getDistanceFromGoal() / (1 - k));
		double xG2 = min.getX() + (min.getDistanceFromGoal() / (1 - k));
		
		System.out.println(xG1+" "+xG2);
		
		double dMax1 = Math.abs(max.getX() - xG1) * Math.sqrt(1 + Math.pow(k, 2));
		double dMax2 = Math.abs(max.getX() - xG2) * Math.sqrt(1 + Math.pow(k, 2));

		Function<Double, Double> calcY = xG -> (k * xG) - q;
		System.out.println(dMax1 + " " + max.getDistanceFromGoal()+" 1");
		System.out.println(dMax2 + " " + max.getDistanceFromGoal()+" 2");
		
		if (dMax1 == max.getDistanceFromGoal()) {
			System.out.println(dMax1 + " " + max.getDistanceFromGoal()+" 1");
			return new Position(xG1, calcY.apply(xG1), 0.0, 0.0);
		} else {
			System.out.println(dMax2 + " " + max.getDistanceFromGoal()+" 2");
			return new Position(xG2, calcY.apply(xG2), 0.0, 0.0);
		}
	}
	public static Position trilaterateGoal(Position firstPointGot, Position secondPointGot,
			Position thirdPointGot) {
		Position errorX;
		Position pointA;
		Position errorY;
		Position pointB;

		double d = Math.sqrt(Math.pow(secondPointGot.getX() - firstPointGot.getX(), 2)
				+ Math.pow(secondPointGot.getY() - firstPointGot.getY(), 2));
		errorX = new Position((secondPointGot.getX() - firstPointGot.getX()) / d,
				(secondPointGot.getY() - firstPointGot.getY()) / d, 0.0, 0.0);
		double fex = Math.sqrt(Math.pow(errorX.getX(), 2) + Math.pow(errorX.getY(), 2));

		pointA = new Position(errorX.getX() * (thirdPointGot.getX() - firstPointGot.getX()),
				errorX.getY() * (thirdPointGot.getY() - firstPointGot.getY()), 0.0, 0.0);
		double fI = Math.sqrt(squareX.apply(pointA) + squareY.apply(pointA));

		double absoluteErrorY = Math
				.sqrt(Math.pow(thirdPointGot.getX() - firstPointGot.getX() - pointA.getX() * errorX.getX(), 2)
						+ Math.pow(thirdPointGot.getY() - firstPointGot.getY() - pointA.getY() * errorX.getY(), 2));

		errorY = new Position(
				(thirdPointGot.getX() - firstPointGot.getX() - pointA.getX() * errorX.getX()) / absoluteErrorY,
				(thirdPointGot.getY() - firstPointGot.getY() - pointA.getY() * errorX.getY()) / absoluteErrorY, 0.0,
				0.0);

		double fey = Math.sqrt(Math.pow(errorY.getX(), 2) + Math.pow(errorY.getY(), 2));
		pointB = new Position(errorY.getX() * (thirdPointGot.getX() - firstPointGot.getX()),
				errorY.getY() * (thirdPointGot.getY() - firstPointGot.getY()), 0.0, 0.0);

		double fJ = Math.sqrt(squareX.apply(pointB) + squareY.apply(pointB));
		double x = (squareR.apply(firstPointGot) - squareR.apply(secondPointGot) + Math.pow(d, 2)) / (2 * d);
		double y = ((squareR.apply(firstPointGot) - squareR.apply(thirdPointGot) + Math.pow(fI, 2) + Math.pow(fJ, 2))
				/ (2 * fJ)) - ((fI * x) / (fJ));
		Position goal = new Position(x * fex + firstPointGot.getX(), y * fey + firstPointGot.getY(), 0.0, 0.0);

		return goal;

	}
}
