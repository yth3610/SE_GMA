package MapArtifacts;

import java.util.*;

public class Path {
	
	private ArrayList<Position> path; // ������� ( Ŭ���� ���̾�׷��� �Ӽ� �߰��ϱ� )
	private Position path_xy; // �̵� ��� ���� ��ü

	private ArrayList<Position> finds; // Ž�� ����
	private Position start_xy; // ���� ����

	// ��� �ʱ�ȭ �ϴ� �Լ�   (Position start_xy, ArrayList<Position> finds)
	public void createPath(){

		//this.start_xy=start_xy;
		//this.finds=finds;

		// ���Ƿ� ������ ���� ������
		Position find_xy = new Position(2,3);
		finds=new ArrayList<Position>();
		finds.add(find_xy);
		find_xy.setPosition(2,4);
		finds.add(find_xy);
		find_xy.setPosition(4,3);
		finds.add(find_xy);
			
		start_xy = new Position(0,0);
		
		// ��� ���� ����
		path = new ArrayList<Position>();

		// Map ��ü ����
		Map p_map=new Map();

		// ���� ����, Ž�� ���� ������ ����
		p_map.update("Start", start_xy);
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", path.get(i));

		// ���� �޾ƿ���
		int[][] map = p_map.getMap();
		
		int x, y;
		int fx, fy;
		boolean move = true;
		
		// path�� �������� �߰�
		path_xy = new Position(start_xy.getX(),start_xy.getY());
		path.add(path_xy);
		
		x=start_xy.getX()+1; y=start_xy.getY()+1;
		
		for(int i=0;i<finds.size();i++)
		{
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
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
		
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).getPositionString());
		
	}
	
	// ��� �缳���ϴ� �Լ�
	public void updatePath(){
		
	}
	
	// ��� �����ϴ� �Լ�
	public ArrayList<Position> getPath(){
		return path;
	}

}

