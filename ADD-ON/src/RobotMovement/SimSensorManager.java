package RobotMovement;

import Foundation.Position;
import Interface.MapForm;

public class SimSensorManager {
	public void hazardSensor(){ //hazard�� ������ �޾ƿ��� method
		MapForm.getRobot().hazardSensor();
	}
	
	public void colorBlobSensor(){ //colorblob �迭�� �޾ƿ��� method
		MapForm.getRobot().colorBlobSensor(); //default = false;
	}
	
	public Position positionSensor() { //robot�� position�� �޾ƿ��� method
		Position position = MapForm.getRobot().positionSensor();
		return position;
	}
	
	public void setMap(int[][] map) { //robot�� ������ ���� map �����͸� �����ϴ� method-
		MapForm.getRobot().setMap(map);
	}
}