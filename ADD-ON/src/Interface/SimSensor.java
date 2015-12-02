package Interface;

import MapArtifacts.Map;
import MapArtifacts.Position;

public class SimSensor implements Finals {
	private boolean hazard; //바로 앞의 위치에 hazard가 있는지, 없는지
	private boolean[] colorBlob; //[1]EAST, [2]SOUTH, [3]WEST, [4]NORTH
	private static Position position; //현재 위치
	private Map mmap = new Map();
	private int[][] map;

	//처음 시작시에 생성하여 Operator로 부터 받은 시작지점으로 Robot의 위치를 설정하는 생성자
	public SimSensor(int x, int y) {
		this.position = new Position(x, y);
	}
	
	public boolean hazardSensor() {
		switch(this.position.getDirection()) {
		case EAST:
			if(map[position.getX() + 1][position.getY()] == HIDE_HAZARD) {
				hazard = true;
			}
			break;
		case SOUTH:
			if(map[position.getX()][position.getY() - 1] == HIDE_HAZARD) {
				hazard = true;
			}
			break;
		case WEST:
			if(map[position.getX() - 1][position.getY()] == HIDE_HAZARD) {
				hazard = true;
			}
			break;
		case NORTH:
			if(map[position.getX()][position.getY() + 1] == HIDE_HAZARD) {
				hazard = true;
			}
			break;
		}
		return hazard;
	}
	
	public boolean[] colorBlobSensor() {
		boolean[] colorblob = new boolean[5]; //default = false;
		//숨겨진 colorblob을 발견한 경우
		//4방향 모두 찾아야 하기 때문에 4개의 if문 사용
		if(map[position.getX() + 1][position.getY()] == HIDE_COLORBLOB) { //EAST(+x) 방향
			colorblob[EAST] = true;
			map[position.getX() + 1][position.getY()] = COLORBLOB;
		}
		if(map[position.getX()][position.getY() - 1] == HIDE_COLORBLOB) { //SOUTH(-x) 방향
			colorblob[SOUTH] = true;
			map[position.getX()][position.getY() - 1] = COLORBLOB;
		}
		if(map[position.getX() - 1][position.getY()] == HIDE_COLORBLOB) { //WEST(-x) 방향
			colorblob[WEST] = true;
			map[position.getX() - 1][position.getY()] = COLORBLOB;
		}
		if(map[position.getX()][position.getY() + 1] == HIDE_COLORBLOB) { //NORTH(+y) 방향
			colorblob[NORTH] = true;
			map[position.getX()][position.getY() + 1] = COLORBLOB;
		}
		return colorblob;
	}
	
	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public Position positionSensor() {
		return this.position;
	}
}