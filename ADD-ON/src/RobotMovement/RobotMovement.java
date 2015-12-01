package RobotMovement;

import Interface.Finals;
import MapArtifacts.Position;

public class RobotMovement implements Finals {
	private Position currentPosition, nextPosition;
	private String movement;
	
	public RobotMovement() {
		
	}
	
	public String createMovement() { //nextPosition�� �������� Movement ����
		if(currentPosition.getDirection() == nextPosition.getDirection())
			//������ġ�� ����� ������ġ�� ������ ���� ���
			return "go";
		else { 	//������ �ٸ���� �ð�������� ��� �������� �ϴ��� Return
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			return String.valueOf(turn);
		}
	}
	
	public void orderMovement(String move) { //RobotMovementInterface�� ������ ���
		if(movement.equals("go")) { //��ĭ �̵��ϴ� ���
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
		}
		else //������ �ٲٴ� ���
			movement = move;
	}
	
	public boolean verifyMovement() { //Robot�� ������ Ȯ��
		return true;
	}
	
	public void cancelMovement() {
		
	}
}