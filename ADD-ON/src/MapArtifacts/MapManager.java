package MapArtifacts;

import java.util.ArrayList;

public class MapManager {

	private static Map map;
	
	// 지도 초기화 값 전달
	public void create(int x, int y, ArrayList<Position> hazard){
		map=new Map();
		map.create(x, y, hazard);
	}
	
	// 지도 재설정 값 전달
	public void update(String type, Position xy){
		map.update(type, xy);
	}
}