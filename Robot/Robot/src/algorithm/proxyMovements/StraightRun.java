package algorithm.proxyMovements;

import java.util.HashMap;

public class StraightRun implements IMovement{

	private final HashMap<String, Double> run= new HashMap<>();
	
	public StraightRun() {
		run.put("linVel", 1.8);
        run.put("angVel", 0.0);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}
}
