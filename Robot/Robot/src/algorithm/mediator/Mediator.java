package algorithm.mediator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import algorithm.Coordinates;
import algorithm.GoalCoordinatesCalculator;
import algorithm.proxyMovements.ProxyMovement;
import sensorsActuators.DistanceSensor;
import sensorsActuators.IrSensor;
import sensorsActuators.PoseSensor;
import sensorsActuators.SensorListener;
import sensorsActuators.SpeedActuator;	

public class Mediator {

	private static Mediator med= new Mediator();
	private SpeedActuator speedAct;
    private IrSensor centralIr;
    private IrSensor leftIr;
    private IrSensor rightIr;
    private IrSensor backIr;
    private PoseSensor poseSens;
    private DistanceSensor distSens;
    private String ip="192.168.1.72";
    private ProxyMovement movement=new ProxyMovement();
    
    private double[] positionLearned;
    private double distanceFromGoal;
    private Coordinates goalCoordinates=new Coordinates(0.0, 0.0, 0.0, 0.0);

	public static Mediator getMed() {
		return med;
	}
	
	public void runRobot(String name)throws Exception{
		initializePhase(name);
		initializeGCoord2();
		movement.selectMovementType(ProxyMovement.STOP);
		speedAct.act(movement.move());

	}
	
	private void initializeGCoord2()throws Exception{
		poseSens.sense();
		distSens.sense();
		Coordinates a= new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
		movement.selectMovementType(ProxyMovement.STRAIGHT_MOVEMENT);
		speedAct.act(movement.move());
		Coordinates tmp=new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
		Coordinates b= new Coordinates(0.0, 0.0, 0.0, 0.0);
		Coordinates c= new Coordinates(0.0, 0.0, 0.0, 0.0);
		int i=1;
		while (true) {
			poseSens.sense();
			distSens.sense();
			if(newCoords(tmp, 2)&&i==1){
				b=new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
				tmp=new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
				movement.selectMovementType(ProxyMovement.TURN_ON_YOURSELF);
				speedAct.act(movement.move());
				i=2;
			}else if(newCoords(tmp, (float)0.8)&&i==2){
				c=new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
				break;
			}	
		}
		
		Coordinates goal=GoalCoordinatesCalculator.triangulateThis(a,b,c);
		System.out.println(goal);
		
	}
	private boolean newCoords(Coordinates last, float soglia) throws IOException{
		poseSens.sense();
		distSens.sense();
		if(Math.abs(last.getX()-positionLearned[1])>soglia||Math.abs(last.getY()-positionLearned[2])>soglia)
			return true;
		return false;
	}
	private void initializeGoalCoordinates() throws IOException{
		poseSens.sense();
		distSens.sense();
		Coordinates previousPosition= new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
		movement.selectMovementType(ProxyMovement.TURN_ON_YOURSELF);
		speedAct.act(movement.move());
		poseSens.sense();
		distSens.sense();
		Coordinates newPosition= new Coordinates(positionLearned[1],positionLearned[2],positionLearned[4], distanceFromGoal);
		Coordinates min= new Coordinates(0.0, 0.0, 0.0, 0.0);
		Coordinates max= new Coordinates(0.0, 0.0, 0.0, 0.0);
		boolean amIDecreasing=newPosition.getDistanceFromGoal()<previousPosition.getDistanceFromGoal();
		while(!goalCoordinates.isValid()){
//			System.out.println("GIROOOO");
//			movement.move().forEach((key, value)-> System.out.println(key+" "+value));
//			speedAct.act(movement.move());
			newInfos(previousPosition, newPosition);
			if(newPosition.getDistanceFromGoal()<previousPosition.getDistanceFromGoal()&&!amIDecreasing){
				max.setCoordinates(previousPosition.getX(), previousPosition.getY(), previousPosition.getM(), previousPosition.getDistanceFromGoal());
//				System.out.println("MAX found \n"+max.toString());
				amIDecreasing=true;
			}else if(newPosition.getDistanceFromGoal()>previousPosition.getDistanceFromGoal()&&amIDecreasing){
				min.setCoordinates(previousPosition.getX(), previousPosition.getY(), previousPosition.getM(), previousPosition.getDistanceFromGoal());
//				System.out.println("MIN found \n"+min.toString());
				amIDecreasing=false;
			}
			if (min.isValid()&& max.isValid()) {
				goalCoordinates=GoalCoordinatesCalculator.getGoalCoordinates(min, max);
			}
		}
		System.out.println(goalCoordinates.toString());
	}
	private void newInfos(Coordinates older, Coordinates newer) throws IOException{
		poseSens.sense();
		distSens.sense();
		older.setCoordinates(newer.getX(), newer.getY(), newer.getM(), newer.getDistanceFromGoal());
		newer.setCoordinates(positionLearned[1], positionLearned[2], positionLearned[4], distanceFromGoal);
//		System.out.println("Sensed coord: "+newer.toString());
		
	}
	private void initializePhase(String name) throws IOException{

        speedAct = new SpeedActuator(name, "motion", ip, 4000);

        centralIr = new IrSensor(name, "ir1", ip,4000);
        centralIr.setSensorListener(new SensorListener() {
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {	
			}
			
			@Override
			public void onSense(double[] meas) {
				for (double measure : meas) {
					if(measure<=1.5){
//						System.out.println(measure);
//			        	movement.selectMovementType(ProxyMovement.STOP);
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

        leftIr = new IrSensor(name, "ir2", ip,4000);
        leftIr.setSensorListener(new SensorListener() {
			
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {	
			}
			
			@Override
			public void onSense(double[] meas) {
				for (double measure : meas) {
					if(measure<=1.5){
//						System.out.println(measure);
//			        	movement.selectMovementType(ProxyMovement.STOP);
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

        rightIr = new IrSensor(name, "ir3", ip,4000);
        rightIr.setSensorListener(new SensorListener() {
			
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {	
			}
			
			@Override
			public void onSense(double[] meas) {
				for (double measure : meas) {
					if(measure<=1.5){
//						System.out.println(measure);
//			        	movement.selectMovementType(ProxyMovement.STOP);
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

        backIr = new IrSensor(name, "ir4", ip,4000);
        backIr.setSensorListener(new SensorListener() {
			
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {	
			}
			
			@Override
			public void onSense(double[] meas) {
				for (double measure : meas) {
					if(measure<=1.5){
//						System.out.println(measure);
//			        	movement.selectMovementType(ProxyMovement.STOP);
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
        poseSens = new PoseSensor(name, "pose", ip,4000);
        poseSens.setSensorListener(new SensorListener() {
			@Override
			public void onSense(String arg0, HashMap<String, Double> arg1) {	
			}
			
			@Override
	        public void onSense(double[] meas) {
				positionLearned=meas;
	        }
			
			@Override
			public void onSense(double arg0) {
			}
			
			@Override
			public void onSense(int arg0) {
			}
		});

        distSens = new DistanceSensor(name, "prox", ip,4000);
        distSens.setSensorListener(new SensorListener() {
        	@Override
            public void onSense(String s, HashMap<String, Double> map) {
        		distanceFromGoal=map.get("target");
//            	System.out.println("DISTANCE SENSOR:");
//
//                System.out.println(s);
//
//                Set o = map.keySet();
//                for ( Object a : o) {
//                    System.out.println(a);
//                    System.out.println(map.get(a));
//                }
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
