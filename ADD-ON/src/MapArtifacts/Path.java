package MapArtifacts;

import java.util.*;

public class Path {
	
	private static ArrayList<Position> path; // ������� (�湮�ϸ� �����ϵ���???)
	private static ArrayList<Position> finds; // Ž�� ���� (�湮�ϸ� �����ϵ���???)
	private static Position start_xy; // ���� ����
	
	private int[][] map; // getMap()�ؼ� ���� ����
	private static int[][] n_path; // ���� �湮 ���� ��Ÿ���� ����
	private int end=1;
	
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int NORTH = 4;
	
	public static final int ERROR = 999;

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
		map = p_map.getMap();
		
		// n_path �ʱ�ȭ
		n_path=new int[map.length][map[0].length];
		for(int i=0; i<n_path.length; i++)
		{
			for(int j=0; j<n_path.length; j++)
				n_path[i][j]=0;
		}
		
		int x, y;
		int fx, fy;
		boolean move = true;
		
		// �������� �޾ƿ���
		x=start_xy.getX()+1; y=start_xy.getY()+1;
		// �������� ����
		path.add(new Position(x-1, y-1));
		
		// �������� �湮 ���� ó������ ����
		n_path[x][y]=end++;
		
		// �������� ���
		for(int i=1;i<6;i++)
		{
			for(int j=1; j<6; j++)
			{
				if(map[i][j]==3)
					System.out.println("(("+(i-1)+","+(j-1)+"))");
			}
		}

		for(int i=0;i<finds.size();i++)  // ������������ �ѷ����� ��� ���� �ذ� ����...
		{
			// Ž������ �޾ƿ���
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			while(true)
			{
				
				if(Math.abs(fx-x)<Math.abs(fy-y)) // find ���� �� ������ ���� �������� 
				{
					if(fy>y && map[x][y+1]!=3 && n_path[x][y+1]==0)
						y++;
					else if(fy<y && map[x][y-1]!=3 && n_path[x][y-1]==0)
						y--;
					else if(fx>x && map[x+1][y]!=3 && n_path[x+1][y]==0)
						x++;
					else if(fx<x && map[x-1][y]!=3 && n_path[x-1][y]==0)
						x--;
					else 
						move = false;
				}
				else
				{
					if(fx>x && map[x+1][y]!=3 && n_path[x+1][y]==0)
						x++;
					else if(fx<x && map[x-1][y]!=3 && n_path[x-1][y]==0)
						x--;
					else if(fy>y && map[x][y+1]!=3 && n_path[x][y+1]==0)
						y++;
					else if(fy<y && map[x][y-1]!=3 && n_path[x][y-1]==0)
						y--;
					else 
						move = false;
				}
				
				if(!move) // find �ݴ� ���� �� ������ ���� �������� 
				{
					move = true;
					
					if(fx>=x && map[x-1][y]!=3 && n_path[x-1][y]==0)
						x--;
					else if(fx<=x && map[x+1][y]!=3 && n_path[x+1][y]==0)
						x++;
					else if(fy<=y && map[x][y+1]!=3 && n_path[x][y+1]==0)
						y++;
					else if(fy>=y && map[x][y-1]!=3 && n_path[x][y-1]==0)
						y--;
					else 
						move = false; 
				}
				
				if(!move) // ���� ������ �� ��������
				{
					move = true;
					
					switch(minimun(x, y)){
						case EAST :
							x++;
							break;
						case NORTH :
							y++;
							break;
						case WEST :
							x--;
							break;
						case SOUTH :
							y--;
							break;
						case ERROR:
							move = false;
					}
				}

				if(!move) // �����ӿ� ������ ���
				{
					System.out.println("ERROR");
					return ;
				}
				
				move = true; // �ʱ�ȭ
				path.add(new Position(x-1, y-1)); // ��� ����
				
				// �湮 ���� ����
				n_path[x][y]=end++;
				
				if(fx==x && fy==y) // Ž������ ������ ���
				{
					x=fx;
					y=fy;
					break;
				}
			}
				
		}
		
		// path ���
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
	}
	
	// ��� �缳���ϴ� �Լ� (Position start_xy, ArrayList<Position> finds)??
	public void updatePath(){
		
	}
	
	// ��� �����ϴ� �Լ�
	public ArrayList<Position> getPath(){
		return path;
	}

	
	// ���� ������ �� ���� return	
	public int minimun(int x, int y)
	{
		int a, b, c, d;
		
		a = n_path[x+1][y]; 
		b = n_path[x-1][y]; 
		c = n_path[x][y+1]; 
		d = n_path[x][y-1];
		
		if(map[x+1][y]==3)
			a=999;
		if(map[x-1][y]==3)
			b=999;
		if(map[x][y+1]==3)
			c=999;
		if(map[x][y-1]==3)
			d=999;
		
		if(a<=b && a<=c && a<=d)
			return EAST;
		else if(b<=a && b<=c && b<=d)
			return WEST;
		else if(c<=b && c<=a && c<=d)
			return NORTH;
		else if(d<=b && d<=c && d<=a)
			return SOUTH;
		else
			return ERROR;
	}

}

