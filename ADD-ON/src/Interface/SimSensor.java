package Interface;

import MapArtifacts.MapManager;
import MapArtifacts.Position;

public class SimSensor implements Finals {
	private static Position position; //현재 위치
	private int[][] map;

	//처음 시작시에 생성하여 Operator로 부터 받은 시작지점으로 Robot의 위치를 설정하는 생성자
	public SimSensor(int x, int y) {
		this.position = new Position(x, y);
	}
	
	public SimSensor() {
		
	}
	
	public void hazardSensor() {
	      MapManager mm = new MapManager(); // mm 생성
	      
	      switch(this.position.getDirection()) {
	      case EAST:
	         if(map[position.getX() + 1][position.getY()] == HIDE_HAZARD) {
	            mm.updateMap("Hazard", new Position(position.getX() + 1, position.getY()));
	         }
	         break;
	      case SOUTH:
	         if(map[position.getX()][position.getY() - 1] == HIDE_HAZARD) {
	            mm.updateMap("Hazard", new Position(position.getX(), position.getY() - 1));
	         }
	         break;
	      case WEST:
	         if(map[position.getX() - 1][position.getY()] == HIDE_HAZARD) {
	            mm.updateMap("Hazard", new Position(position.getX() - 1, position.getY()));
	         }
	         break;
	      case NORTH:
	         if(map[position.getX()][position.getY() + 1] == HIDE_HAZARD) {
	            mm.updateMap("Hazard", new Position(position.getX(), position.getY() + 1));
	         }
	         break;
	      }
	   }
	   
	   public void colorBlobSensor() {
	      MapManager mm = new MapManager(); // mm 생성
	      //숨겨진 colorblob을 발견한 경우
	      //4방향 모두 찾아야 하기 때문에 4개의 if문 사용
	      if(map[position.getX() + 1][position.getY()] == HIDE_COLORBLOB) { //EAST(+x) 방향
	         mm.updateMap("Color", new Position(position.getX() + 1, position.getY()));
	      }
	      if(position.getY() != 0 && map[position.getX()][position.getY() - 1] == HIDE_COLORBLOB) { //SOUTH(-x) 방향
	         mm.updateMap("Color", new Position(position.getX(), position.getY() - 1));
	      }
	      if(position.getX() != 0 && map[position.getX() - 1][position.getY()] == HIDE_COLORBLOB) { //WEST(-x) 방향
	         mm.updateMap("Color", new Position(position.getX() - 1, position.getY()));
	      }
	      if(map[position.getX()][position.getY() + 1] == HIDE_COLORBLOB) { //NORTH(+y) 방향
	         mm.updateMap("Color", new Position(position.getX(), position.getY() + 1));
	      }
	   }

	public Position positionSensor() {
		return this.position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setPosition(int x, int y) {
		this.position.setX(x);
		this.position.setY(y);
	}
	
	public void setDirection(int d) {
		this.position.setDirection(d);
	}
	
	public void setMap(int[][] map) {
		this.map = map;
	}
}