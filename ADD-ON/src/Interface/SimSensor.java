package Interface;

import MapArtifacts.Position;

interface SIM { //static 상수 사용을 위한 interface
	public static final int START = 0;
	public static final int FIND = 1;
	public static final int COLORBLOB = 2;
	public static final int HAZARD = 3;
	public static final int HIDE_COLORBLOB = 4;
	public static final int HIDE_HAZARD = 5;
	
	public static final int EAST = 0;
	public static final int SOUTH = 1;
	public static final int WEST = 2;
	public static final int NORTH = 3;
}

public class SimSensor implements SIM {
	private boolean hazard; //바로 앞의 위치에 hazard가 있는지, 없는지
	private boolean[] colorBlob = new boolean[4]; //[0]EAST, [1]SOUTH, [2]WEST, [3]NORTH
	private Position position; //현재 위치

	//처음 시작시에 생성하여 Operator로 부터 받은 시작지점으로 Robot의 위치를 설정하는 생성자
	public SimSensor(int x, int y) {
		this.position = new Position(x, y);
	}

	//현재 위치, 현재 위치 앞 위치에 Hazard 여부, 현재 위치에 ColorBlob이 있는지 확인
	public SimSensor() {
		
	}
	
	private boolean hazardSensor() {
		return false;
	}
	
	private boolean[] colorBlobSensor() {
		boolean[] colorblob = new boolean[4]; //default = false;
		
		return colorblob;
	}
	
	private Position positionSensor() {
		return position;
	}

	public Position getPosition() {
		return this.position;
	}
}
