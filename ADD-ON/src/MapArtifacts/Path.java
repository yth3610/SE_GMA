package MapArtifacts;

import java.util.*;

public class Path {
	
	private static ArrayList<Position> path; // �������
	private static ArrayList<Position> finds; // Ž�� ����
	private static Position start_xy; // ���� ����

	public static void main(String[] args)
	{
		Map map=new Map();
		map.create();
		Path pp = new Path();
		pp.createPath();
	}
	
	// ��� �ʱ�ȭ �ϴ� �Լ�   (Position start_xy, ArrayList<Position> finds)
	public void createPath(){

		//this.start_xy=start_xy;
		//this.finds=finds;

		// ���Ƿ� ������ ���� ������
		finds=new ArrayList<Position>();
		finds.add(new Position(2,3));
		finds.add(new Position(2,4));
		finds.add(new Position(4,3));
			
		start_xy = new Position(0,0);
		
		// ��� ���� ����
		path = new ArrayList<Position>();

		// Map ��ü ����
		Map p_map=new Map();

		// ���� ����, Ž�� ���� ������ ����
		p_map.update("Start", start_xy);
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", finds.get(i));

		// ���� �޾ƿ���
		int[][] map = p_map.getMap();
		
		int x, y;
		int fx, fy;
		boolean move = true;
		
		// path�� �������� �߰�
		path.add(new Position(start_xy.getX(),start_xy.getY()));
		
		// �������� �޾ƿ���
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
				path.add(new Position(x-1, y-1)); // �̵��� ��� ����
				
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
		
		for(int i=0;i<5;i++)
		{
			for(int j=0; i<5; j++)
			{
				//if(map[i][j]==3)
			}
		}
		
	}
	
	// ��� �缳���ϴ� �Լ�
	public void updatePath(){
		
	}
	
	// ��� �����ϴ� �Լ�
	public ArrayList<Position> getPath(){
		return path;
	}

}

