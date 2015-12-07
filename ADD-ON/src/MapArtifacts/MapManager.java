package MapArtifacts;

import java.util.ArrayList;

import Foundation.Position;

public class MapManager {

	private static Map map;
	
	// 지도 초기화 값 전달
	public void createMap(int x, int y, ArrayList<Position> hazard){
		map=new Map();
		map.createMap(x, y, hazard);
	}
	
	// 지도 재설정 값 전달
	public void updateMap(String type, Position xy){
		map.updateMap(type, xy);
	}
}