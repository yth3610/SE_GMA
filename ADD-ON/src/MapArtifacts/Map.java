package MapArtifacts;

import java.util.ArrayList;
import java.util.Random;
import Interface.Finals;

public class Map implements Finals {

	private static int[][] map; // ��������
	private static int[][] draw_map; // ��������
	private static int x, y;

	private ArrayList<Position> hazard;

	public static final int OK = 9;
	public static final int ERROR = 999;

	// ���� �ʱ�ȭ �ϴ� �Լ� ( x : ���� x�� ũ��, y : ���� y�� ũ��, hazard : �������� )
	public void create(int x, int y, ArrayList<Position> hazard){
		
		// �ʱ�ȭ
		this.x=x;
		this.y=y;
		this.hazard=hazard;
		
		Random rand = new Random();
		map=new int[x+3][y+3];
		draw_map=new int[x+1][y+1];
		
		for(int i=0; i<x+3; i++) // �Է� ũ�⸦ �������� map�� ���� ������ �ʱ�ȭ
		{
			for(int j=0; j<y+3; j++)
			{
				if(i==0 || i==x+2 || j==0 || j==y+2)
					map[i][j]=HAZARD;
				else
				{
					map[i][j]=rand.nextInt(10)+5;
					draw_map[i-1][j-1]=rand.nextInt(10)+5;
				}
			}
		}
		
		// �Է� ���� �������� ǥ���ϱ�
		for(int i=0; i<hazard.size(); i++)
		{
			draw_map[hazard.get(i).getX()][hazard.get(i).getY()]=HAZARD;
			map[hazard.get(i).getX()+1][hazard.get(i).getY()+1]=HAZARD;
		}
	}
	
	// ���� �缳�� �ϴ� �Լ� (type : ���� (hazard, color blob�߿� ������), xy : ��ǥ)
	public void update(String type, Position xy){
		
		Path m_path = new Path();
		
		// type�� ���� ���� Update
		switch(type){
			case "Hazard" :
				map[xy.getX()+1][xy.getY()+1]=HAZARD; 
				m_path.updatePath(xy);
				break;
			case "Color" :
				map[xy.getX()+1][xy.getY()+1]=COLORBLOB;
				break;
			case "Start" :
				map[xy.getX()+1][xy.getY()+1]=START;
				break;
			case "Find" :
				map[xy.getX()+1][xy.getY()+1]=FIND;
				break;
		}
		
		if(confirm()==ERROR)
			; // �ѷ����� ���
	}
	
	// ���� ��ȯ �ϴ� �Լ�
	public int[][] getMap(int i){
		if(i==0)
			return draw_map;
		else
			return map;
	}
	
	// ��������, Ž�������� ������������ �ѷ����� ���
	public int confirm(){
		for(int i=1; i<x+1; i++) 
		{
			for(int j=1; j<y+1; j++)
			{
				if(map[i][j]==FIND || map[i][j]==START)
				{
					if(map[i+1][j]==HAZARD && map[i-1][j]==HAZARD && map[i][j+1]==HAZARD && map[i][j-1]==HAZARD)
						return ERROR;
				}
			}
		}
		return OK;
	}
}

