package algorithm.proxyMovements;

import java.util.HashMap;

public class TurnRight implements IMovement {

private final HashMap<String, Double> run= new HashMap<>();
	
	public TurnRight() {
		run.put("linVel", 1.0);
        run.put("angVel", -1.0);
	}
	
	@Override
	public HashMap<String, Double> move() {
		return run;
	}

}
