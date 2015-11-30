package RobotMovement;

import java.util.ArrayList;

import MapArticfacts.Path;

public class RobotPosition {
	private String currentPosition, nextPosition;

	private ArrayList<String> path;
	private static int mcount = 0; //movement count
	
	public RobotPosition () {
		SimSensorManager simSensorM = new SimSensorManager();
		Path path = new Path();
		path.create();
	}
}

