package RobotMovement;

import Foundation.Position;

public class RobotPositionManager {
	public RobotPositionManager() {
		RobotPosition rp = new RobotPosition();
	}
	public static Position getPosition() {
		return SimSensorManager.positionSensor();
	}
}