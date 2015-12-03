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
		//임시
		currentPosition = new Position(1, 1);
		pathList = new ArrayList<>();
		pathList.add(new Position(1, 1));
		pathList.add(new Position(1, 2));
		pathList.add(new Position(2, 2));
		pathList.add(new Position(2, 1));
		
		//연동해서 쓸 부분
		//RobotPositionManager로 부터 robot의 현재위치를 가져온다.
		//currentPosition = RobotPositionManager.getPosition();
		//pathList = path.getPath();
		//for(int i = 0; i < pathList.size(); i++)
		//	this.createNextPosition(pathList.get(i));
		
	}
	
	//path와 currentPositin을 비교하여 다음 위치를 만드는 method
	public void createNextPosition(Position path) {
		nextPosition = new Position();
		if(path.getX() - currentPosition.getX() == 1) {
			//EAST(+x축)으로 한칸 이동해야 하는 경우
			nextPosition.setX(currentPosition.getX() + 1);
			nextPosition.setY(currentPosition.getY());
			nextPosition.setDirection(EAST); //EAST(+x)
		} 
		else if(path.getX() - currentPosition.getX() == -1) {
			//WEST(-x축)으로 한칸 이동해야 하는 경우
			nextPosition.setX(currentPosition.getX() - 1);
			nextPosition.setY(currentPosition.getY());
			nextPosition.setDirection(WEST); //WEST(-x)
		}
		else if(path.getY() - currentPosition.getY() == 1) {
			//NORTH(+y축)으로 한칸 이동해야 하는 경우
			nextPosition.setX(currentPosition.getX());
			nextPosition.setY(currentPosition.getY() + 1);
			nextPosition.setDirection(NORTH); //NORTH(+y)
		} 
		else if(path.getY() - currentPosition.getY() == -1) {
			//SOUTH(-y축)으로 한칸 이동해야 하는 경우
			nextPosition.setX(currentPosition.getX());
			nextPosition.setY(currentPosition.getY() - 1);
			nextPosition.setDirection(SOUTH); //SOUTH(-y)
		}
		RobotMovement rm = new RobotMovement(currentPosition, nextPosition);
	}
	
	//임시
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