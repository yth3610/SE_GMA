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
	
	public void createMovement() { //nextPosition�� �������� Movement ����
		if(currentPosition.getDirection() == nextPosition.getDirection()) {
			//������ġ�� ����� ������ġ�� ������ ���� ���
			String movement = "go";
			this.orderMovement(movement);
		}
		else { 	//������ �ٸ���� �ð�������� ��� �������� �ϴ��� Return
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			String movement = String.valueOf(turn);
			this.orderMovement(movement);
		}
	}
	
	public void orderMovement(String move) { //RobotMovementInterface�� ������ ���
		
		String movement = "";
		
		if(move.equals("go")) { //��ĭ �̵��ϴ� ��� ������ String������ �����Ѵ�
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
			Position resultPosition = RMI.moveRobot(movement); //Robot�� �����̰� �Ѵ�
			verifyMovement(resultPosition, movement); //�������� Ȯ���Ѵ�
		}
		else { //������ �ٲٴ� ���
			movement = move; //������ȯ Ƚ��
			Position resultPosition = RMI.moveRobot(movement); //Robot�� ������ ��ȯ�ϰ� �Ѵ�
			verifyMovement(resultPosition, movement); //�������� Ȯ���Ѵ�
		}
		RobotPosition.plusCount(); //Movement ��� ������ ���� path�� ���� pathCount�� ���������ش�
	}
	
	public boolean verifyMovement(Position position, String movement) { //Robot�� ������ Ȯ��
		if(position.getX() == this.nextPosition.getX() && position.getY() == this.nextPosition.getY()
				&& position.getDirection() == this.nextPosition.getDirection())
			//robot�� ���� ��ġ(X, Y)�� ������ ��� ���� ���
			return true;
		else { //��ġ�� �ٸ� ���
			this.cancelMovement(position, movement); //�߸� �����ΰ��� ��� �Ѵ�
			return true;
		}
	}
	
	public void cancelMovement(Position position, String movement) {
		switch(movement) {
		case "+x": //EAST(+x) �������� �������� �ߴ� ���
			if(position.getX() - this.nextPosition.getX() == -1) { //�������� ���� ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == 1) { //2ĭ ������ ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "-y": //SOUTH(-y) �������� �������� �ߴ� ���
			if(position.getY() - this.nextPosition.getY() == 1) { //�������� ���� ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == -1) { //2ĭ ������ ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "-x": //WEST(-x) �������� �������� �ߴ� ���
			if(position.getX() - this.nextPosition.getX() == 1) { //�������� ���� ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getX() - this.nextPosition.getX() == -1) { //2ĭ ������ ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		case "+y": //NORTH(+y) �������� �������� �ߴ� ���
			if(position.getY() - this.nextPosition.getX() == -1) { //�������� ���� ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			else if(position.getY() - this.nextPosition.getY() == 1) { //2ĭ ������ ���
				Position resultPosition = RMI.moveRobot(movement);
				verifyMovement(resultPosition, movement);
			}
			break;
		default:
				break;
		}
	}
	
}