package MapArtifacts;

import java.util.ArrayList;
import java.util.Random;
import Interface.Finals;

public class Map implements Finals {

	private static int[][] path_map; // ��������
	private static int[][] map; // ��������
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
		path_map=new int[x+3][y+3];
		map=new int[x+1][y+1];
		
		for(int i=0; i<x+3; i++) // �Է� ũ�⸦ �������� map�� ���� ������ �ʱ�ȭ
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
		
		// �Է� ���� �������� ǥ���ϱ�
		for(int i=0; i<hazard.size(); i++)
		{
			map[hazard.get(i).getX()][hazard.get(i).getY()]=HAZARD;
			path_map[hazard.get(i).getX()+1][hazard.get(i).getY()+1]=HAZARD;
		}
	}
	
	// ���� �缳�� �ϴ� �Լ� (type : ���� (hazard, color blob�߿� ������), xy : ��ǥ)
	public void update(String type, Position xy){
		
		Path m_path = new Path();
		
		// type�� ���� ���� Update
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
		
		// �ѷ����� ��� system log�� �ߵ��� �˸���
		if(confirm()==ERROR)
			System.out.println("no path(surrounded hazard)");
	}
	
	// ���� ��ȯ �ϴ� �Լ� (0 : �Ϲ����� 1 : ��ο�)
	public int[][] getMap(int i){
		if(i==0)
			return map;
		else
			return path_map;
	}
	
	// ��������, Ž�������� ������������ �ѷ����� ���
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
