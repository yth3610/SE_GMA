package RobotMovement;

import MapArtifacts.Position;

public class RobotMovement {
	private Position currentPosition, nextPosition;
	private String movement;
	
	public RobotMovement() {
		
	}
	
	public String createMovement() { //nextPosition을 바탕으로 Movement 생성
		if(currentPosition.getDirection() == nextPosition.getDirection())
			//현재위치의 방향과 다음위치의 방향이 같은 경우
			return "go";
		else { 	//방향이 다른경우 시계방향으로 몇번 움직여야 하는지 Return
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			return String.valueOf(turn);
		}
	}
	
	public void orderMovement(String move) { //RobotMovementInterface에 움직임 명령
		if(movement.equals("go")) { //한칸 이동하는 경우
			switch(currentPosition.getDirection()) {
			case 1: //EAST(+x)
				movement = "+x";
				break;
			case 2: //SOUTH(-y)
				movement = "-y";
				break;
			case 3: //WEST(-x)
				movement = "-x";
				break;
			case 4: //NORTH(+y)
				movement = "+y";
				break;
			default:
					break;
			}
		}
		else //방향을 바꾸는 경우
			movement = move;
	}
	
	public boolean verifyMovement() { //Robot의 움직임 확인
		return true;
	}
	
	public void cancelMovement() {
		
	}
}