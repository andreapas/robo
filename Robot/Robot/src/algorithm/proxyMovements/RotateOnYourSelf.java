package algorithm.proxyMovements;

import java.util.HashMap;

public class RotateOnYourSelf implements IMovement{

private final HashMap<String, Double> run= new HashMap<>();
	//lin 0.1 ang 0.8
	public RotateOnYourSelf() {
		run.put("linVel", 0.5);
        run.put("angVel", 0.8);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}
}
