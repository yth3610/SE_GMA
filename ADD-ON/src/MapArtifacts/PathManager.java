package MapArtifacts;

import java.util.ArrayList;

import Foundation.Position;

public class PathManager {

	private static Path path;
	
	// ��� �ʱ�ȭ �� ����
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){
		path= new Path();
		path.createPath(start_x, start_y, finds);
	}
}
