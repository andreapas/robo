package algorithm.proxyMovements;

import java.util.HashMap;

public class StopMovement implements IMovement{

	private final HashMap<String, Double> run= new HashMap<>();
	
	public StopMovement() {
		run.put("linVel", 0.);
        run.put("angVel", 0.);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}
}

