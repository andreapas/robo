package algorithm.proxyMovements;

public class Position {

	private double x;
	private double y;
	private double radiants;

	public Position() {
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

	public void setPosition(double[] position) {
		this.x = position[1];
		this.y = position[2];
		this.radiants = position[6];
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
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
		if (Double.doubleToLongBits(radiants) != Double.doubleToLongBits(other.radiants))
			return false;
		if (Double.doubleToLongBits(x) < Math.abs(Double.doubleToLongBits(other.x))-0.5||Double.doubleToLongBits(x) > Math.abs(Double.doubleToLongBits(other.x))+0.5)
			return false;
		if (Double.doubleToLongBits(y) < Math.abs(Double.doubleToLongBits(other.y))-0.5||Double.doubleToLongBits(y) > Math.abs(Double.doubleToLongBits(other.y))+0.5)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", radiants=" + radiants + "]";
	}
	
	
}
