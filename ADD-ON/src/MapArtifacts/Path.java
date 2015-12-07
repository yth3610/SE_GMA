package MapArtifacts;

import java.util.*;
import Interface.Finals;
import Interface.MapForm;

public class Path implements Finals{
	
	private static ArrayList<Position> path; // 경로저장
	private static ArrayList<Position> finds; // 탐색 지점
	private ArrayList<Position> part_path; // 부분 경로
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
		p_map.updateMap("Start", new Position(start_x, start_y));
		for(int i=0; i<finds.size(); i++)
			p_map.updateMap("Find", finds.get(i));
		
		// 지도 받아오기
		map = p_map.getMap(1);

		// 시작지점 받아오기
		x=start_x+1; y=start_y+1;
		
		// 시작지점 저장
		path.add(new Position(x-1, y-1));
		
		//// 위험지역 출력
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
			
			// 방문한 탐색지점이 아닌 경우
			if(visitedFinds(fx, fy)>0)
			{
				if(create(x, y, fx, fy)==ERROR)
					return ;
			
				for(int j=1; j<part_path.size(); j++)
					path.add(part_path.get(j));
			
				x=fx; y=fy;
			}
		}
		
		//// path 출력
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
	}
	
	// 경로 재설정하는 함수
	public void updatePath(Position xy, int index){
		
		int x, y, fx, fy, now_xy, num=0;
		
		// 현재 위치 받아오기
		x=xy.getX();
		y=xy.getY();
		
		// 탐색 안한 탐색지점
		ArrayList<Position> un_finds=new ArrayList<Position>();
		
		// Map 객체 생성
		Map p_map=new Map();
				
		// update된 지도 받아오기
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
		
		// 현재 위치 이후의 경로 지우기, 탐색 안한 탐색지점 un_finds에 저장
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
		
		// 현재 위치부터 경로 생성
		for(int i=0;i<un_finds.size();i++)
		{
			// 탐색지점 받아오기
			fx=un_finds.get(i).getX()+1;
			fy=un_finds.get(i).getY()+1;
			
			// 방문한 탐색지점이 아닌 경우
			if(visitedFinds(fx, fy)>0)
			{
				if(create(x, y, fx, fy)==ERROR)
					return ;
			
				for(int j=1; j<part_path.size(); j++)
					path.add(part_path.get(j));
			
				x=fx; y=fy;
			}
		}
		
		//// path 출력
		for(int i=0;i<path.size(); i++)
			System.out.println(path.get(i).toString());
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
		part_path = new ArrayList<Position>();
		
		// 출발지점 저장
		part_path.add(new Position(x-1, y-1));
		n_path[x][y]=end++;
		
		while(true)
		{
			
			if(Math.abs(fx-x)<Math.abs(fy-y)) // find 방향 중 가보지 않은 방향으로 
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
				MapForm.errorMessage("Fault Move"+"\n3초뒤 프로그램이 종료됩니다.");
			
			if(end==99) // 경로이동 횟수가 99이상이 되면 위험지역으로 둘러싸였다고 판단하여 종료한다.
				MapForm.errorMessage("No Path(위험지역으로 둘러싸임)"+"\n3초뒤 프로그램이 종료됩니다.");
			
			
			move = true; // 초기화
			
			System.out.println(" (( "+(x-1)+","+(y-1)+" )) ");
			
			// 빙 돌아온 경우 중복 제거하고 아닌 경우 경로저장
			if(overlapPath(x-1, y-1)>0)
				part_path.add(new Position(x-1, y-1)); // 경로 저장
			
			// 방문 순서 저장
			n_path[x][y]=end++;
			
			if(fx==x && fy==y) // 탐색지점 도착한 경우
				break;
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
	
	// 다른 탐색지점을 방문하는 중에 방문한 탐색지점일 경우 넘어가도록 하기위한 함수
	private int visitedFinds(int fx, int fy){
		for(int i=0; i<path.size(); i++)
		{
			if((fx-1)==path.get(i).getX() && (fy-1)==path.get(i).getY())
				return 0;
		}
		return 1;
	}
	
	// 한 바퀴 삥 돈 경로 있는 경우
	private int overlapPath(int x, int y){
		int first=-1;
		
		for(int i=0; i<part_path.size(); i++)
		{
			if(part_path.get(i).getX()==x && part_path.get(i).getY()==y)
				first=i;
		}
		
		if(first<0)
			return 1; // 없는 경우

		for(int i=part_path.size()-1; i>first; i--)
			part_path.remove(i);
		
		return 0;
		
	}
}