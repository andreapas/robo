package algorithm;

public class SensorInfo {

	
	private double[] streamSensed;
	
	public void setStreamSensed(double[] streamSensed) {
		this.streamSensed=streamSensed;
	}
	
	public double getMinDistance(){
		double min=100.0;
		for (int i = 1; i < streamSensed.length; i++) {
			double d = streamSensed[i];
			if(d<min)
				min=d;
		}
		return min;
	}
	
	public double getLeftValue() {
		
		return streamSensed[20];
	}
	
	public double getRightValue() {
		
		return streamSensed[1];
	}
	
}
