package MapArtifacts;

import java.util.ArrayList;

import Foundation.Position;

public class MapManager {

	private static Map map;
	
	// ���� �ʱ�ȭ �� ����
	public void createMap(int x, int y, ArrayList<Position> hazard){
		map=new Map();
		map.createMap(x, y, hazard);
	}
	
	// ���� �缳�� �� ����
	public void updateMap(String type, Position xy){
		map.updateMap(type, xy);
	}
}