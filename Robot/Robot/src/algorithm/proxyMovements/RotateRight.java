package algorithm.proxyMovements;

import java.util.HashMap;

public class RotateRight implements IMovement {

	private final HashMap<String, Double> run= new HashMap<>();

	public RotateRight() {
		run.put("linVel", 0.0);
        run.put("angVel", -1.8);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}

}
