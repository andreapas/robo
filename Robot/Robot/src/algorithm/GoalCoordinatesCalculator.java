package algorithm;

public class GoalCoordinatesCalculator {

	
	private static GoalCoordinatesCalculator gCoord= new GoalCoordinatesCalculator();
	
	public GoalCoordinatesCalculator() {
	}
	
	public static GoalCoordinatesCalculator getgCoord() {
		return gCoord;
	}
	
	public static Coordinates getGoalCoordinates(Coordinates min, Coordinates max){
		
		double m=0.0;
		double a=0.0;
		double b=0.0;
		double alpha=0.0;
		
		m=(max.getY()-min.getY())/(max.getX()-min.getX());
		alpha=Math.atan(m);
		
		a=min.getDistanceFromGoal()*Math.sin(alpha);
		b=min.getDistanceFromGoal()*Math.cos(alpha);
		
		a=min.getX()+a;
		b=min.getY()+b;
		
		return new Coordinates(a,b, alpha, 0.0);
	}
	
}
