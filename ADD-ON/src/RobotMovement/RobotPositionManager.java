package RobotMovement;

import MapArtifacts.Position;

public class RobotPositionManager {
	public static Position getPosition() {
		return SimSensorManager.positionSensor();
	}
}