package MapArtifacts;

import java.util.*;

public class Path {
	
	private ArrayList<Position> path; // 경로저장 ( 클래스 다이어그램에 속성 추가하기 )
	private Position path_xy; // 이동 경로 저장 객체

	private ArrayList<Position> finds; // 탐색 지점
	private Position start_xy; // 시작 지점

	// 경로 초기화 하는 함수   (Position start_xy, ArrayList<Position> finds)
	public void createPath(){

		//this.start_xy=start_xy;
		//this.finds=finds;

		// 임의로 설정해 놓은 변수들
		Position find_xy = new Position(2,3);
		finds=new ArrayList<Position>();
		finds.add(find_xy);
		find_xy.setPosition(2,4);
		finds.add(find_xy);
		find_xy.setPosition(4,3);
		finds.add(find_xy);
			
		start_xy = new Position(0,0);
		
		// 경로 저장 변수
		path = new ArrayList<Position>();

		// Map 객체 생성
		Map p_map=new Map();

		// 시작 지점, 탐색 지점 지도에 저장
		p_map.update("Start", start_xy);
		for(int i=0; i<finds.size(); i++)
			p_map.update("Find", path.get(i));

		// 지도 받아오기
		int[][] map = p_map.getMap();
		
		int x, y;
		int fx, fy;
		boolean move = true;
		
		// path에 시작지점 추가
		path_xy = new Position(start_xy.getX(),start_xy.getY());
		path.add(path_xy);
		
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
				path_xy.setPosition(x-1, y-1);
				path.add(path_xy); // 이동한 경로 저장
				
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
		
	}
	
	// 경로 재설정하는 함수
	public void updatePath(){
		
	}
	
	// 경로 전달하는 함수
	public ArrayList<Position> getPath(){
		return path;
	}

}

