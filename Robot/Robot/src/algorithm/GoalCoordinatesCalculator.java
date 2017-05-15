package algorithm;

import java.util.function.Function;

public class GoalCoordinatesCalculator {

	
	private static GoalCoordinatesCalculator gCoord= new GoalCoordinatesCalculator();
	
	public GoalCoordinatesCalculator() {
	}
	
	public static GoalCoordinatesCalculator getgCoord() {
		return gCoord;
	}
	
	private static Function<Coordinates,Double> squareR= a-> Math.pow(a.getDistanceFromGoal(), 2);
	private static Function<Coordinates,Double> squareX= a-> Math.pow(a.getX(), 2);
	private static Function<Coordinates,Double> squareY= a-> Math.pow(a.getY(), 2);
	
	public static Coordinates getGoalCoordinates(Coordinates min, Coordinates max){
		
		double m=0.0;
		double a=0.0;
		double b=0.0;
		double alpha=0.0;
		
		m=(min.getY()-max.getY())/(min.getX()-max.getX());
		alpha=Math.atan(m);
		
		a=max.getDistanceFromGoal()*(Math.sin(alpha));
		b=max.getDistanceFromGoal()*(Math.cos(alpha));
		
		b=max.getX()+b;
		a=max.getY()+a;

		
		return new Coordinates(b,a, alpha, 0.0);
	}
	
	/* 
	 * [10 -10] risultato 
	 *  a1 Coordinates [x=6.9998579025268555, y=6.999711036682129, m=2.373617036255382E-8, distanceFromGoal=17.26361656188965]
 		b1 Coordinates [x=4.699386119842529, y=7.002416610717773, m=1.371509861201048E-4, distanceFromGoal=17.798505783081055]
 		c1 Coordinates [x=3.8454885482788086, y=6.7290472984313965, m=0.006659831386059523, distanceFromGoal=17.825885772705078]
	 */
	public static Coordinates triangulateThis(Coordinates a, Coordinates b, Coordinates c){
		//System.out.println("\n a "+a+"\n b "+b+"\n c "+c);
		double x= ((squareR.apply(a)-squareR.apply(b)-squareX.apply(a)+squareX.apply(b)-squareY.apply(a)+squareY.apply(b))*
				(-2*b.getX()+2*c.getX())-(squareR.apply(b)-squareR.apply(c)-squareX.apply(b)+squareX.apply(c)-squareY.apply(b)+squareY.apply(c))*(-2*a.getX()+2*b.getX()))
				/
				((-2*a.getY()+2*b.getY())*(-2*b.getX()+2*c.getX())-(-2*b.getY()+2*c.getY())*(-2*a.getX()+2*b.getX()));
		
		double y=((-2*a.getX()+2*b.getX())*(-2*b.getY()+2*c.getY())-(-2*b.getX()+2*c.getX())*(-2*a.getY()+2*b.getY()))
				/
				((squareR.apply(a)-squareR.apply(b)-squareX.apply(a)+squareX.apply(b)-squareY.apply(a)+squareY.apply(b))*
				(-2*b.getY()+2*c.getY())-(squareR.apply(b)-squareR.apply(c)-squareX.apply(b)+squareX.apply(c)-squareY.apply(b)+squareY.apply(c))*(-2*a.getY()+2*b.getY()));
		return new Coordinates(x, y, 0.0, 0.0);

	}
}
