package RobotMovement;

import Interface.Finals;
import MapArtifacts.Path;
import MapArtifacts.Position;

public class RobotMovement implements Finals {
	private Position currentPosition, nextPosition;
	private RobotMovementInterface RMI;
	Position resultPosition;
	Path path = new Path();
	
	public RobotMovement(Position currentPosition, Position nextPosition) {
		RMI = new RobotMovementInterface();
		this.currentPosition = currentPosition;
		this.nextPosition = nextPosition;
		this.createMovement();
		RobotPosition.plusCount(); //Movement ��� ������ ���� path�� ���� pathCount�� ���������ش�
		//System.out.println("------plus---Count-----");
	}
	
	public void createMovement() { //nextPosition�� �������� Movement ����
		if(currentPosition.getDirection() == nextPosition.getDirection()) {
			//������ġ�� ����� ������ġ�� ������ ���� ���
			String movement = "go";
			this.orderMovement(movement);
		} else if(nextPosition.getDirection() > currentPosition.getDirection()) { //������ �ٸ���� �ð�������� ��� �������� �ϴ��� Return
			int turn = nextPosition.getDirection() - currentPosition.getDirection();
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
			movement = "go";
			this.orderMovement(movement);
		} else {
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			if(turn == 0) //���� �ݴ������ ��� ����
				turn = 2;
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
			movement = "go";
			this.orderMovement(movement);
		}	
	}
	
	public void orderMovement(String move) { //RobotMovementInterface�� ������ ���
		
		String movement = "";
		Position resultPosition = new Position();
		
		if(move.equals("go")) { //��ĭ �̵��ϴ� ��� ������ String������ �����Ѵ�
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
			resultPosition = RMI.moveRobot(movement); //Robot�� �����̰� �Ѵ�
			boolean checkMovement = verifyMovement(resultPosition, movement);
			while(checkMovement == false) { //�������� Ȯ���Ѵ�
				//System.out.println("movement check : " + checkMovement);
				this.cancelMovement(resultPosition, movement); //�߸� �����ΰ��� ��� �Ѵ�
				checkMovement = verifyMovement(resultPosition, movement);
			}
		}
		else { //������ �ٲٴ� ���
			movement = move; //������ȯ Ƚ��
			RMI.turnRobot(movement); //Robot�� ������ ��ȯ�ϰ� �Ѵ�
			System.out.println("������ȯ �Ϸ�");
			//������ȯ�� ������ �����Ƿ� �������� Ȯ������ �ʴ´�
		}
	}
	
	public boolean verifyMovement(Position position, String movement) { //Robot�� ������ Ȯ��
		if(position.getX() == this.nextPosition.getX() && position.getY() == this.nextPosition.getY()
				&& position.getDirection() == this.nextPosition.getDirection()) {
			//robot�� ���� ��ġ(X, Y)�� ������ ��� ���� ���
			System.out.println("Correct Movement.");
			System.out.println("=====================");
			return true;
		}
		else { //��ġ�� �ٸ� ���
			System.out.println("Wrong Movement.");
			return false;
		}
	}
	
	public boolean cancelMovement(Position position, String movement) {
		System.out.println("Cancel Movement...");
		switch(movement) {
		case "+x": //EAST(+x) �������� �������� �ߴ� ���
			if(position.getX() - this.nextPosition.getX() == -1) { //�������� ���� ���
				resultPosition = RMI.moveRobot(movement);
				return verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == 1) { //2ĭ ������ ���
				path.updatePath(position, RobotPosition.pathCount);
				return true; 
			}
			break;
		case "-y": //SOUTH(-y) �������� �������� �ߴ� ���
			if(position.getY() - this.nextPosition.getY() == 1) { //�������� ���� ���
				resultPosition = RMI.moveRobot(movement);
				return verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == -1) { //2ĭ ������ ���
				path.updatePath(position, RobotPosition.pathCount);
				return true;
			}
			break;
		case "-x": //WEST(-x) �������� �������� �ߴ� ���
			if(position.getX() - this.nextPosition.getX() == 1) { //�������� ���� ���
				resultPosition = RMI.moveRobot(movement);
				return verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == -1) { //2ĭ ������ ���
				path.updatePath(position, RobotPosition.pathCount);
				return true;
			}
			break;
		case "+y": //NORTH(+y) �������� �������� �ߴ� ���
			if(position.getY() - this.nextPosition.getX() == -1) { //�������� ���� ���
				resultPosition = RMI.moveRobot(movement);
				return verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == 1) { //2ĭ ������ ���
				path.updatePath(position, RobotPosition.pathCount);
				return true;
			}
			break;
		}
		return true;
	}
}