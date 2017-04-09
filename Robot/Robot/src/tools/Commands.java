package tools;

public class Commands {

	private String commandIDName="cmd";
	private int idValue=0;
	private String baseGoCommand=" r2d2.motion set_speed ";
	
	public String goStraight(int i) {
		idValue++;
		return commandIDName+idValue+baseGoCommand+"["+i+",0]";
	}

}
