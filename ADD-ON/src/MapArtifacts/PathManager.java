package MapArtifacts;

import java.util.ArrayList;

public class PathManager {

	private static Path path;
	
	// 경로 초기화 값 전달
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){
		path= new Path();
		path.createPath(start_x, start_y, finds);
	}
	
	// 경로 출력...? (시퀸스 잘못 그린 것 같다 Planning a Path)
	public void print(){
		
	}
}
