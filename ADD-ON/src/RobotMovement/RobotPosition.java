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
		ssm.setMap(map.getMap(0)); //robot이 가지고 있을 맵 설정

		//RobotPositionManager로 부터 robot의 현재위치를 가져온다.
		currentPosition = RobotPositionManager.getPosition();
		pathList = path.getPath();

		while(pathCount < pathList.size()) { //pathList.size()
			this.createNextPosition(pathList.get(pathCount));
		}
		System.out.println("탐색종료");
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
		System.out.println(pathCount + "번째 nextPosition : " + nextPosition);
		RobotMovement rm = new RobotMovement(currentPosition, nextPosition);
	}
	
	public Position getNextPosition() {
		return this.nextPosition;
	}
	
	public static void plusCount() {
		pathCount++;
	}
}