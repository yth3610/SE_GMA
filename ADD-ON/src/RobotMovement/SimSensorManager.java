package RobotMovement;

import Interface.MapForm;
import MapArtifacts.Position;

public class SimSensorManager {
	public static boolean hazardSensor() { //hazard�� ������ �޾ƿ��� method
		boolean hazard = MapForm.getRobot().hazardSensor();
		return hazard;
	}
	
	public static boolean[] colorBlobSensor() { //colorblob �迭�� �޾ƿ��� method
		boolean[] colorblob = MapForm.getRobot().colorBlobSensor(); //default = false;
		return colorblob;
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