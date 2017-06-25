package algorithm.proxyMovements;

import java.util.HashMap;

public class RotateLeft implements IMovement{

private final HashMap<String, Double> run= new HashMap<>();
	//lin 0.1 ang 0.8
	public RotateLeft() {
		run.put("linVel", 0.0);
        run.put("angVel", 1.8);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}
}
