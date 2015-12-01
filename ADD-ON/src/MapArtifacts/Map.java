package MapArtifacts;

import java.util.Random;

public class Map {

	private static int[][] map; // 지도저장
	
	// 지도 초기화 하는 함수 (int x, int y, arraylist hazard,) ( x : 지도 x축 크기, y : 지도 y축 크기 )
	public void create(){
		
		int x=5, y=5;
		Random rand = new Random();
		map=new int[x+2][y+2];
		
		for(int i=0; i<x+2; i++) // 입력 크기를 바탕으로 map을 임의 값으로 초기화
		{
			for(int j=0; j<y+2; j++)
			{
				if(i==0 || i==x+1 || j==0 || j==y+1)
					map[i][j]=3;
				else
					map[i][j]=rand.nextInt(10)+1;
			}
		}
		
		// 입력 받은 위험지역 표시하기
		
		/* for(int i=0; i<hazard.size(); i++)
		 *		map[hazard.get(i).getX()][hazard.get(i).getY()]=2;
		 */
		// 둥글게 감싸진 부분, 갈 수 없는 부분 해결방법...????
	}
	
	// 지도 재설정 하는 함수 (type : 종류 (hazard, color blob중에 뭐인지), xy : 좌표)
	public void update(String type, Position xy){
		
		// type에 따라 지도 Update
		switch(type){
			case "Hazard" :
				map[xy.getX()][xy.getY()]=3;
				break;
			case "Color" :
				map[xy.getX()][xy.getY()]=2;
				break;
			case "Start" :
				map[xy.getX()][xy.getY()]=0;
				break;
			case "Find" :
				map[xy.getX()][xy.getY()]=1;
				break;
		}
	}
	
	// 지도 반환 하는 함수
	public int[][] getMap(){
		return map;
	}
}

