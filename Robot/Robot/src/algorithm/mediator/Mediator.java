package algorithm.mediator;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiFunction;

import algorithm.BugTwo;
import algorithm.GoalCoordinatesCalculator;
import algorithm.Position;
import algorithm.SensorInfo;
import algorithm.logic.CheckSpace;
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

	private Position actualPosition = new Position();
	private double distanceFromGoal;
	private SensorInfo centralInfo = new SensorInfo();
	private SensorInfo leftInfo = new SensorInfo();
	private SensorInfo rightInfo = new SensorInfo();
	private SensorInfo backInfo = new SensorInfo();

	private Position goalCoordinates = new Position(0.0, 0.0, 0.0, 0.0);
	
	private static BiFunction<Double,Double, Double> pitagora = (a,b) -> Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
	private BugTwo algorithm= new BugTwo();


	public static Mediator getMed() {
		return med;
	}

	public Position getGoal() {
		return goalCoordinates;
	}
	
	public void runRobot(String name) throws Exception {
		initializePhase(name);
		goalCoordinates=CheckSpace.getChecker().findGoal();
		algorithm.run();

	}

	public void goStraight(Double distance) {
		try {
			poseSens.sense();
			double actualX=actualPosition.getX();
			double actualY=actualPosition.getY();
			
			movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
			speedAct.act(movement.move());
			poseSens.sense();
			while (pitagora.apply(actualX-actualPosition.getX(),actualY-actualPosition.getY())<distance) {
				poseSens.sense();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		stop();


	}
	
	public void goStraight() {
		try {
			movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
			speedAct.act(movement.move());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void goBack(double distance) {
		try {
			poseSens.sense();
			double actualX=actualPosition.getX();
			double actualY=actualPosition.getY();
			
			movement.selectMovementType(Movements.STRAIGHT_MOVEMENT);
			speedAct.act(movement.move());
			poseSens.sense();
			while (pitagora.apply(actualX-actualPosition.getX(),actualY-actualPosition.getY())<distance) {
				poseSens.sense();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		stop();
	}
	public void goBack(){
		try {
			movement.selectMovementType(Movements.BACK);
			speedAct.act(movement.move());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void rotateOf(double relative, String direction) {
		try {
			poseSens.sense();
			double actualAngle = actualPosition.getRadiants();
			movement.selectMovementType(direction);
			speedAct.act(movement.move());
			poseSens.sense();
			while (actualPosition.getRadiants() != (actualAngle + relative)) {
				poseSens.sense();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		stop();
	}

	public void rotateTo(double absoluteAngle, String direction) {
		try {
			movement.selectMovementType(direction);
			speedAct.act(movement.move());
			poseSens.sense();
			while (actualPosition.getRadiants() != absoluteAngle) {
				poseSens.sense();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		stop();
	}

	public void stop() {
		try {
			movement.selectMovementType(Movements.STOP);
			speedAct.act(movement.move());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Position getActualPosition() {
		try {
			poseSens.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return actualPosition;
	}

	public double getDistanceFromGoal() {
		try {
			distSens.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return distanceFromGoal;
	}

	public SensorInfo getBackInfo() {
		try {
			backIr.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return backInfo;
	}

	public SensorInfo getCentralInfo() {
		try {
			centralIr.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return centralInfo;
	}

	public SensorInfo getLeftInfo() {
		try {
			leftIr.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return leftInfo;
	}

	public SensorInfo getRightInfo() {
		try {
			rightIr.sense();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rightInfo;
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
				centralInfo.setStreamSensed(meas);
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
				leftInfo.setStreamSensed(meas);
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
				rightInfo.setStreamSensed(meas);
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
				backInfo.setStreamSensed(meas);
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
				actualPosition.setPosition(meas);
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
