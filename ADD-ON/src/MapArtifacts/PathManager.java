package MapArtifacts;

import java.util.ArrayList;

public class PathManager {

	private static Path path;
	
	// ��� �ʱ�ȭ �� ����
	public void createPath(Position start_xy, ArrayList<Position> finds){
		path= new Path();
		path.createPath(start_xy, finds);
	}
	
	// ��� ���...? (������ �߸� �׸� �� ���� Planning a Path)
	public void print(){
		
	}
}
