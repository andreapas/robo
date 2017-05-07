package algorithm;

public class Coordinates {

	private double x;
	private double y;
	private double m;
	private double distanceFromGoal;
	
	public Coordinates(double x, double y, double m, double distanceFromGoal) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.distanceFromGoal=distanceFromGoal;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getM() {
		return m;
	}
	
	public void setCoordinates(double x,double y,double m, double distanceFromGoal) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.distanceFromGoal=distanceFromGoal;
	}
	
	public double getDistanceFromGoal() {
		return distanceFromGoal;
	}
	
	public void setDistanceFromGoal(double distanceFromGoal) {
		this.distanceFromGoal = distanceFromGoal;
	}


	public boolean isValid(){
		if(x==0.0)
			return false;
		if(y==0.0)
			return false;
		if(m==0.0)
			return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (Double.doubleToLongBits(distanceFromGoal) != Double.doubleToLongBits(other.distanceFromGoal))
			return false;
		if (Double.doubleToLongBits(m) != Double.doubleToLongBits(other.m))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + ", m=" + m + ", distanceFromGoal=" + distanceFromGoal + "]";
	}
	
	
}
