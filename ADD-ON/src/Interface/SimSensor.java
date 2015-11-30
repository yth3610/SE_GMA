package Interface;

import MapArtifacts.Position;

interface SIM { //static ��� ����� ���� interface
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
	private boolean hazard; //�ٷ� ���� ��ġ�� hazard�� �ִ���, ������
	private boolean[] colorBlob = new boolean[4]; //[0]EAST, [1]SOUTH, [2]WEST, [3]NORTH
	private Position position; //���� ��ġ

	//ó�� ���۽ÿ� �����Ͽ� Operator�� ���� ���� ������������ Robot�� ��ġ�� �����ϴ� ������
	public SimSensor(int x, int y) {
		this.position = new Position(x, y);
	}

	//���� ��ġ, ���� ��ġ �� ��ġ�� Hazard ����, ���� ��ġ�� ColorBlob�� �ִ��� Ȯ��
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
