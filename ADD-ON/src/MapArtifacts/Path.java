package MapArtifacts;

import java.util.*;

public class Path {
	
	private static ArrayList<Position> path; // 경로저장
	private static ArrayList<Position> finds; // 탐색 지점
	private static Position start_xy; // 시작 지점

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
		int[][] map = p_map.getMap();
		
		int x, y;
		int fx, fy;
		boolean move = true;
		
		// path에 시작지점 추가
		path.add(new Position(start_xy.getX(),start_xy.getY()));
		
		// 시작지점 받아오기
		x=start_xy.getX()+1; y=start_xy.getY()+1;

		for(int i=0;i<finds.size();i++)
		{
			fx=finds.get(i).getX()+1;
			fy=finds.get(i).getY()+1;
			
			// 때떄로 무한 루프에 빠진다. 이전 이동 값을 몰라서...
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
				path.add(new Position(x-1, y-1)); // 이동한 경로 저장
				
				if(fx==x && fy==y) // 탐색지점 도착한 경우
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
	
	// 경로 재설정하는 함수
	public void updatePath(){
		
	}
	
	// 경로 전달하는 함수
	public ArrayList<Position> getPath(){
		return path;
	}

}

