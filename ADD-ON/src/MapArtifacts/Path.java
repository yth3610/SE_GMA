package MapArtifacts;

import java.util.*;
import Interface.Finals;

public class Path implements Finals{
	
	private static ArrayList<Position> path; // 경로저장
	private static ArrayList<Position> finds; // 탐색 지점
	
	private ArrayList<Position> path2; // 부분 경로
	private int[][] map; // 지도 저장
	
	public static final int OK = 9;
	public static final int ERROR = 999;
	
	// 경로 초기화 하는 함수   (start_xy : 출발지점 , finds : 탐색지점)
	public void createPath(int start_x, int start_y, ArrayList<Position> finds){
		
		int x, y, fx, fy;
		
		// 탐색지점 초기화
		this.finds=finds;
		
		// 경로 저장 변수 생성
		path = new ArrayList<Position>();
		
		// Map 객체 생성
		Map p_map=new Map();

		// 시작 지점, 탐색 지점 지도에 저장
		p_map.update("Start", new Position(start_x, start_y));
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", finds.get(i));
		
		// 지도 받아오기
		map = p_map.getMap(1);

		// 시작지점 받아오기
		x=start_x+1; y=start_y+1;
		
		// 시작지점 저장
		path.add(new Position(x-1, y-1));
		
		// 위험지역 출력
		for(int i=1;i<map.length-1;i++)
		{
			for(int j=1; j<map[0].length-1; j++)
			{
				if(map[i][j]==HAZARD)
					System.out.println("(("+(i-1)+","+(j-1)+"))");
			}
		}

		// 경로 생성
		for(int i=0;i<finds.size();i++)
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
			System.out.println(path.get(i).toString());
	}
	
	// 경로 재설정하는 함수
	public void updatePath(Position hazard_xy){
		
		int x, y, fx, fy, hazard, num=0;
		
		// 발견된 위험지역 위치 받아오기
		x=hazard_xy.getX();
		y=hazard_xy.getY();
		
		// 탐색 안한 탐색지점
		ArrayList<Position> un_finds=new ArrayList<Position>();
		
		// Map 객체 생성
		Map p_map=new Map();
				
		// update된 지도 받아오기
		map = p_map.getMap(1);
		
		// 위치 이후의 경로 지우기
		for(hazard=0;hazard<path.size();hazard++)
		{
			if( x==path.get(hazard).getX() && y==path.get(hazard).getY())
				break;
		}
		num=path.size()-hazard;
		for(int j=0; j<num; j++)
		{
			for(int q=0; q<finds.size(); q++)
			{
				if(finds.get(q).getX()==path.get(hazard).getX() && finds.get(q).getY()==path.get(hazard).getY())
					un_finds.add(new Position(finds.get(q).getX(), finds.get(q).getY()));
			}
			path.remove(hazard);
		}
		
		// path의 마지막 지점 저장
		x=path.get(path.size()-1).getX()+1;
		y=path.get(path.size()-1).getY()+1;
		
		// path의 마지막 지점부터 경로 생성
		for(int i=0;i<un_finds.size();i++)
		{
			// 탐색지점 받아오기
			fx=un_finds.get(i).getX()+1;
			fy=un_finds.get(i).getY()+1;
			
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
	private int create(int x, int y, int fx, int fy)
	{
		int end=1;
		boolean move = true;
	
		// n_path 초기화
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
		
		// 초기화
		path2 = new ArrayList<Position>();
		
		while(true)
		{
			
			if(Math.abs(fx-x)<Math.abs(fy-y)) // find 방향 중 가보지 않은 방향으로 
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
			
			if(end==ERROR) // 경로이동 횟수가 999이상이 되면 위험지역으로 둘러싸였다고 판단하여 종료한다.
			{
				System.out.println("BLOCKED ERROR");
				return ERROR;
			}
			
			// 어떤 탐색지점을 목표로 움직이다가 다른 탐색지점에 도착하는 경우????
			// 빙 돌아온 경우 중복 제거하기
		}
		return OK;
	}
	
	// 가장 예전에 간 방향 return	
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