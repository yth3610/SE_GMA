package MapArtifacts;

import java.util.ArrayList;

public class PathManager {

	private static Path path;
	
	// ��� �ʱ�ȭ �� ����
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){
		path= new Path();
		path.createPath(start_x, start_y, finds);
	}
	
	// ��� ���...? (������ �߸� �׸� �� ���� Planning a Path)
	public void print(){
		
	}
}
