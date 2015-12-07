package RobotMovement;

import Interface.MapForm;
import MapArtifacts.Position;

public class SimSensorManager {
	public static void hazardSensor() { //hazard�� ������ �޾ƿ��� method
		MapForm.getRobot().hazardSensor();
	}
	
	public static void colorBlobSensor() { //colorblob �迭�� �޾ƿ��� method
		MapForm.getRobot().colorBlobSensor(); //default = false;
	}
	
	public static Position positionSensor() { //robot�� position�� �޾ƿ��� method
		Position position = MapForm.getRobot().positionSensor();
		return position;
	}
	
	public void setPosition(Position position) { //robot�� position�� �����ϴ� method
		MapForm.getRobot().setPosition(position);
	}
	
	public void setMap(int[][] map) { //robot�� ������ ���� map �����͸� �����ϴ� method-
		MapForm.getRobot().setMap(map);
	}
}