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
		System.out.println(RobotPosition.pathCount + "������ ���� �� ��ġ : " + MapForm.getRobot().positionSensor());
		return MapForm.getRobot().positionSensor();
	}

	public void turnRobot(String move) {
		int turn = Integer.valueOf(move);
		int direction = MapForm.getRobot().positionSensor().getDirection();
		for(int i = 1; i <= turn; i++) {
			direction += 1;
			if(direction > 4) //���Ⱚ�� NORTH(4)�� �Ѵ°�� ����
				direction %= 4;
		}
		MapForm.getRobot().setDirection(direction);
	}
}

