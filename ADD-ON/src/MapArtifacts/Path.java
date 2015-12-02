package MapArtifacts;

import java.util.*;
import Interface.Finals;

public class Path implements Finals{
	
	private static ArrayList<Position> path; // �������
	private static ArrayList<Position> finds; // Ž�� ����
	private static int start_x; // ���� ����
	private static int start_y; // ���� ����
	
	private ArrayList<Position> path2; // �κ� ���
	private int[][] map; // ���� ����
	
	public static final int OK = 9;
	public static final int ERROR = 999;
	
	// ��� �ʱ�ȭ �ϴ� �Լ�   (start_xy : ������� , finds : Ž������)
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){

		// �ʱ�ȭ
		this.start_x=start_x;
		this.start_y=start_y;
		this.finds=finds;
		
		// ��� ���� ����
		path = new ArrayList<Position>();

		// Map ��ü ����
		Map p_map=new Map();

		// ���� ����, Ž�� ���� ������ ����
		p_map.update("Start", new Position(start_x, start_y));
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", finds.get(i));
		
		// ���� �޾ƿ���
		map = p_map.getMap();

		int x, y, fx, fy; // ��������, Ž������
		
		// �������� �޾ƿ���
		x=start_x+1; y=start_y+1;
		
		// �������� ����
		path.add(new Position(x-1, y-1));
		
		// �������� ���
		for(int i=1;i<6;i++)
		{
			for(int j=1; j<6; j++)
			{
				if(map[i][j]==HAZARD)
					System.out.println("(("+(i-1)+","+(j-1)+"))");
			}
		}

		for(int i=0;i<finds.size();i++)  // ������������ �ѷ����� ��� ���� �ذ� ����...
		{
			// Ž������ �޾ƿ���
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			if(create(x, y, fx, fy)==ERROR)
				return ;
			
			for(int j=0; j<path2.size(); j++)
				path.add(path2.get(j));
			
			x=fx; y=fy;
		}
		
		// path ���
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
	}
	
	// ��� �缳���ϴ� �Լ� (Position start_xy, ArrayList<Position> finds)??
	public void updatePath(){
		
		// ������ġ �޾ƿ��� 
		
		// ������ġ �޾ƿ���
		int x=3, y=2, fx, fy;
		ArrayList<Position> un_finds=finds;
		
		// ���� ��ġ ������ ��� �����  (����.....)
		for(int i=path.size()-1;i>=0;i++)
		{
			if( x==path.get(path.size()-1).getX() && y==path.get(path.size()-1).getY())
				break;
			else
				path.remove(path.size()-1);
		}
		
		// Ž���� Ž������ ����� (����.....)
		for(int i=un_finds.size()-1; i>=0; i++)
		{
			if(path.indexOf((un_finds).get(i))>0)
				un_finds.remove(i);
		}
		
		// ��� ����
		for(int i=0;i<un_finds.size();i++)  // ������������ �ѷ����� ��� ���� �ذ� ����...
		{
			// Ž������ �޾ƿ���
			fx=un_finds.get(i).getX()+1;
			fy=un_finds.get(i).getY()+1;
			
			if(create(x, y, fx, fy)==ERROR)
				return ;
			
			for(int j=0; j<path2.size(); j++)
				path.add(path2.get(j));
			
			x=fx; y=fy;
		}
	}
	
	// ��� �����ϴ� �Լ�
	public ArrayList<Position> getPath(){
		return path;
	}
	
	// x, y ���� fx, fy���� ��� ���ϱ�
	private int create(int x, int y, int fx, int fy)
	{
		int end=1;
		boolean move = true;
	
		// n_path �ʱ�ȭ
		int [][]n_path=new int[map.length][map[0].length];
		for(int i=0; i<n_path.length; i++)
		{
			for(int j=0; j<n_path[0].length; j++)
			{
				if(map[i][j]==HAZARD)
					n_path[i][j]=ERROR;
				else
					n_path[i][j]=0;
			}
		}
		
		// �ʱ�ȭ
		path2 = new ArrayList<Position>();
		
		while(true)
		{
			
			if(Math.abs(fx-x)<Math.abs(fy-y)) // find ���� �� ������ ���� �������� 
			{
				if(fy>y && n_path[x][y+1]==0)
					y++;
				else if(fy<y && n_path[x][y-1]==0)
					y--;
				else if(fx>x && n_path[x+1][y]==0)
					x++;
				else if(fx<x && n_path[x-1][y]==0)
					x--;
				else 
					move = false;
			}
			else
			{
				if(fx>x && n_path[x+1][y]==0)
					x++;
				else if(fx<x && n_path[x-1][y]==0)
					x--;
				else if(fy>y && n_path[x][y+1]==0)
					y++;
				else if(fy<y && n_path[x][y-1]==0)
					y--;
				else 
					move = false;
			}
			
			if(!move) // ���� ������ �� ��������
			{	
				move=true;
				
				switch(oldestMove(x, y, n_path)){
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
						break;
				}
			}

			if(!move) // �����ӿ� ������ ���
			{
				System.out.println("ERROR");
				return ERROR;
			}
			
			move = true; // �ʱ�ȭ
			path2.add(new Position(x-1, y-1)); // ��� ����
			
			// �湮 ���� ����
			n_path[x][y]=end++;
			
			if(fx==x && fy==y) // Ž������ ������ ���
			{
				x=fx;
				y=fy;
				break;
			}
			
			if(end==ERROR) // ����̵� Ƚ���� 999�̻��� �Ǹ� ������������ �ѷ��ο��ٰ� �Ǵ��Ͽ� �����Ѵ�.
			{
				System.out.println("BLOCKED ERROR");
				return ERROR;
			}
		}
		return OK;
	}
	
	// ���� ������ �� ���� return	
	private int oldestMove(int x, int y, int[][] n_path)
	{
		int a, b, c, d;
		
		a = n_path[x+1][y]; 
		b = n_path[x-1][y]; 
		c = n_path[x][y+1]; 
		d = n_path[x][y-1];
		
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