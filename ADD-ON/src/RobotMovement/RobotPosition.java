package RobotMovement;

import MapArtifacts.Path;
import MapArtifacts.Position;

import java.util.ArrayList;

public class RobotPosition {
	private Position currentPosition, nextPosition;
	Path path = new Path();

	private ArrayList<Position> pathList;
	private static int mcount = 0; //movement count
	
	
	
	public RobotPosition () {
		//임시
		currentPosition = new Position(1, 1);
		pathList.add(new Position(1, 1));
		pathList.add(new Position(1, 2));
		pathList.add(new Position(2, 2));
		pathList.add(new Position(2, 1));
		
		//	SimSensorManager simSensorM = new SimSensorManager();
		//	path.createPath();
		
	}
	
	//path와 currentPositin을 비교하여 다음 위치를 만드는 method
	public void createNextPosition() {
		
	}
	
	public Position getNextPosition() {
		return this.nextPosition;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RobotPosition r = new RobotPosition();
	}
}

