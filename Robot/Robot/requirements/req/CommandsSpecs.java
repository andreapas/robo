package req;

import org.junit.Assert;
import org.junit.Test;

import tools.Commands;

public class CommandsSpecs {

	@Test
	public void setSpeedCommand() {
		Commands commands= new Commands();
		String output=commands.goStraight(2);
		String expectedOutput="cmd1 r2d2.motion set_speed [2,0]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}

	@Test
	public void setAnotherSpeedCommand() {
		Commands commands= new Commands();
		String output=commands.goStraight(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [4,0]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}
	
	@Test
	public void sendTwoCommands() {
		Commands commands= new Commands();
		String output=commands.goStraight(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [4,0]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
		output=commands.goStraight(2);
		expectedOutput="cmd2 r2d2.motion set_speed [2,0]";
		Assert.assertEquals("Right command sent with right id detected", expectedOutput, output);
	}
	
	@Test
	public void rotateRightCommand() {
		Commands commands= new Commands();
		String output=commands.goLeft(2);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,2]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}
	
	@Test
	public void rotateAnotherRightCommand() {
		Commands commands= new Commands();
		String output=commands.goLeft(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,4]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}
	
	@Test
	public void rotateTwoTimesRightCommand() {
		Commands commands= new Commands();
		String output=commands.goLeft(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,4]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
		output=commands.goLeft(2);
		expectedOutput="cmd2 r2d2.motion set_speed [0,2]";
		Assert.assertEquals("Right command sent with right id detected", expectedOutput, output);
	}

	
	
	
	
	
	
	@Test
	public void rotateLeftCommand() {
		Commands commands= new Commands();
		String output=commands.goRight(2);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,-2]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}
	
	@Test
	public void rotateAnotherLeftCommand() {
		Commands commands= new Commands();
		String output=commands.goRight(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,-4]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
	}
	
	@Test
	public void rotateTwoTimesLeftCommand() {
		Commands commands= new Commands();
		String output=commands.goRight(4);
		String expectedOutput="cmd1 r2d2.motion set_speed [0,-4]";
		Assert.assertEquals("Right command sent", expectedOutput, output);
		output=commands.goRight(2);
		expectedOutput="cmd2 r2d2.motion set_speed [0,-2]";
		Assert.assertEquals("Right command sent with right id detected", expectedOutput, output);
	}
}
