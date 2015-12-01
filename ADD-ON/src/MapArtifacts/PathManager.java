package MapArtifacts;

import java.util.ArrayList;

public class PathManager {

	private static Path path;
	
	// 경로 초기화 값 전달
	public void createPath(Position start_xy, ArrayList<Position> finds){
		path= new Path();
		path.createPath();
	}
	
	// 경로 출력...? (시퀸스 잘못 그린 것 같다 Planning a Path)
	public void print(){
		
	}
}
