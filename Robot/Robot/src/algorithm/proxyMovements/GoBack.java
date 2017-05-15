package algorithm.proxyMovements;

import java.util.HashMap;

public class GoBack implements IMovement {

	private final HashMap<String, Double> run= new HashMap<>();

	public GoBack() {
		run.put("linVel", -1.5);
        run.put("angVel", 0.0);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}

}
