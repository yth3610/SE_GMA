package RobotMovement;

import Interface.Finals;
import MapArtifacts.Position;

public class RobotMovement implements Finals {
	private Position currentPosition, nextPosition;
	private RobotMovementInterface RMI;
	
	public RobotMovement(Position currentPosition, Position nextPosition) {
		RMI = new RobotMovementInterface();
		this.currentPosition = currentPosition;
		this.nextPosition = nextPosition;
	}
	
	public void createMovement() { //nextPosition을 바탕으로 Movement 생성
		if(currentPosition.getDirection() == nextPosition.getDirection()) {
			//현재위치의 방향과 다음위치의 방향이 같은 경우
			String movement = "go";
			this.orderMovement(movement);
		}
		else { 	//방향이 다른경우 시계방향으로 몇번 움직여야 하는지 Return
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
		}
	}
	
	public void orderMovement(String move) { //RobotMovementInterface에 움직임 명령
		
		String movement = "";
		
		if(move.equals("go")) { //한칸 이동하는 경우 방향을 String형으로 전달한다
			switch(currentPosition.getDirection()) {
			case EAST: //EAST(+x)
				movement = "+x";
				break;
			case SOUTH: //SOUTH(-y)
				movement = "-y";
				break;
			case WEST: //WEST(-x)
				movement = "-x";
				break;
			case NORTH: //NORTH(+y)
				movement = "+y";
				break;
			default:
					break;
			}
			Position resultPosition = RMI.moveRobot(movement); //Robot이 움직이게 한다
			verifyMovement(resultPosition, movement); //움직임을 확인한다
		}
		else { //방향을 바꾸는 경우
			movement = move; //방향전환 횟수
			Position resultPosition = RMI.moveRobot(movement); //Robot이 방향을 전환하게 한다
			verifyMovement(resultPosition, movement); //움직임을 확인한다
		}
		RobotPosition.plusCount(); //Movement 명령 수행후 다음 path를 위한 pathCount를 증가시켜준다
	}
	
	public boolean verifyMovement(Position position, String movement) { //Robot의 움직임 확인
		if(position.getX() == this.nextPosition.getX() && position.getY() == this.nextPosition.getY()
				&& position.getDirection() == this.nextPosition.getDirection())
			//robot의 현재 위치(X, Y)와 방향이 모두 같은 경우
			return true;
		else { //위치가 다른 경우
			this.cancelMovement(position, movement); //잘못 움직인것을 취소 한다
			return true;
		}
	}
	
	public void cancelMovement(Position position, String movement) {
		switch(movement) {
		case "+x": //EAST(+x) 방향으로 움직여야 했던 경우
			if(position.getX() - this.nextPosition.getX() == -1) { //움직이지 않은 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == 1) { //2칸 움직인 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "-y": //SOUTH(-y) 방향으로 움직여야 했던 경우
			if(position.getY() - this.nextPosition.getY() == 1) { //움직이지 않은 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == -1) { //2칸 움직인 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "-x": //WEST(-x) 방향으로 움직여야 했던 경우
			if(position.getX() - this.nextPosition.getX() == 1) { //움직이지 않은 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == -1) { //2칸 움직인 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "+y": //NORTH(+y) 방향으로 움직여야 했던 경우
			if(position.getY() - this.nextPosition.getX() == -1) { //움직이지 않은 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == 1) { //2칸 움직인 경우
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		default:
				break;
		}
	}
	
}