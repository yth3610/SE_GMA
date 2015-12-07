package RobotMovement;

import Interface.MapForm;
import MapArtifacts.Position;

public class SimSensorManager {
	public static void hazardSensor() { //hazard의 유무를 받아오는 method
		MapForm.getRobot().hazardSensor();
	}
	
	public static void colorBlobSensor() { //colorblob 배열을 받아오는 method
		MapForm.getRobot().colorBlobSensor(); //default = false;
	}
	
	public static Position positionSensor() { //robot의 position을 받아오는 method
		Position position = MapForm.getRobot().positionSensor();
		return position;
	}
	
	public void setPosition(Position position) { //robot의 position을 설정하는 method
		MapForm.getRobot().setPosition(position);
	}
	
	public void setMap(int[][] map) { //robot이 가지고 있을 map 데이터를 설정하는 method-
		MapForm.getRobot().setMap(map);
	}
}