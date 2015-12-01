package MapArtifacts;

import java.util.*;

public class Path {
	
	private static ArrayList<Position> path; // 경로저장 (방문하면 삭제하도록???)
	private static ArrayList<Position> finds; // 탐색 지점 (방문하면 삭제하도록???)
	private static Position start_xy; // 시작 지점
	private ArrayList<Position> path2; // 임시
	
	private int[][] map; // getMap()해서 지도 저장
	
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int NORTH = 4;
	
	public static final int OK = 9;
	public static final int ERROR = 999;

	public static void main(String[] args)
	{
		Map map=new Map();
		map.create();
		Path pp = new Path();
		pp.createPath();
	}
	
	// 경로 초기화 하는 함수   (Position start_xy, ArrayList<Position> finds)
	public void createPath(){

		//this.start_xy=start_xy;
		//this.finds=finds;

		// 임의로 설정해 놓은 변수들
		finds=new ArrayList<Position>();
		finds.add(new Position(2,3));
		finds.add(new Position(2,4));
		finds.add(new Position(4,3));
		start_xy = new Position(0,0);
		
		// 경로 저장 변수
		path = new ArrayList<Position>();

		// Map 객체 생성
		Map p_map=new Map();

		// 시작 지점, 탐색 지점 지도에 저장
		p_map.update("Start", start_xy);
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", finds.get(i));
		
		// 지도 받아오기
		map = p_map.getMap();

		int x, y, fx, fy;
		
		// 시작지점 받아오기
		x=start_xy.getX()+1; y=start_xy.getY()+1;
		// 시작지점 저장
		path.add(new Position(x-1, y-1));

		map[3][4]=2;
		map[3][5]=2;
		map[5][4]=2;
		
		// 위험지역 출력
		for(int i=1;i<6;i++)
		{
			for(int j=1; j<6; j++)
			{
				if(map[i][j]==3)
					System.out.println("(("+(i-1)+","+(j-1)+"))");
			}
		}

		for(int i=0;i<finds.size();i++)  // 위험지역으로 둘러싸인 경우 아직 해결 못함...
		{
			// 탐색지점 받아오기
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			if(create(x, y, fx, fy)==ERROR)
				return ;
			
			for(int j=0; j<path2.size(); j++)
				path.add(path2.get(j));
			
			x=fx; y=fy;
		}
		
		// path 출력
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toPositionString());
	}
	
	// 경로 재설정하는 함수 (Position start_xy, ArrayList<Position> finds)??
	public void updatePath(){
		// 현재위치 받아오기
		int x=3, y=2, p=0, fx, fy;
		boolean a = false;
		
		for(int i=0; i<path.size(); i++)
		{
			if(("("+x+","+y+")").equalsIgnoreCase(path.get(i).getPositionString()) && !a)
			{	
				p=i+1;
				a=true;
			}
			else if(a)
				path.remove(p);
		}
		
		for(int i=0;i<finds.size();i++)  // 위험지역으로 둘러싸인 경우 아직 해결 못함...
		{
			// 탐색지점 받아오기
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			if(create(x, y, fx, fy)==ERROR)
				return ;
			
			for(int j=0; j<path2.size(); j++)
				path.add(path2.get(j));
			
			x=fx; y=fy;
		}
	}
	
	// 경로 전달하는 함수
	public ArrayList<Position> getPath(){
		return path;
	}
	
	// x, y 부터 fx, fy까지 경로 구하기
	public int create(int x, int y, int fx, int fy)
	{
		int end=1;
		boolean move = true;
	
		// n_path 초기화
		int [][]n_path=new int[map.length][map[0].length];
		for(int i=0; i<n_path.length; i++)
		{
			for(int j=0; j<n_path[0].length; j++)
			{
				if(map[i][j]==3)
					n_path[i][j]=ERROR;
				else
					n_path[i][j]=0;
			}
		}
		
		// 초기화
		path2 = new ArrayList<Position>();
		
		while(true)
		{
			
			if(Math.abs(fx-x)<Math.abs(fy-y)) // find 방향 중 가보지 않은 방향으로 
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
			
			if(!move) // 가장 예전에 간 방향으로
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

			if(!move) // 움직임에 실패한 경우
			{
				System.out.println("ERROR");
				return ERROR;
			}
			
			move = true; // 초기화
			path2.add(new Position(x-1, y-1)); // 경로 저장
			
			// 방문 순서 저장
			n_path[x][y]=end++;
			
			if(fx==x && fy==y) // 탐색지점 도착한 경우
			{
				x=fx;
				y=fy;
				break;
			}
			
			// 다른 탐색지점을 지나가는 경우??
		}
		return OK;
	}
	
	// 가장 예전에 간 방향 return	
	public int oldestMove(int x, int y, int[][] n_path)
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

