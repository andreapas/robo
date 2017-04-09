package tools;

public class Commands {

	private String baseGoCommand="cmd1 r2d2.motion set_speed ";
	
	public String goStraight(int i) {
		// TODO Auto-generated method stub
		return baseGoCommand+"["+i+",0]";
	}

}
