package tools;

public class Commands {

	private String commandIDName="cmd";
	private int idValue=0;
	private String baseGoCommand=" r2d2.motion set_speed ";
	
	public String goStraight(int i) {
		return composeAnswer()+"["+i+",0]";
	}
	public String goRight(int i) {
		return composeAnswer()+"[0,"+i+"]";
	}
	private String composeAnswer(){
		idValue++;
		return commandIDName+idValue+baseGoCommand;
	}
	

}
