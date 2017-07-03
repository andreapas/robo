package algorithm;

public class Position {

	private double x;
	private double y;
	private double radiants;
	private double distanceFromGoal;

	public Position(){
		
	}
	
	
	public Position(double x, double y, double m, double distanceFromGoal) {
		this.x = x;
		this.y = y;
		this.radiants = m;
		this.distanceFromGoal=distanceFromGoal;

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}


	public double getRadiants() {
		return radiants;
	}

	public void setPosition(Position otherPosition) {
		this.x = otherPosition.getX();
		this.y = otherPosition.getY();
		this.radiants = otherPosition.getRadiants();
		this.distanceFromGoal=otherPosition.getDistanceFromGoal();
	}
	
	public void setPosition(Position otherPosition, double distanceFromGoal) {
		this.x = otherPosition.getX();
		this.y = otherPosition.getY();
		this.radiants = otherPosition.getRadiants();
		this.distanceFromGoal=distanceFromGoal;
	}
	
	public void setPosition(double[] position) {
		this.x = position[1];
		this.y = position[2];
		this.radiants = position[6];
	}
	public double getDistanceFromGoal() {
		return distanceFromGoal;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distanceFromGoal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radiants);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(distanceFromGoal) != Double.doubleToLongBits(other.distanceFromGoal))
			return false;
		if (Double.doubleToLongBits(radiants) != Double.doubleToLongBits(other.radiants))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", radiants=" + radiants + ", distanceFromGoal=" + distanceFromGoal
				+ "]";
	}
	
	
	

	
	
}
