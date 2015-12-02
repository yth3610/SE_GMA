package Interface;

import MapArtifacts.Map;
import MapArtifacts.Position;

public class SimSensor implements Finals {
	private boolean hazard; //�ٷ� ���� ��ġ�� hazard�� �ִ���, ������
	private boolean[] colorBlob; //[1]EAST, [2]SOUTH, [3]WEST, [4]NORTH
	private static Position position; //���� ��ġ
	private Map mmap = new Map();
	private int[][] map;

	//ó�� ���۽ÿ� �����Ͽ� Operator�� ���� ���� ������������ Robot�� ��ġ�� �����ϴ� ������
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
		//������ colorblob�� �߰��� ���
		//4���� ��� ã�ƾ� �ϱ� ������ 4���� if�� ���
		if(map[position.getX() + 1][position.getY()] == HIDE_COLORBLOB) { //EAST(+x) ����
			colorblob[EAST] = true;
			map[position.getX() + 1][position.getY()] = COLORBLOB;
		}
		if(map[position.getX()][position.getY() - 1] == HIDE_COLORBLOB) { //SOUTH(-x) ����
			colorblob[SOUTH] = true;
			map[position.getX()][position.getY() - 1] = COLORBLOB;
		}
		if(map[position.getX() - 1][position.getY()] == HIDE_COLORBLOB) { //WEST(-x) ����
			colorblob[WEST] = true;
			map[position.getX() - 1][position.getY()] = COLORBLOB;
		}
		if(map[position.getX()][position.getY() + 1] == HIDE_COLORBLOB) { //NORTH(+y) ����
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