package Interface;

import MapArtifacts.MapManager;
import MapArtifacts.Position;

public class SimSensor implements Finals {
	private static Position position; //현재 위치
	private int[][] map;

	//처음 시작시에 생성하여 Operator로 부터 받은 시작지점으로 Robot의 위치를 설정하는 생성자
	public SimSensor(int x, int y) {
		position = new Position(x, y);
	}
	
	public SimSensor() {
		
	}
	
	public void hazardSensor(){
		MapManager mm = new MapManager(); // mm 생성
		
		switch(position.getDirection()) {
		case EAST:
			if(map[position.getX() + 2][position.getY()+1] == HIDE_HAZARD) {
				mm.updateMap("Hazard", new Position(position.getX() + 1, position.getY()));
				MapForm.moveLog("Find HIDE_HAZARD (" + (position.getX()+1) + ", " + position.getY() + ")");
				System.out.println("Find HIDE_HAZARD (" + (position.getX()+1) + ", " + position.getY() + ")");
			}
	        break;
		case SOUTH:
			if(map[position.getX()+1][position.getY()] == HIDE_HAZARD) {
				mm.updateMap("Hazard", new Position(position.getX(), position.getY() - 1));
				MapForm.moveLog("Find HIDE_HAZARD (" + (position.getX()) + ", " + (position.getY()-1) + ")");
				System.out.println("Find HIDE_HAZARD (" + (position.getX()) + ", " + (position.getY()-1) + ")");
			}
	         break;
		case WEST:
			if(map[position.getX()][position.getY()+1] == HIDE_HAZARD) { 
				mm.updateMap("Hazard", new Position(position.getX() - 1, position.getY()));
				MapForm.moveLog("Find HIDE_HAZARD (" + (position.getX()-1) + ", " + (position.getY()) + ")");
				System.out.println("Find HIDE_HAZARD (" + (position.getX()-1) + ", " + (position.getY()) + ")");
			}
			break;
		case NORTH:
			if(map[position.getX()+1][position.getY() + 2] == HIDE_HAZARD) {
				mm.updateMap("Hazard", new Position(position.getX(), position.getY() + 1));
				MapForm.moveLog("Find HIDE_HAZARD (" + (position.getX()) + ", " + (position.getY()+1) + ")");
				System.out.println("Find HIDE_HAZARD (" + (position.getX()) + ", " + (position.getY()+1) + ")");
			}
			break;
		}
	}
	   
	public void colorBlobSensor(){
		MapManager mm = new MapManager(); // mm 생성
		//숨겨진 colorblob을 발견한 경우
		//4방향 모두 찾아야 하기 때문에 4개의 if문 사용
		if(map[position.getX() + 2][position.getY()+1] == HIDE_COLORBLOB) { //EAST(+x) 방향
			mm.updateMap("Color", new Position(position.getX() + 1, position.getY()));
			MapForm.moveLog("Find HIDE_COLORBLOB (" + (position.getX()+1) + ", " + (position.getY()) + ")");
			System.out.println("Find HIDE_COLORBLOB (" + (position.getX()+1) + ", " + (position.getY()) + ")");
		}
		if(position.getY() != 0 && map[position.getX()+1][position.getY()] == HIDE_COLORBLOB) { //SOUTH(-x) 방향
			mm.updateMap("Color", new Position(position.getX(), position.getY() - 1));
			MapForm.moveLog("Find HIDE_COLORBLOB (" + (position.getX()) + ", " + (position.getY()-1) + ")");
			System.out.println("Find HIDE_COLORBLOB (" + (position.getX()) + ", " + (position.getY()-1) + ")");
		}
		if(position.getX() != 0 && map[position.getX()][position.getY()+1] == HIDE_COLORBLOB) { //WEST(-x) 방향
			mm.updateMap("Color", new Position(position.getX() - 1, position.getY()));
			MapForm.moveLog("Find HIDE_COLORBLOB (" + (position.getX()-1) + ", " + (position.getY()) + ")");
			System.out.println("Find HIDE_COLORBLOB (" + (position.getX()-1) + ", " + (position.getY()) + ")");
		}
		if(map[position.getX()+1][position.getY() + 2] == HIDE_COLORBLOB) { //NORTH(+y) 방향
			mm.updateMap("Color", new Position(position.getX(), position.getY() + 1));
			MapForm.moveLog("Find HIDE_COLORBLOB (" + (position.getX()) + ", " + (position.getY()+1) + ")");
			System.out.println("Find HIDE_COLORBLOB (" + (position.getX()) + ", " + (position.getY()+1) + ")");
		}
	}

	public Position positionSensor() {
		return position;
	}
	
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void setDirection(int d) {
		position.setDirection(d);
	}
	
	public void setMap(int[][] map) {
		this.map = map;
	}
}