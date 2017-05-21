package algorithm;

import java.util.function.Function;

public class GoalCoordinatesCalculator {

	private static GoalCoordinatesCalculator gCoord = new GoalCoordinatesCalculator();

	public GoalCoordinatesCalculator() {
	}

	public static GoalCoordinatesCalculator getgCoord() {
		return gCoord;
	}

	private static Function<Coordinates, Double> squareR = a -> Math.pow(a.getDistanceFromGoal(), 2);
	private static Function<Coordinates, Double> squareX = a -> Math.pow(a.getX(), 2);
	private static Function<Coordinates, Double> squareY = a -> Math.pow(a.getY(), 2);

	private static double k;
	private static double q;

	
	
	
	
	public static Coordinates lastTry(Coordinates P1, Coordinates P2, Coordinates P3){
		Coordinates offsetVector= new Coordinates(-P1.getX(), -P1.getY(), 0.0, 0.0);
//		System.out.println("V= " +offsetVector);;
		
		Coordinates P1off=new Coordinates(P1.getX()+offsetVector.getX(), P1.getY()+offsetVector.getY(), 0.0, 0.0);
		Coordinates P2off=new Coordinates(P2.getX()+offsetVector.getX(), P2.getY()+offsetVector.getY(), 0.0, 0.0);
		Coordinates P3off=new Coordinates(P3.getX()+offsetVector.getX(), P3.getY()+offsetVector.getY(), 0.0, 0.0);
		
//		System.out.println("P1\'= "+P1off);
//		System.out.println("P2\'= "+P2off);
//		System.out.println("P3\'= "+P3off);
		
		double xoffset=(squareR.apply(P1)-squareR.apply(P2)+squareX.apply(P2off))/(2*P2off.getX());
//		System.out.println("x= "+xoffset);
		double yoffset=((squareR.apply(P1)-squareR.apply(P3)+squareX.apply(P3off)+squareY.apply(P3off))/(2*P3off.getY()))-((P3off.getX()/P3off.getY())*xoffset);
//		System.out.println("y= "+yoffset);

		
		return new Coordinates(xoffset-offsetVector.getX(), yoffset-offsetVector.getY(), 0, 0);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Coordinates getGoalCoordinates(Coordinates min, Coordinates max) {

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
			return new Coordinates(xG1, calcY.apply(xG1), 0.0, 0.0);
		} else {
			System.out.println(dMax2 + " " + max.getDistanceFromGoal()+" 2");
			return new Coordinates(xG2, calcY.apply(xG2), 0.0, 0.0);
		}
		// double m=0.0;
		// double a=0.0;
		// double b=0.0;
		// double alpha=0.0;
		//
		// m=(min.getY()-max.getY())/(min.getX()-max.getX());
		// alpha=Math.atan(m);
		//
		// a=max.getDistanceFromGoal()*(Math.sin(alpha));
		// b=max.getDistanceFromGoal()*(Math.cos(alpha));
		//
		// b=max.getX()+b;
		// a=max.getY()+a;

		// return new Coordinates(b,a, alpha, 0.0);
	}

	/*
	 * [10 -10] risultato a1 Coordinates [x=6.9998579025268555,
	 * y=6.999711036682129, m=2.373617036255382E-8,
	 * distanceFromGoal=17.26361656188965] b1 Coordinates [x=4.699386119842529,
	 * y=7.002416610717773, m=1.371509861201048E-4,
	 * distanceFromGoal=17.798505783081055] c1 Coordinates
	 * [x=3.8454885482788086, y=6.7290472984313965, m=0.006659831386059523,
	 * distanceFromGoal=17.825885772705078]
	 */
	public static Coordinates trilaterateGoal(Coordinates firstPointGot, Coordinates secondPointGot,
			Coordinates thirdPointGot) {
		Coordinates errorX;
		Coordinates pointA;
		Coordinates errorY;
		Coordinates pointB;

		double d = Math.sqrt(Math.pow(secondPointGot.getX() - firstPointGot.getX(), 2)
				+ Math.pow(secondPointGot.getY() - firstPointGot.getY(), 2));
		errorX = new Coordinates((secondPointGot.getX() - firstPointGot.getX()) / d,
				(secondPointGot.getY() - firstPointGot.getY()) / d, 0.0, 0.0);
		double fex = Math.sqrt(Math.pow(errorX.getX(), 2) + Math.pow(errorX.getY(), 2));

		pointA = new Coordinates(errorX.getX() * (thirdPointGot.getX() - firstPointGot.getX()),
				errorX.getY() * (thirdPointGot.getY() - firstPointGot.getY()), 0.0, 0.0);
		double fI = Math.sqrt(squareX.apply(pointA) + squareY.apply(pointA));

		double absoluteErrorY = Math
				.sqrt(Math.pow(thirdPointGot.getX() - firstPointGot.getX() - pointA.getX() * errorX.getX(), 2)
						+ Math.pow(thirdPointGot.getY() - firstPointGot.getY() - pointA.getY() * errorX.getY(), 2));

		errorY = new Coordinates(
				(thirdPointGot.getX() - firstPointGot.getX() - pointA.getX() * errorX.getX()) / absoluteErrorY,
				(thirdPointGot.getY() - firstPointGot.getY() - pointA.getY() * errorX.getY()) / absoluteErrorY, 0.0,
				0.0);

		double fey = Math.sqrt(Math.pow(errorY.getX(), 2) + Math.pow(errorY.getY(), 2));
		pointB = new Coordinates(errorY.getX() * (thirdPointGot.getX() - firstPointGot.getX()),
				errorY.getY() * (thirdPointGot.getY() - firstPointGot.getY()), 0.0, 0.0);

		double fJ = Math.sqrt(squareX.apply(pointB) + squareY.apply(pointB));
		double x = (squareR.apply(firstPointGot) - squareR.apply(secondPointGot) + Math.pow(d, 2)) / (2 * d);
		double y = ((squareR.apply(firstPointGot) - squareR.apply(thirdPointGot) + Math.pow(fI, 2) + Math.pow(fJ, 2))
				/ (2 * fJ)) - ((fI * x) / (fJ));
		Coordinates goal = new Coordinates(x * fex + firstPointGot.getX(), y * fey + firstPointGot.getY(), 0.0, 0.0);

		return goal;

	}
}
