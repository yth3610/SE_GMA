package RobotMovement;

import Interface.Finals;
import MapArtifacts.Path;
import MapArtifacts.Position;

public class RobotMovement implements Finals {
	private Position currentPosition, nextPosition;
	private RobotMovementInterface RMI;
	private boolean updatePath = false;
	Position resultPosition;
	Path path = new Path();
	
	public RobotMovement(Position currentPosition, Position nextPosition) {
		RMI = new RobotMovementInterface();
		this.currentPosition = currentPosition;
		this.nextPosition = nextPosition;
		this.createMovement();
		RobotPosition.plusCount(); //Movement 명령 수행후 다음 path를 위한 pathCount를 증가시켜준다
		//System.out.println("------plus---Count-----");
	}
	
	public void createMovement() { //nextPosition을 바탕으로 Movement 생성
		if(currentPosition.getDirection() == nextPosition.getDirection()) {
			//현재위치의 방향과 다음위치의 방향이 같은 경우
			String movement = "go";
			this.orderMovement(movement);
		} else if(nextPosition.getDirection() > currentPosition.getDirection()) { //방향이 다른경우 시계방향으로 몇번 움직여야 하는지 Return
			int turn = nextPosition.getDirection() - currentPosition.getDirection();
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
			movement = "go";
			this.orderMovement(movement);
		} else {
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			if(turn == 0) //완전 반대방향인 경우 보정
				turn = 2;
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
			movement = "go";
			this.orderMovement(movement);
		}	
	}
	
	public void orderMovement(String move) { //RobotMovementInterface에 움직임 명령
		
		String movement = "";
		//Position resultPosition = new Position();
		
		if(move.equals("go")) { //한칸 이동하는 경우 방향을 String형으로 전달한다
			switch(nextPosition.getDirection()) {
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
			resultPosition = RMI.moveRobot(movement); //Robot이 움직이게 한다
			boolean checkMovement = verifyMovement(resultPosition, movement);
			System.out.println("first check");
			
			if(checkMovement == false) { //움직임을 확인한다
				path.updatePath(resultPosition, RobotPosition.pathCount);
			}
		}
		else { //방향을 바꾸는 경우
			movement = move; //방향전환 횟수
			RMI.turnRobot(movement); //Robot이 방향을 전환하게 한다
			System.out.println("방향전환 완료");
			//방향전환은 오류가 없으므로 움직임을 확인하지 않는다
		}
	}
	
	public boolean verifyMovement(Position position, String movement) { //Robot의 움직임 확인
		if(position.getX() == this.nextPosition.getX() && position.getY() == this.nextPosition.getY()
				&& position.getDirection() == this.nextPosition.getDirection()) {
			//robot의 현재 위치(X, Y)와 방향이 모두 같은 경우
			System.out.println("Correct Movement.");
			System.out.println("=====================");
			return true;
		}
		else { //위치가 다른 경우
			System.out.println("Wrong Movement.");
			return false;
		}
	}
}