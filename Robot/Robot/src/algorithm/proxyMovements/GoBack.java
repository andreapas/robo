package algorithm.proxyMovements;

import java.util.HashMap;

public class GoBack implements IMovement {

	private final HashMap<String, Double> run= new HashMap<>();

	public GoBack() {
		run.put("linVel", 0.0);
        run.put("angVel", 1.0);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}

}
