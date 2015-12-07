package RobotMovement;

import MapArtifacts.Map;
import MapArtifacts.Path;
import MapArtifacts.Position;
import Interface.Finals;
import Interface.MapForm;

import java.util.ArrayList;

public class RobotPosition implements Finals{
	private Position currentPosition, nextPosition;
	private ArrayList<Position> pathList;
	public static int pathCount = 1; //movement count
	
	Path path = new Path();
	Map map = new Map();
	
	public RobotPosition () {
		
		SimSensorManager ssm = new SimSensorManager();
		ssm.setMap(map.getMap(0)); //robot�� ������ ���� �� ����

		//RobotPositionManager�� ���� robot�� ������ġ�� �����´�.
		currentPosition = RobotPositionManager.getPosition();
		pathList = path.getPath();

		while(pathCount < pathList.size()) { //pathList.size()
			this.createNextPosition(pathList.get(pathCount));
		}
		System.out.println("Ž������");
	}
	
	//path�� currentPositin�� ���Ͽ� ���� ��ġ�� ����� method
	public void createNextPosition(Position path) {
		nextPosition = new Position();
		if(path.getX() - currentPosition.getX() == 1) {
			//EAST(+x��)���� ��ĭ �̵��ؾ� �ϴ� ���
			nextPosition.setX(currentPosition.getX() + 1);
			nextPosition.setY(currentPosition.getY());
			nextPosition.setDirection(EAST); //EAST(+x)
		} 
		else if(path.getX() - currentPosition.getX() == -1) {
			//WEST(-x��)���� ��ĭ �̵��ؾ� �ϴ� ���
			nextPosition.setX(currentPosition.getX() - 1);
			nextPosition.setY(currentPosition.getY());
			nextPosition.setDirection(WEST); //WEST(-x)
		}
		else if(path.getY() - currentPosition.getY() == 1) {
			//NORTH(+y��)���� ��ĭ �̵��ؾ� �ϴ� ���
			nextPosition.setX(currentPosition.getX());
			nextPosition.setY(currentPosition.getY() + 1);
			nextPosition.setDirection(NORTH); //NORTH(+y)
		} 
		else if(path.getY() - currentPosition.getY() == -1) {
			//SOUTH(-y��)���� ��ĭ �̵��ؾ� �ϴ� ���
			nextPosition.setX(currentPosition.getX());
			nextPosition.setY(currentPosition.getY() - 1);
			nextPosition.setDirection(SOUTH); //SOUTH(-y)
		}
		System.out.println(pathCount + "��° nextPosition : " + nextPosition);
		RobotMovement rm = new RobotMovement(currentPosition, nextPosition);
	}
	
	public Position getNextPosition() {
		return this.nextPosition;
	}
	
	public static void plusCount() {
		pathCount++;
	}
}