package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import algorithm.Coordinates;
import algorithm.GoalCoordinatesCalculator;

public class TestGoalCoordCalc {

	
	
	@Test
	public void testCalc(){
		double ag= Math.sqrt(Math.pow(10, 2)+Math.pow(8, 2));
		double bg= Math.sqrt(Math.pow(7, 2)+Math.pow(8, 2));
		double cg= Math.sqrt(Math.pow(7, 2)+Math.pow(5, 2));
		
		Coordinates a = new Coordinates(2, 2, Math.asin(8/ag), ag);
		Coordinates b = new Coordinates(5, 2, Math.asin(8/bg), bg);
		Coordinates c = new Coordinates(5, 5, Math.asin(5/cg), cg);
		
		Coordinates goal=GoalCoordinatesCalculator.triangulateThis(a, b, c);
		assertEquals(10, goal.getY(), 0.1);
		assertEquals(12, goal.getX(), 0.1);
		
	}
	
}
