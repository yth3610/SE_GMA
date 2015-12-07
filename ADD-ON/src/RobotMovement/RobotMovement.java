package RobotMovement;

import Foundation.Finals;
import Foundation.Position;
import Interface.MapForm;
import MapArtifacts.Path;
import MapArtifacts.Map;

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
		MapForm.moveLog("=======================");
		System.out.println("=======================");
	}
	
	public void createMovement() { //nextPosition�� �������� Movement ����
		if(currentPosition.getDirection() == nextPosition.getDirection()) {
			//������ġ�� ����� ������ġ�� ������ ���� ���
			String movement = "go";
			this.orderMovement(movement); //�� ĭ ������
		} else if(nextPosition.getDirection() > currentPosition.getDirection()) { //������ �ٸ���� �ð�������� ��� �������� �ϴ��� Return
			int turn = nextPosition.getDirection() - currentPosition.getDirection();
			String movement = String.valueOf(turn);
			this.orderMovement(movement); //������ȯ
			movement = "go";
			this.orderMovement(movement); //�� ĭ ������
		} else {
			int turn = (nextPosition.getDirection() + currentPosition.getDirection()) % 4;
			if(turn == 0) //���� �ݴ������ ��� ����
				turn = 2;
			String movement = String.valueOf(turn);
			this.orderMovement(movement); //������ȯ
			movement = "go";
			this.orderMovement(movement); //�� ĭ ������
		}	
	}
	
	public void orderMovement(String move) { //RobotMovementInterface�� ������ ���
		
		String movement = "";
		//Position resultPosition = new Position();
		
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
			robotDie(resultPosition); // �κ��� �߸������� ���������� �� ���
			boolean checkMovement = verifyMovement(resultPosition, movement);
			//System.out.println("first check");

			if(checkMovement == false) { //�������� Ȯ���Ѵ�
				path.updatePath(resultPosition, RobotPosition.pathCount);
			}
		}
		else { //������ �ٲٴ� ���
			movement = move; //������ȯ Ƚ��
			RMI.turnRobot(movement); //Robot�� ������ ��ȯ�ϰ� �Ѵ�
			//System.out.println("������ȯ �Ϸ�");
			//������ȯ�� ������ �����Ƿ� �������� Ȯ������ �ʴ´�
		}
	}
	
	public boolean verifyMovement(Position position, String movement) { //Robot�� ������ Ȯ��
		if(position.getX() == this.nextPosition.getX() && position.getY() == this.nextPosition.getY()) {
			//robot�� ���� ��ġ(X, Y)�� ��� ���� ���
			MapForm.moveLog("Correct Movement.");
			System.out.println("Correct Movement.");
			//System.out.println("=====================");
			return true;
		}
		else { //��ġ�� �ٸ� ���
			MapForm.moveLog("Wrong Movement.");
			System.out.println("Wrong Movement.");
			return false;
		}
	}
	
	public void robotDie(Position resultPosition){
		Map m = new Map();
		int[][] map = m.getMap(0);
		
		if(map[resultPosition.getX()][resultPosition.getY()]==HAZARD)
			MapForm.errorMessage("�κ��� ���������� �����ϴ�."+"\n3�ʵ� ���α׷��� ����˴ϴ�.");;
			
	}
}