package MapArtifacts;

import java.util.ArrayList;

public class MapManager {

	private static Map map;
	
	// ���� �ʱ�ȭ �� ����
	public int[][] create(int x, int y, ArrayList<Position> hazard){
		map=new Map();
		return map.create(x, y, hazard);
	}
	
	// ���� �缳�� �� ����
	public void update(String type, Position xy){
		map.update(type, xy);
	}
	
	// ���� ���...??? (������ �߸� �׸� �� ���� Handling map)
	public void print(){
		
	}
}