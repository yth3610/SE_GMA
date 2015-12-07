package RobotMovement;

import MapArtifacts.Map;
import MapArtifacts.Path;
import Interface.MapForm;

import java.util.ArrayList;

import Foundation.Finals;
import Foundation.Position;

public class RobotPosition implements Finals{
	private Position currentPosition, nextPosition;
	private ArrayList<Position> pathList;
	ArrayList<Position> movedList = new ArrayList<>();
	public static int pathCount = 1; //movement count
	
	Path path = new Path();
	Map map = new Map();
	
	public RobotPosition () {
		
		SimSensorManager ssm = new SimSensorManager();
		ssm.setMap(map.getMap(1)); //robot이 가지고 있을 맵 설정

		//RobotPositionManager로 부터 robot의 현재위치를 가져온다.
		currentPosition = ssm.positionSensor();
		pathList = path.getPath();
		movedList.add(currentPosition);

		while(pathCount < pathList.size()) { //pathList.size()
			this.createNextPosition(pathList.get(pathCount));
			ssm.hazardSensor(); //hazard 유무 판단
			ssm.colorBlobSensor(); //color blob 유무 판단
			pathList = path.getPath();
			ssm.setMap(map.getMap(1));
			//MapForm.movepaint();
			MapForm.robotPaint(MapForm.getRobot().positionSensor());
		}
		MapForm.moveLog("탐색종료");
		System.out.println("탐색종료");
		
		for(int i=0; i<pathList.size(); i++)
			System.out.print(pathList.get(i).toString()+" ");
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
		MapForm.moveLog(pathCount + "번째 nextPosition : " + nextPosition);
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