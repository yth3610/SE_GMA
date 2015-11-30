package MapArtifacts;

import java.util.*;

public class Path {
	
	private ArrayList<Position> path; // ������� ( Ŭ���� ���̾�׷��� �Ӽ� �߰��ϱ� )
	private Position path_xy; // �̵� ��� ���� ��ü

	private ArrayList<Position> finds; // Ž�� ����
	private Position start_xy; // ���� ����

	// ��� �ʱ�ȭ �ϴ� �Լ�
	public void createPath(Position start_xy, ArrayList<Position> finds){

		this.start_xy=start_xy;
		this.finds=finds;
		
		// ��� ���� ����
		path = new ArrayList<Position>();
		

		// ���� ����, Ž�� ���� ������ ����
		Map p_map=new Map();
		//p_map.update("Start", );

		// ���� �޾ƿ���
		int[][] map = p_map.getMap();
		
		// ���Ƿ� ������ ���� ������
		int[][] find = {{2,3},{2,4},{4,3}};
		int x, y;
		int fx, fy, fnum=3;
		String s;
		boolean move = true;
		
		map[3][4] = 1;
		map[3][5] = 1;
		map[5][4] = 1; // Ž������
		
		// �������� �߰�
		path_xy = new Position(0,0);
		path.add(path_xy);
		
		x=1; y=1;
		
		for(int i=0;i<fnum;i++)
		{
			fx=find[i][0]+1;
			fy=find[i][1]+1;
			
			// ������ ���� ������ ������. ���� �̵� ���� ����...
			while(true)
			{
				
				if(Math.abs(fx-x)<Math.abs(fy-y))
				{
					if(fy>y && map[x][y+1]!=3)
						y++;
					else if(fy<y && map[x][y-1]!=3)
						y--;
					else if(fx>x && map[x+1][y]!=3)
						x++;
					else if(fx<x && map[x-1][y]!=3)
						x--;
					else 
						move = false;
				}
				else
				{
					if(fx>x && map[x+1][y]!=3)
						x++;
					else if(fx<x && map[x-1][y]!=3)
						x--;
					else if(fy>y && map[x][y+1]!=3)
						y++;
					else if(fy<y && map[x][y-1]!=3)
						y--;
					else 
						move = false;
				}
				
				if(!move)
				{
					if(fx>x && map[x-1][y]!=3)
						x--;
					else if(fx<x && map[x+1][y]!=3)
						x++;
					else if(fy<y && map[x][y+1]!=3)
						y++;
					else if(fy>y && map[x][y-1]!=3)
						y--;
				}
			
				move = true;
				s=(x-1)+","+(y-1);
				path_xy.setPosition(x-1, y-1);
				path.add(path_xy); // �̵��� ��� ����
				
				if(fx==x && fy==y) // Ž������ ������ ���
				{
					x=fx;
					y=fy;
					break;
				}
			}
				
		}
		
		//for(int i=0;i<path.size(); i++)
			//System.out.println(path.get(i).getPositionString());
		
	}
	
	// ��� �缳���ϴ� �Լ�
	public void updatePath(){
		
	}
	
	// ��� �����ϴ� �Լ�
	public ArrayList<Position> getPath(){
		return path;
	}

}

