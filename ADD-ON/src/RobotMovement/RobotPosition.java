package RobotMovement;

import MapArtifacts.Path;

import java.util.ArrayList;

public class RobotPosition {
	private String currentPosition, nextPosition;

	private ArrayList<String> path;
	private static int mcount = 0; //movement count
	
	public RobotPosition () {
		SimSensorManager simSensorM = new SimSensorManager();
		Path path = new Path();
	}
}

