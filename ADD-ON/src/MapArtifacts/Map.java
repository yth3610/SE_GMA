package MapArtifacts;

import java.util.ArrayList;
import java.util.Random;
import Interface.Finals;

public class Map implements Finals {

	private static int[][] path_map; // 지도저장
	private static int[][] map; // 지도저장
	private static int x, y;

	private ArrayList<Position> hazard;

	public static final int OK = 9;
	public static final int ERROR = 999;

	// 지도 초기화 하는 함수 ( x : 지도 x축 크기, y : 지도 y축 크기, hazard : 위험지역 )
	public void create(int x, int y, ArrayList<Position> hazard){
		
		// 초기화
		this.x=x;
		this.y=y;
		this.hazard=hazard;
		
		Random rand = new Random();
		path_map=new int[x+3][y+3];
		map=new int[x+1][y+1];
		
		for(int i=0; i<x+3; i++) // 입력 크기를 바탕으로 map을 임의 값으로 초기화
		{
			for(int j=0; j<y+3; j++)
			{
				if(i==0 || i==x+2 || j==0 || j==y+2)
					path_map[i][j]=HAZARD;
				else
				{
					path_map[i][j]=rand.nextInt(10)+5;
					map[i-1][j-1]=rand.nextInt(10)+5;
				}
			}
		}
		
		// 입력 받은 위험지역 표시하기
		for(int i=0; i<hazard.size(); i++)
		{
			map[hazard.get(i).getX()][hazard.get(i).getY()]=HAZARD;
			path_map[hazard.get(i).getX()+1][hazard.get(i).getY()+1]=HAZARD;
		}
	}
	
	// 지도 재설정 하는 함수 (type : 종류 (hazard, color blob중에 뭐인지), xy : 좌표)
	public void update(String type, Position xy){
		
		Path m_path = new Path();
		
		// type에 따라 지도 Update
		switch(type){
			case "Hazard" :
				path_map[xy.getX()+1][xy.getY()+1]=HAZARD; 
				map[xy.getX()][xy.getY()]=HAZARD; 
				m_path.updatePath(xy);
				break;
			case "Color" :
				path_map[xy.getX()+1][xy.getY()+1]=COLORBLOB;
				map[xy.getX()][xy.getY()]=COLORBLOB;
				break;
			case "Start" :
				path_map[xy.getX()+1][xy.getY()+1]=START;
				map[xy.getX()][xy.getY()]=START;
				break;
			case "Find" :
				path_map[xy.getX()+1][xy.getY()+1]=FIND;
				map[xy.getX()][xy.getY()]=FIND;
				break;
		}
		
		// 둘러싸인 경우 system log에 뜨도록 알리기
		if(confirm()==ERROR)
			System.out.println("no path(surrounded hazard)");
	}
	
	// 지도 반환 하는 함수 (0 : 일반지도 1 : 경로용)
	public int[][] getMap(int i){
		if(i==0)
			return map;
		else
			return path_map;
	}
	
	// 시작지점, 탐색지점이 위험지점으로 둘러싸인 경우
	public int confirm(){
		for(int i=1; i<x+2; i++) 
		{
			for(int j=1; j<y+2; j++)
			{
				if(path_map[i][j]==FIND || path_map[i][j]==START)
				{
					if(path_map[i+1][j]==HAZARD && path_map[i-1][j]==HAZARD && path_map[i][j+1]==HAZARD && path_map[i][j-1]==HAZARD)
						return ERROR;
				}
			}
		}
		return OK;
	}
}
