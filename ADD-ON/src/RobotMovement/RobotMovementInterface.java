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
			if(random == 1) { //5%확률로 2칸 이동
				if(mapPosition.getX() <= (MapForm.getRobot().positionSensor().getX() + 2)) {
					//2칸 이동하는 경우 Map의 크기를 넘어가면 움직이지 않음
					break;
				}
				else 
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() + 2,
						MapForm.getRobot().positionSensor().getY());
			} else if (random == 2) { //5%확률로 움직이지 않음
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() + 1,
						MapForm.getRobot().positionSensor().getY());
			}
			break;
		case "-y":
			if(random == 1) { //5%확률로 2칸 이동
				if(0 >= (MapForm.getRobot().positionSensor().getY() - 2)) {
					//2칸 이동하는 경우 Map의 크기를 넘어가면 움직이지 않음
					break;
				}
				else
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() - 2);
			} else if (random == 2) { //5%확률로 움직이지 않음
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() - 1);
			}
			break;
		case "-x":
			if(random == 1) { //5%확률로 2칸 이동
				if(0 >= (MapForm.getRobot().positionSensor().getX() - 2)) {
					//2칸 이동하는 경우 Map의 크기를 넘어가면 움직이지 않음
					break;
				}
				else
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() - 2,
						MapForm.getRobot().positionSensor().getY());
			} else if (random == 2) { //5%확률로 움직이지 않음
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX() - 1,
						MapForm.getRobot().positionSensor().getY());
			}
			break;
		case "+y":
			if(random == 1) { //5%확률로 2칸 이동
				if(mapPosition.getY() <= (MapForm.getRobot().positionSensor().getY() + 2)) {
					//2칸 이동하는 경우 Map의 크기를 넘어가면 움직이지 않음
					break;
				}
				else
					MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() + 2);
			} else if (random == 2) { //5%확률로 움직이지 않음
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY());
			} else {
				MapForm.getRobot().setPosition(MapForm.getRobot().positionSensor().getX(),
						MapForm.getRobot().positionSensor().getY() + 1);
			}
			break;
		}
		System.out.println("움직임 수행 후 위치 : " + MapForm.getRobot().positionSensor());
		return MapForm.getRobot().positionSensor();
	}

	public void turnRobot(String move) {
		int turn = Integer.valueOf(move);
		int direction = MapForm.getRobot().positionSensor().getDirection();
		for(int i = 1; i <= turn; i++) {
			direction += 1;
			System.out.println("시계방향 90도 회전(" + i + ")");
			if(direction > 4) //방향값이 NORTH(4)를 넘는경우 보정
				direction %= 4;
		}
		MapForm.getRobot().setDirection(direction);
		//System.out.println("현재 로봇 상태 : " + MapForm.getRobot().positionSensor());
	}
}

