package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.*;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.Border;

import MapArtifacts.Map;
import MapArtifacts.MapManager;
import MapArtifacts.PathManager;
import MapArtifacts.Position;
import Interface.Finals;
	
class MyFrame extends JFrame{
	private JTextField txmap,txhazard,txstart,txfind;
	private JButton btnset;
	private JSplitPane sp1, sp2, sp3;
	private TextArea txlog;
	private JPanel panelInput, panelMap, panelLog, panelDev;
	private JScrollPane scrollPane;
	private static SimSensor robot;
	private static int width=400;	// �糭 ���� �� �ʺ�
	private static int height=350;	// �糭 ���� �� ����
	private static int x=30, y=30;	// �糭 ���� �� ���� ��� x,y ��ǥ
	private static int robotWsa=50,robotWaa=-100;	// �κ��� ������ ���ϰ� �ִ� ���
	private static int robotSsa=40,robotSaa=100;	// �κ��� ������ ���ϰ� �ִ� ���
	private static int robotEsa=130,robotEaa=100;	// �κ��� ������ ���ϰ� �ִ� ���
	private static int robotNsa=-40,robotNaa=-100;	// �κ��� ������ ���ϰ� �ִ� ���
	private int[][] mapdata= new int[5][5];

	public MyFrame(){
		setSize(750,500);
		setTitle("ADD-ON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener listener = new ButtonListener();
		
//		mapdata[0][0] = 0;
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
		sp1.setDividerLocation(40);	// �ڸ��� ��ġ ���� 40���� ����
		
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // �¿� ����
		sp2.setDividerLocation(250); // �ڸ��� ��ġ �ʺ� 250���� ����		
		
		sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
		sp3.setDividerLocation(300); // �ڸ��� ��ġ ���� 300���� ����
		
		panelInput = new JPanel(); // �Է� ������ ��ġ�ϴ� �ǳ�
		panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border border = BorderFactory.createLineBorder(null);
		panelInput.setBorder(border);
		
		panelInput.add(new JLabel("���� ũ��"));	// �Է°����� ��ġ�� �ǳ�
		txmap = new JTextField(8);	// ����ũ�⸦ �Է¹��� �ؽ�Ʈ�ʵ�
		txmap.addActionListener(listener);
		panelInput.add(txmap);
		panelInput.add(new JLabel("���� ����"));	
		txhazard = new JTextField(8);	// ���������� �Է¹��� �ؽ�Ʈ�ʵ�
		txhazard.addActionListener(listener);
		panelInput.add(txhazard);
		panelInput.add(new JLabel("���� ����"));
		txstart = new JTextField(8);	// ���������� �Է¹��� �ؽ�Ʈ�ʵ�
		txstart.addActionListener(listener);
		panelInput.add(txstart);
		panelInput.add(new JLabel("Ž�� ����"));
		txfind = new JTextField(8);		// Ž�������� �Է¹��� �ؽ�Ʈ�ʵ�
		txfind.addActionListener(listener);
		panelInput.add(txfind);
		btnset = new JButton("set");
		btnset.addActionListener(listener);	// set ��ư�� listener �޾���
		panelInput.add(btnset);
				
		panelLog = new JPanel();	// System Log�� ��ġ�� �ǳ�	
		panelLog.setLayout(new BorderLayout());
		panelLog.add(new JLabel("System Log"),BorderLayout.PAGE_START);
		txlog = new TextArea(15,30);
		scrollPane = new JScrollPane(txlog);
		panelLog.add(txlog,BorderLayout.CENTER);
		
		panelDev = new JPanel(); // ������ ������ ��ġ�� �ǳ�		
		MapComponent mapcomponent = new MapComponent(1, 1);
				
		sp1.setTopComponent(panelInput);	// sp1�� panelInput ��ġ
		sp2.setRightComponent(mapcomponent);	// sp2�� panelMap ��ġ
		sp3.setTopComponent(panelLog);	// sp3 ��ܺο� panelLog ��ġ
		sp3.setBottomComponent(panelDev);	// sp3 �ϴܺο� panelDev ��ġ
		sp2.add(sp3);
		sp1.add(sp2);	
		getContentPane().add(sp1);
		
		setVisible(true);
	}
	
	public void setComponent(MapComponent mapcomponent) {
		sp2.setRightComponent(mapcomponent);
	}
	
	public SimSensor getRobot() {
	      return this.robot;
	}
	
	class MapComponent extends JComponent implements Finals{
		int mapx = 1, mapy = 1;
		
		public MapComponent(int x, int y) {
			this.mapx = x;
			this.mapy = y;
		}
		
		public void paint(Graphics g){
			g.drawRect(x,y,width,height);
			/*
			g.setColor(Color.MAGENTA);
			g.fillArc(90,90,50,50,robotEsa,robotEaa);
			g.fillArc(90, 60, 50, 50, robotSsa, robotSaa);
			g.fillArc(90, 120,50,50,robotWsa,robotWaa);
			g.fillArc(90, 150, 50, 50, robotNsa, robotNaa);
			*/
			int widthmap=width/mapx;	// ������ �ʺ�
			int heightmap=height/mapy;	// ������ ����
			int tempx=widthmap;
			int tempy=heightmap;
			
			for(int i=0;i<mapx-1;i++){	// x�� ������ �׸���
				g.drawLine(30+tempx,y,30+tempx,y+height);
				tempx=tempx+widthmap;
			}
			
			for(int i=0;i<mapy-1;i++){	// y�� ������ �׸���			
				g.drawLine(x,30+tempy,x+width,30+tempy);
				tempy=tempy+heightmap;
			}
			
			for(int i=0;i<=mapx;i++){
				for(int j=0;j<=mapy;j++){
					if(mapdata[i][j]==COLORBLOB){	// ��ǥ���� ColorBlob�� ���
						g.setColor(Color.BLUE);
						g.fillOval(15+widthmap*i,15+heightmap*j,30,30);	// �Ķ����� �׸���
					}
					if(mapdata[i][j]==HAZARD){		// ��ǥ���� hazard�� ���
						g.setColor(Color.RED);
						g.fillOval(15+widthmap*i,15+heightmap*j,30,30);	// �������� �׸���
					}
					if(mapdata[i][j]==START){	// Ž�� ������ �κ��� ��ġ��Ų��
						g.setColor(Color.MAGENTA);
						g.fillArc(15+widthmap*i,15+heightmap*j,50,50,robotEsa,robotEaa);
					}
				}
			}
					
		}		
	}
	
	// set ��ư�� ������ ��� �̺�Ʈ
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==txmap || e.getSource()==txhazard ||
					e.getSource()==txstart || e.getSource()==txfind ||
					e.getSource()==btnset){		
				
				ArrayList<Position> mappositionList = new ArrayList<>();	// �� ��ǥ�� �����ϴ� ����Ʈ
				ArrayList<Position> startpositionList = new ArrayList<>();	// �������� �·Ḧ �����ϴ� ����Ʈ
				ArrayList<Position> hazardpositionList = new ArrayList<>();	// �������� ��ǥ�� �����ϴ� ����Ʈ
				ArrayList<Position> findpositionList = new ArrayList<>();	// Ž������ �·Ḧ �����ϴ� ����Ʈ
								 
			    StringTokenizer sthazard = new StringTokenizer(txhazard.getText());
			    StringTokenizer stfind = new StringTokenizer(txfind.getText());
			    StringTokenizer ststart = new StringTokenizer(txstart.getText());
			    StringTokenizer stmap = new StringTokenizer(txmap.getText());
			    
			    String tmp = stmap.nextToken();
			    int mapx = Integer.valueOf(tmp.substring(0,1));
			    int mapy = Integer.valueOf(tmp.substring(2,tmp.length()));
			    mappositionList.add(new Position(mapx,mapy));	
			    			    
			    tmp = ststart.nextToken();
			    int startx = Integer.valueOf(tmp.substring(0,1));
			    int starty = Integer.valueOf(tmp.substring(2,tmp.length()));
			    startpositionList.add(new Position(startx,starty));
			    
			    while(stfind.hasMoreTokens()) {
			    	tmp = stfind.nextToken();
			    	int findx = Integer.valueOf(tmp.substring(0,1));
			    	int findy = Integer.valueOf(tmp.substring(2,tmp.length()));
			    	findpositionList.add(new Position(findx,findy));			    	
			    }
			    
			    while(sthazard.hasMoreTokens()) {
			        tmp = sthazard.nextToken();
			        int hazardx = Integer.valueOf(tmp.substring(0, 1));
			        int hazardy = Integer.valueOf(tmp.substring(2, tmp.length()));
			        hazardpositionList.add(new Position(hazardx, hazardy));
			    }
			    			   
			    MapManager map = new MapManager();
			    PathManager path = new PathManager();
			    map.create(mapx,mapy,hazardpositionList);
			    path.createPath(startx, starty, findpositionList);
			    Map mapmap = new Map();		
				mapdata= mapmap.getMap(0);
				/*
				for(int i=0; i<mapdata.length; i++)
				{
					for(int j=0; j<mapdata[0].length; j++)
						System.out.print(mapdata[i][j]+" ");
					System.out.println();	
					
				}
				*/
			    MapComponent mapcomponent = new MapComponent(mappositionList.get(0).getX(), mappositionList.get(0).getY());
			    MapForm.f.setComponent(mapcomponent);
			 
				txlog.append("���� ũ��"+mappositionList+"\n���� ����"+hazardpositionList+
						"\n���� ����"+startpositionList+"\nŽ�� ����"+findpositionList+"\n");
				// system log�� �Է¹��� ������ ���				
			}			
		}		
	}	 
}

public class MapForm  {
	   static MyFrame f;
	   
	   public static void main(String[] args) {
	      f = new MyFrame();
	   }
	   
	   public static SimSensor getRobot() {
	      return f.getRobot();
	   }
}