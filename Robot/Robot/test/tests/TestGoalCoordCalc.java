package tests;

import static org.junit.Assert.assertEquals;

import org.assertj.core.data.Offset;
import org.junit.Test;
import static org.assertj.core.api.BDDAssertions.then;
import algorithm.Position;
import algorithm.GoalCoordinatesCalculator;

public class TestGoalCoordCalc {

	/*@Test
	public void testCalc() {
		double ag = Math.sqrt(Math.pow(10, 2) + Math.pow(8, 2));
		double bg = Math.sqrt(Math.pow(7, 2) + Math.pow(8, 2));
		double cg = Math.sqrt(Math.pow(7, 2) + Math.pow(5, 2));

		Position a = new Position(2, 2, Math.asin(8 / ag), ag);
		Position b = new Position(5, 2, Math.asin(8 / bg), bg);
		Position c = new Position(5, 5, Math.asin(5 / cg), cg);

		Position goal = GoalCoordinatesCalculator.trilaterateGoal(a, b, c);
		// System.out.println(goal.toString());
		assertEquals(10, goal.getY(), 0.1);
		assertEquals(12, goal.getX(), 0.1);

	}*/

	@Test
	public void testLast() {
		Position a = new Position(-1, 1, 0, 2);
		Position b = new Position(1, 1, 0, 2);
		Position c = new Position(-1, -1, 0, 2);

		Position goal1 = GoalCoordinatesCalculator.findGoal(a, b, c);
		System.out.println(goal1);
		assertEquals(0, goal1.getX(), 0.1);
		assertEquals(0, goal1.getY(), 0.1);
	}

	@Test
	public void testLast2() {
		double ag = Math.sqrt(Math.pow(10, 2) + Math.pow(8, 2));
		double bg = Math.sqrt(Math.pow(7, 2) + Math.pow(8, 2));
		double cg = Math.sqrt(Math.pow(7, 2) + Math.pow(5, 2));

		Position a = new Position(2, 2, Math.asin(8 / ag), ag);
		Position b = new Position(5, 2, Math.asin(8 / bg), bg);
		Position c = new Position(5, 5, Math.asin(5 / cg), cg);

		Position goal = GoalCoordinatesCalculator.findGoal(a, b, c);
		System.out.println(goal.toString());
		assertEquals(10, goal.getY(), 0.1);
		assertEquals(12, goal.getX(), 0.1);
	}

	@Test
	public void testLast3() {

		Position a = new Position(6.9998579025268555, 6.999711036682129, 0.0, 17.26361656188965);
		Position b = new Position(4.699386119842529, 7.002416610717773, 0.0, 17.798505783081055);
		Position c = new Position(3.8454885482788086, 6.7290472984313965, 0.0, 17.825885772705078);

		Position goal = GoalCoordinatesCalculator.findGoal(a, b, c);
		System.out.println("real one= " + goal.toString());
		then(goal.getX()).isBetween(9.0, 11.0);
		then(goal.getY()).isBetween(-11.0, -9.0);

	}

	/*@Test
	public void testLast4StessaX() {

		Position a = new Position(-13.464030265808105, -0.300258070230484, 0.0, 25.39069175720215);
		Position b = new Position(-13.464030265808105, -7.984826564788818, 0.0, 23.555505752563477);
		Position c = new Position(14.387412071228027, -17.90849449340, 0.0, 9.0462276092529297);

		Position goal = GoalCoordinatesCalculator.findGoal(a, b, c);
		System.out.println("real one2= " + goal.toString());
		then(goal.getX()).isBetween(9.0, 11.0);
		then(goal.getY()).isBetween(-11.0, -9.0);

	}*/
	@Test
	public void testLast5() {

		Position a = new Position(10.997702598571777, 10.111210823059082, 0.0, 20.13697052001953);
		Position b = new Position(-11.622086524963379, 10.126947402954102, 0.0, 29.540651321411133);
		Position c = new Position(-11.055853843688965, -14.029830932617188, 0.0, 21.438983917236328);

		Position goal = GoalCoordinatesCalculator.findGoal(a, b, c);
		System.out.println("real one3= " + goal.toString());
		then(goal.getX()).isBetween(9.0, 11.0);
		then(goal.getY()).isBetween(-11.0, -9.0);

	}
}
