package MapArtifacts;

import java.util.Random;

public class Map {

	private int[][] map; // ��������
	
	// ���� �ʱ�ȭ �ϴ� �Լ� ( x : ���� x�� ũ��, y : ���� y�� ũ�� )
	public void create(int x, int y){
		
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
		// �ձ۰� ������ �κ�, �� �� ���� �κ� �ذ���...????
	}
	
	// ���� �缳�� �ϴ� �Լ� (type : ���� (hazard, color blob�߿� ������), x : ��ġ x��ǥ, y : ��ġ y��ǥ)
	public void update(String type, int x, int y){
		
		// type�� ���� ���� Update
		switch(type){
			case "Hazard" :
				map[x][y]=3;
				break;
			case "Color" :
				map[x][y]=2;
				break;
			case "Start" :
				map[x][y]=0;
				break;
			case "Find" :
				map[x][y]=1;
				break;
		}
	}
	
	// ���� ��ȯ �ϴ� �Լ�
	public int[][] getMap(){
		return map;
	}
}

