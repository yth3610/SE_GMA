package MapArtifacts;

import java.util.*;
import Interface.Finals;
import Interface.MapForm;

public class Path implements Finals{
	
	private static ArrayList<Position> path; // �������
	private static ArrayList<Position> finds; // Ž�� ����
	private ArrayList<Position> part_path; // �κ� ���
	private int[][] map; // ���� ����
	
	public static final int OK = 9;
	public static final int ERROR = 999;
	
	// ��� �ʱ�ȭ �ϴ� �Լ�   (start_xy : ������� , finds : Ž������)
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){
		
		int x, y, fx, fy;
		
		// Ž������ �ʱ�ȭ
		this.finds=finds;
		
		// ��� ���� ���� ����
		path = new ArrayList<Position>();
		
		// Map ��ü ����
		Map p_map=new Map();

		// ���� ����, Ž�� ���� ������ ����
		p_map.updateMap("Start", new Position(start_x, start_y));
		for(int i=0; i<finds.size(); i++)
			p_map.updateMap("Find", finds.get(i));
		
		// ���� �޾ƿ���
		map = p_map.getMap(1);

		// �������� �޾ƿ���
		x=start_x+1; y=start_y+1;
		
		// �������� ����
		path.add(new Position(x-1, y-1));
		
		//// �������� ���
		for(int i=1;i<map.length-1;i++)
		{
			for(int j=1; j<map[0].length-1; j++)
			{
				if(map[i][j]==HAZARD)
					System.out.println("(("+(i-1)+","+(j-1)+"))");
			}
		}

		// ��� ����
		for(int i=0;i<finds.size();i++)
		{
			// Ž������ �޾ƿ���
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			// �湮�� Ž�������� �ƴ� ���
			if(visitedFinds(fx, fy)>0)
			{
				if(create(x, y, fx, fy)==ERROR)
					return ;
			
				for(int j=1; j<part_path.size(); j++)
					path.add(part_path.get(j));
			
				x=fx; y=fy;
			}
		}
		
		//// path ���
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
	}
	
	// ��� �缳���ϴ� �Լ�
	public void updatePath(Position xy, int index){
		
		int x, y, fx, fy, now_xy, num=0;
		
		// ���� ��ġ �޾ƿ���
		x=xy.getX();
		y=xy.getY();
		
		// Ž�� ���� Ž������
		ArrayList<Position> un_finds=new ArrayList<Position>();
		
		// Map ��ü ����
		Map p_map=new Map();
				
		// update�� ���� �޾ƿ���
		map = p_map.getMap(1);
		
		if(index==0)
		{
			for(now_xy=0;now_xy<path.size();now_xy++)
			{
				if( x==path.get(now_xy).getX() && y==path.get(now_xy).getY())
					break;
			}
		}
		else
		{
			now_xy=index;
			path.remove(now_xy);
			path.add(now_xy, xy);
		}
		
		// ���� ��ġ ������ ��� �����, Ž�� ���� Ž������ un_finds�� ����
		now_xy++;
		num=path.size()-now_xy;
		for(int i=0; i<num; i++)
		{
			for(int j=0; j<finds.size(); j++)
			{
				if(finds.get(j).getX()==path.get(now_xy).getX() && finds.get(j).getY()==path.get(now_xy).getY())
					un_finds.add(new Position(finds.get(j).getX(), finds.get(j).getY()));
			}
			path.remove(now_xy);
		}
		
		// ���� ��ġ���� ��� ����
		for(int i=0;i<un_finds.size();i++)
		{
			// Ž������ �޾ƿ���
			fx=un_finds.get(i).getX()+1;
			fy=un_finds.get(i).getY()+1;
			
			// �湮�� Ž�������� �ƴ� ���
			if(visitedFinds(fx, fy)>0)
			{
				if(create(x, y, fx, fy)==ERROR)
					return ;
			
				for(int j=1; j<part_path.size(); j++)
					path.add(part_path.get(j));
			
				x=fx; y=fy;
			}
		}
		
		//// path ���
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
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
		part_path = new ArrayList<Position>();
		
		// ������� ����
		part_path.add(new Position(x-1, y-1));
		n_path[x][y]=end++;
		
		while(true)
		{
			
			if(Math.abs(fx-x)<Math.abs(fy-y)) // find ���� �� ������ ���� �������� 
			{
				if(fy>y && n_path[x][y+1]==0)
					y++;
				else if(fy<y && n_path[x][y-1]==0)
					y--;
				else if(fx>=x && n_path[x+1][y]==0)
					x++;
				else if(fx<=x && n_path[x-1][y]==0)
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
				else if(fy>=y && n_path[x][y+1]==0)
					y++;
				else if(fy<=y && n_path[x][y-1]==0)
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
				MapForm.errorMessage("Fault Move"+"\n3�ʵ� ���α׷��� ����˴ϴ�.");
			
			if(end==99) // ����̵� Ƚ���� 99�̻��� �Ǹ� ������������ �ѷ��ο��ٰ� �Ǵ��Ͽ� �����Ѵ�.
				MapForm.errorMessage("No Path(������������ �ѷ�����)"+"\n3�ʵ� ���α׷��� ����˴ϴ�.");
			
			
			move = true; // �ʱ�ȭ
			
			System.out.println(" (( "+(x-1)+","+(y-1)+" )) ");
			
			// �� ���ƿ� ��� �ߺ� �����ϰ� �ƴ� ��� �������
			if(overlapPath(x-1, y-1)>0)
				part_path.add(new Position(x-1, y-1)); // ��� ����
			
			// �湮 ���� ����
			n_path[x][y]=end++;
			
			if(fx==x && fy==y) // Ž������ ������ ���
				break;
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
	
	// �ٸ� Ž�������� �湮�ϴ� �߿� �湮�� Ž�������� ��� �Ѿ���� �ϱ����� �Լ�
	private int visitedFinds(int fx, int fy){
		for(int i=0; i<path.size(); i++)
		{
			if((fx-1)==path.get(i).getX() && (fy-1)==path.get(i).getY())
				return 0;
		}
		return 1;
	}
	
	// �� ���� �� �� ��� �ִ� ���
	private int overlapPath(int x, int y){
		int first=-1;
		
		for(int i=0; i<part_path.size(); i++)
		{
			if(part_path.get(i).getX()==x && part_path.get(i).getY()==y)
				first=i;
		}
		
		if(first<0)
			return 1; // ���� ���

		for(int i=part_path.size()-1; i>first; i--)
			part_path.remove(i);
		
		return 0;
		
	}
}