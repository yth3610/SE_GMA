package RobotMovement;

import Interface.MapForm;
import MapArtifacts.Position;

public class RobotMovementInterface {
	
	private String movement;
	
	public RobotMovementInterface() {

	}
	
	public Position moveRobot(String move) {
		switch(move) {
		case "+x":
			MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() + 1,
					MapForm.getRobot().positionSensor().getY());
			break;
		case "-y":
			MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
					MapForm.getRobot().positionSensor().getY() - 1);
			break;
		case "-x":
			MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() - 1,
					MapForm.getRobot().positionSensor().getY());
			break;
		case "+y":
			MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
					MapForm.getRobot().positionSensor().getY() + 1);
			break;
		}
		return MapForm.getRobot().positionSensor();
	}

	public void turnRobot(String move) {
		MapForm.getRobot().setDirection(Integer.valueOf(move));;
	}
}

