package RobotMovement;

import MapArtifacts.Path;
import MapArtifacts.Position;
import Interface.Finals;

import java.util.ArrayList;

public class RobotPosition implements Finals{
	private Position currentPosition, nextPosition;
	private ArrayList<Position> pathList;
	private static int pathCount = 1; //movement count
	
	Path path = new Path();
	
	public RobotPosition () {
		//�ӽ�
		currentPosition = new Position(1, 1);
		pathList = new ArrayList<>();
		pathList.add(new Position(1, 1));
		pathList.add(new Position(1, 2));
		pathList.add(new Position(2, 2));
		pathList.add(new Position(2, 1));
		
		//�����ؼ� �� �κ�
		//RobotPositionManager�� ���� robot�� ������ġ�� �����´�.
		//currentPosition = RobotPositionManager.getPosition();
		//pathList = path.getPath();
		//for(int i = 0; i < pathList.size(); i++)
		//	this.createNextPosition(pathList.get(i));
		
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
		RobotMovement rm = new RobotMovement(currentPosition, nextPosition);
	}
	
	//�ӽ�
	public void setCurrentPosition(int x, int y) {
		this.currentPosition.setX(x);
		this.currentPosition.setY(y);
	}
	
	public Position getNextPosition() {
		return this.nextPosition;
	}
	
	public static void plusCount() {
		pathCount++;
	}
	
	public static void main(String[] args) {

		RobotPosition r = new RobotPosition();
		//r.createNextPosition();
		System.out.println(r.getNextPosition().toString());
		r.setCurrentPosition(1, 2);
		//r.createNextPosition();
		System.out.println(r.getNextPosition().toString());
	}
}