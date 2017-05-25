package algorithm.mediator;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.midi.Soundbank;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import algorithm.Coordinates;
import algorithm.GoalCoordinatesCalculator;
import algorithm.proxyMovements.Movements;
import algorithm.proxyMovements.ProxyMovement;
import sensorsActuators.DistanceSensor;
import sensorsActuators.IrSensor;
import sensorsActuators.PoseSensor;
import sensorsActuators.SensorListener;
import sensorsActuators.SpeedActuator;

public class Mediator {

	private static Mediator med = new Mediator();
	private SpeedActuator speedAct;
	private IrSensor centralIr;
	private IrSensor leftIr;
	private IrSensor rightIr;
	private IrSensor backIr;
	private PoseSensor poseSens;
	private DistanceSensor distSens;
	private String ip = "192.168.1.72";
	private ProxyMovement movement = new ProxyMovement();

	private double[] actualPosition;
	private double distanceFromGoal;
	private double[] centralInfo;
	private double[] leftInfo;
	private double[] rightInfo;
	private double[] backInfo;

	private Coordinates goalCoordinates = new Coordinates(0.0, 0.0, 0.0, 0.0);

	public static Mediator getMed() {
		return med;
	}

	public void runRobot(String name) throws Exception {
		initializePhase(name);
		if (isEnoughSpaceAround())
			initializeGoalCoordinates();
		else
			// TODO: do something else;
			movement.selectMovementType(Movements.STOP);
		speedAct.act(movement.move());

	}

	public void goStraight() throws Exception {
		movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
		speedAct.act(movement.move());
	}

	public void goBack() throws Exception {
		movement.selectMovementType(Movements.BACK);
		speedAct.act(movement.move());
	}

	public void rotateOf(double relative, String direction) throws Exception {
		poseSens.sense();
		double actualAngle = actualPosition[6];
		movement.selectMovementType(direction);
		speedAct.act(movement.move());
		poseSens.sense();
		while (actualPosition[6] != (actualAngle + relative)) {
			poseSens.sense();
		}
		stop();
	}
	public void rotateTo(double absoluteAngle, String direction) throws Exception {
		movement.selectMovementType(direction);
		speedAct.act(movement.move());
		poseSens.sense();
		while (actualPosition[6] != absoluteAngle) {
			poseSens.sense();
		}
		stop();
	}
	public void stop() throws Exception {
		movement.selectMovementType(Movements.STOP);
		speedAct.act(movement.move());
	}

	public double[] getActualPosition() throws Exception {
		poseSens.sense();
		return actualPosition;
	}

	public double getDistanceFromGoal() throws Exception {
		distSens.sense();
		return distanceFromGoal;
	}

	public double[] getBackInfo() throws Exception {
		backIr.sense();
		return backInfo;
	}

	public double[] getCentralInfo() throws Exception {
		centralIr.sense();
		return centralInfo;
	}

	public double[] getLeftInfo() throws Exception {
		leftIr.sense();
		return leftInfo;
	}

	public double[] getRightInfo() throws Exception {
		rightIr.sense();
		return rightInfo;
	}

	private boolean isEnoughSpaceAround() {
		
		return true;
	}

	private void initializeGoalCoordinates() throws Exception {
		poseSens.sense();
		distSens.sense();
		Coordinates a = new Coordinates(actualPosition[1], actualPosition[2], actualPosition[6], distanceFromGoal);
		movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
		speedAct.act(movement.move());
		Coordinates tmp = new Coordinates(actualPosition[1], actualPosition[2], actualPosition[6], distanceFromGoal);
		Coordinates b = new Coordinates(0.0, 0.0, 0.0, 0.0);
		Coordinates c = new Coordinates(0.0, 0.0, 0.0, 0.0);
		int i = 1;
		while (true) {
			if (checkCoordsOnSameAxis(tmp, 0.5)) {
				b = new Coordinates(actualPosition[1], actualPosition[2], actualPosition[6], distanceFromGoal);
				tmp = new Coordinates(actualPosition[1], actualPosition[2], actualPosition[6], distanceFromGoal);
				movement.selectMovementType(Movements.ROTATE_RIGHT);
				speedAct.act(movement.move());
				break;
			}

		}
		while (!rotationCompleted(tmp, 90)) {
		}
		movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
		speedAct.act(movement.move());
		while (true) {
			poseSens.sense();
			distSens.sense();
			if (checkCoordsOnSameAxis(tmp, 0.5)) {
				c = new Coordinates(actualPosition[1], actualPosition[2], actualPosition[6], distanceFromGoal);
				break;
			}

		}
		System.out.println(a + "\n" + b + "\n" + c);
		Coordinates goal;
		goal = GoalCoordinatesCalculator.lastTry(a, b, c);
		System.out.println(goal);
	}

	private boolean rotationCompleted(Coordinates refer, double degrees) throws Exception {
		poseSens.sense();
		distSens.sense();
		double m = refer.getM();
		// System.out.println("degrees:
		// "+(Math.abs(Math.abs(positionLearned[6])-Math.abs(m)))+" m:
		// "+Math.abs(m)+ " new Val= "+ Math.abs(positionLearned[6]));
		// System.out.println("calculated: "+(degrees*Math.PI/180.0));
		if (Math.abs(Math.abs(actualPosition[6]) - Math.abs(m)) >= (degrees * Math.PI / 180.0))
			return true;
		return false;

	}

	private boolean checkCoordsOnSameAxis(Coordinates last, double soglia) throws IOException {
		poseSens.sense();
		distSens.sense();
		if (Math.abs(last.getX() - actualPosition[1]) > soglia || Math.abs(last.getY() - actualPosition[2]) > soglia)
			return true;
		return false;
	}

	private void initializePhase(String name) throws IOException {

		speedAct = new SpeedActuator(name, "motion", ip, 4000);

		centralIr = new IrSensor(name, "ir1", ip, 4000);
		centralIr.setSensorListener(new SensorListener() {
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {
			}

			@Override
			public void onSense(double[] meas) {
				centralInfo = meas;
				for (double measure : meas) {
					if (measure <= 1.5) {
						// System.out.println(measure);
						// movement.selectMovementType(ProxyMovement.STOP);
					}
				}
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});

		leftIr = new IrSensor(name, "ir2", ip, 4000);
		leftIr.setSensorListener(new SensorListener() {

			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {
			}

			@Override
			public void onSense(double[] meas) {
				leftInfo = meas;
				for (double measure : meas) {
					if (measure <= 1.5) {
						// System.out.println(measure);
						// movement.selectMovementType(ProxyMovement.STOP);
					}
				}
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});

		rightIr = new IrSensor(name, "ir3", ip, 4000);
		rightIr.setSensorListener(new SensorListener() {

			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {
			}

			@Override
			public void onSense(double[] meas) {
				rightInfo = meas;
				for (double measure : meas) {
					if (measure <= 1.5) {
						// System.out.println(measure);
						// movement.selectMovementType(ProxyMovement.STOP);
					}
				}
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});

		backIr = new IrSensor(name, "ir4", ip, 4000);
		backIr.setSensorListener(new SensorListener() {

			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {
			}

			@Override
			public void onSense(double[] meas) {
				backInfo = meas;
				for (double measure : meas) {
					if (measure <= 1.5) {
						// System.out.println(measure);
						// movement.selectMovementType(ProxyMovement.STOP);
					}
				}
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});
		poseSens = new PoseSensor(name, "pose", ip, 4000);
		poseSens.setSensorListener(new SensorListener() {
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {
			}

			@Override
			public void onSense(double[] meas) {
				actualPosition = meas;
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});

		distSens = new DistanceSensor(name, "prox", ip, 4000);
		distSens.setSensorListener(new SensorListener() {
			@Override
			public void onSense(String s, HashMap<String, Double> map) {
				try {
					distanceFromGoal = map.get("target");
				} catch (NullPointerException ex) {
					System.out.println(map);
				}
				// System.out.println("DISTANCE SENSOR:");
				//
				// System.out.println(s);
				//
				// Set o = map.keySet();
				// for ( Object a : o) {
				// System.out.println(a);
				// System.out.println(map.get(a));
				// }
			}

			@Override
			public void onSense(double[] meas) {
			}

			@Override
			public void onSense(double arg0) {
			}

			@Override
			public void onSense(int arg0) {
			}
		});
	}
}
