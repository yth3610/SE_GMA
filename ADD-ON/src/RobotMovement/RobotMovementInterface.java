package RobotMovement;

import Foundation.Position;
import Interface.MapForm;

public class RobotMovementInterface {
	
	private Position mapPosition;
	
	public RobotMovementInterface() {
		mapPosition = MapForm.getMapPosition();
	}

	public Position moveRobot(String move) {
		int random = (int)(Math.random() * 1000) % 20;
		System.out.println("random : " + random);

		switch(move) {
		case "+x":
			if(random == 1) { //5%Ȯ���� 2ĭ �̵�
				if(mapPosition.getX() <= (MapForm.getRobot().positionSensor().getX() + 2)) {
					//2ĭ �̵��ϴ� ��� Map�� ũ�⸦ �Ѿ�� �������� ����
					break;
				}
				else 
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() + 2,
						MapForm.getRobot().positionSensor().getY());
			} else if (random == 2) { //5%Ȯ���� �������� ����
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() + 1,
						MapForm.getRobot().positionSensor().getY());
			}
			break;
		case "-y":
			if(random == 1) { //5%Ȯ���� 2ĭ �̵�
				if(0 >= (MapForm.getRobot().positionSensor().getY() - 2)) {
					//2ĭ �̵��ϴ� ��� Map�� ũ�⸦ �Ѿ�� �������� ����
					break;
				}
				else
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() - 2);
			} else if (random == 2) { //5%Ȯ���� �������� ����
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() - 1);
			}
			break;
		case "-x":
			if(random == 1) { //5%Ȯ���� 2ĭ �̵�
				if(0 >= (MapForm.getRobot().positionSensor().getX() - 2)) {
					//2ĭ �̵��ϴ� ��� Map�� ũ�⸦ �Ѿ�� �������� ����
					break;
				}
				else
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() - 2,
						MapForm.getRobot().positionSensor().getY());
			} else if (random == 2) { //5%Ȯ���� �������� ����
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() - 1,
						MapForm.getRobot().positionSensor().getY());
			}
			break;
		case "+y":
			if(random == 1) { //5%Ȯ���� 2ĭ �̵�
				if(mapPosition.getY() <= (MapForm.getRobot().positionSensor().getY() + 2)) {
					//2ĭ �̵��ϴ� ��� Map�� ũ�⸦ �Ѿ�� �������� ����
					break;
				}
				else
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() + 2);
			} else if (random == 2) { //5%Ȯ���� �������� ����
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() + 1);
			}
			break;
		}
		System.out.println("������ ���� �� ��ġ : " + MapForm.getRobot().positionSensor());
		return MapForm.getRobot().positionSensor();
	}

	public void turnRobot(String move) {
		int turn = Integer.valueOf(move);
		int direction = MapForm.getRobot().positionSensor().getDirection();
		for(int i = 1; i <= turn; i++) {
			direction += 1;
			System.out.println("�ð���� 90�� ȸ��(" + i + ")");
			if(direction > 4) //���Ⱚ�� NORTH(4)�� �Ѵ°�� ����
				direction %= 4;
		}
		MapForm.getRobot().setDirection(direction);
		//System.out.println("���� �κ� ���� : " + MapForm.getRobot().positionSensor());
	}
}

