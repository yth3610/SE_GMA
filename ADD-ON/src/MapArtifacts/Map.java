package MapArtifacts;

import java.util.Random;

public class Map {

	private static int[][] map; // ��������
	
	// ���� �ʱ�ȭ �ϴ� �Լ� (int x, int y, arraylist hazard,) ( x : ���� x�� ũ��, y : ���� y�� ũ�� )
	public void create(){
		
		int x=5, y=5;
		Random rand = new Random();
		map=new int[x+2][y+2];
		
		for(int i=0; i<x+2; i++) // �Է� ũ�⸦ �������� map�� ���� ������ �ʱ�ȭ
		{
			for(int j=0; j<y+2; j++)
			{
				if(i==0 || i==x+1 || j==0 || j==y+1)
					map[i][j]=3;
				else
					map[i][j]=rand.nextInt(10)+1;
			}
		}
		
		// �Է� ���� �������� ǥ���ϱ�
		
		/* for(int i=0; i<hazard.size(); i++)
		 *		map[hazard.get(i).getX()][hazard.get(i).getY()]=2;
		 */
		// �ձ۰� ������ �κ�, �� �� ���� �κ� �ذ���...????
	}
	
	// ���� �缳�� �ϴ� �Լ� (type : ���� (hazard, color blob�߿� ������), xy : ��ǥ)
	public void update(String type, Position xy){
		
		// type�� ���� ���� Update
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
	
	// ���� ��ȯ �ϴ� �Լ�
	public int[][] getMap(){
		return map;
	}
}

