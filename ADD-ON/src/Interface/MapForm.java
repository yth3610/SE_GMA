package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.*;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BoxLayout;

import Foundation.Finals;
import Foundation.Position;
import MapArtifacts.Map;
import MapArtifacts.MapManager;
import MapArtifacts.PathManager;
import RobotMovement.RobotPosition;
import Interface.MyFrame.MovementComponent;

class MyFrame extends JFrame{

   private JTextField txmap,txhazard,txstart,txfind;
   private JButton btnset,btnstart;
   private JSplitPane sp1, sp2, sp3;
   private TextArea txlog;
   private JPanel panelInput, panelMap, panelLog, panelDev;
   private JScrollPane scrollPane;
   private static SimSensor robot; 
   private static int width=450;   // �糭 ���� �� �ʺ�
   private static int height=350;   // �糭 ���� �� ����
   private static int x=30, y=30;   // �糭 ���� �� ���� ��� x,y ��ǥ
   private static int robotWsa=50,robotWaa=-100;   // �κ��� ������ ���ϰ� �ִ� ���
   private static int robotSsa=40,robotSaa=100;   // �κ��� ������ ���ϰ� �ִ� ���
   private static int robotEsa=130,robotEaa=100;   // �κ��� ������ ���ϰ� �ִ� ���
   private static int robotNsa=-40,robotNaa=-100;   // �κ��� ������ ���ϰ� �ִ� ���
   private int[][] mapdata = new int[5][5];	// �糭������ ������ ������ ����Ʈ
   private ArrayList<Position> hazardpositionList = new ArrayList<>();   // �������� ��ǥ�� �����ϴ� ����Ʈ
   private ArrayList<Position> findpositionList = new ArrayList<>();   // Ž������ �·Ḧ �����ϴ� ����Ʈ
   private Position startposition, mapposition, robotposition;	// �Է°����� ������ ����Ʈ

   public MyFrame(){
      setSize(800,500);
      setTitle("ADD-ON");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      setListener setlistener = new setListener();
      startListener startlistener = new startListener();
      
      sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
      sp1.setDividerLocation(40);   // �ڸ��� ��ġ ���� 40���� ����
      
      sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // �¿� ����
      sp2.setDividerLocation(250); // �ڸ��� ��ġ �ʺ� 250���� ����      
      
      sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
      sp3.setDividerLocation(350); // �ڸ��� ��ġ ���� 300���� ����
      
      panelInput = new JPanel(); // �Է� ������ ��ġ�ϴ� �ǳ�
      panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
      Border border = BorderFactory.createLineBorder(null);
      panelInput.setBorder(border);
      
      panelInput.add(new JLabel("���� ũ��"));   // �Է°����� ��ġ�� �ǳ�
      txmap = new JTextField(8);   // ����ũ�⸦ �Է¹��� �ؽ�Ʈ�ʵ�
      txmap.addActionListener(setlistener);
      panelInput.add(txmap);
      panelInput.add(new JLabel("���� ����"));   
      txhazard = new JTextField(8);   // ���������� �Է¹��� �ؽ�Ʈ�ʵ�
      txhazard.addActionListener(setlistener);
      panelInput.add(txhazard);
      panelInput.add(new JLabel("���� ����"));
      txstart = new JTextField(8);   // ���������� �Է¹��� �ؽ�Ʈ�ʵ�
      txstart.addActionListener(setlistener);
      panelInput.add(txstart);
      panelInput.add(new JLabel("Ž�� ����"));
      txfind = new JTextField(8);      // Ž�������� �Է¹��� �ؽ�Ʈ�ʵ�
      txfind.addActionListener(setlistener);
      panelInput.add(txfind);
      btnset = new JButton("set");
      btnset.addActionListener(setlistener);   // set ��ư�� setlistener �޾���
      panelInput.add(btnset);
      btnstart = new JButton("start");	// start ��ư�� startlisener �޾���
      btnstart.addActionListener(startlistener);
      panelInput.add(btnstart);
            
      panelLog = new JPanel();   // System Log�� ��ġ�� �ǳ�   
      panelLog.setLayout(new BorderLayout());
      panelLog.add(new JLabel("System Log"),BorderLayout.PAGE_START);
      txlog = new TextArea(15,30);
      scrollPane = new JScrollPane(txlog);
      panelLog.add(txlog,BorderLayout.CENTER);
      
      panelDev = new JPanel(); // ������ ������ ��ġ�� �ǳ�
      panelDev.setLayout(new BoxLayout(panelDev, BoxLayout.Y_AXIS));      
      panelDev.add(new JLabel("2015 SE - Team GMA"));
      panelDev.add(new JLabel("Kim YeoJung, Seo Jung, Cheon SangJin"));
      
      panelMap = new JPanel(); // ��â�� �糭���� ��
            
      sp1.setTopComponent(panelInput);   // sp1�� panelInput ��ġ
      sp2.setRightComponent(panelMap);   // sp2�� panelMap ��ġ
      sp3.setTopComponent(panelLog);   // sp3 ��ܺο� panelLog ��ġ
      sp3.setBottomComponent(panelDev);   // sp3 �ϴܺο� panelDev ��ġ
      sp2.add(sp3);
      sp1.add(sp2);   
      getContentPane().add(sp1);
      
      setVisible(true);
   }
   
   // MapComponent�� �ҷ����� ������
   public void setComponent(MapComponent mapcomponent) {
       sp2.setRightComponent(mapcomponent);
   }
   
   // MovementComponent�� �ҷ����� ������
   public void setComponent(MovementComponent movecomponent){
	   sp2.setRightComponent(movecomponent);
   }

   public SimSensor getRobot() {
         return this.robot;
   }
   
   public Position getMapPosition() {
      return this.mapposition;

   }
   
   // ����ũ��, ��������, Ž������, ���������� �Է¹��� �� �糭�������� �׸��� ������Ʈ
   class MapComponent extends JComponent implements Finals{
      int mapx = 1, mapy = 1;
      
      // ������
      public MapComponent(int x, int y) {
         this.mapx = x;
         this.mapy = y;
      }   

      public void paint(Graphics g){

         g.drawRect(x,y,width,height);  

         int widthmap=width/mapx;   // ������ �ʺ�
         int heightmap=height/mapy;   // ������ ����
         int tempx=widthmap;
         int tempy=heightmap;
         
         for(int i=0;i<mapx-1;i++){   // x�� ������ �׸���
            g.drawLine(30+tempx,y,30+tempx,y+height);
            tempx=tempx+widthmap;
         }
         
         for(int i=0;i<mapy-1;i++){   // y�� ������ �׸���         
            g.drawLine(x,30+tempy,x+width,30+tempy);
            tempy=tempy+heightmap;
         }
         
         for(int i=0;i<=mapx;i++){
            for(int j=0;j<=mapy;j++){
               if(mapdata[i][j]==COLORBLOB){   // ��ǥ���� ColorBlob�� ���
                  g.setColor(Color.BLUE);
                  g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // �Ķ����� �׸���
               }
               if(mapdata[i][j]==HAZARD){      // ��ǥ���� hazard�� ���
                  g.setColor(Color.RED);
                  g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // �������� �׸���
               }
               if(mapdata[i][j]==START){   // ���� ������ �κ��� �׸���
                  g.setColor(Color.MAGENTA);
                  g.fillArc(20+widthmap*i,355-heightmap*j,50,50,robotEsa,robotEaa);
               }
               if(mapdata[i][j]==FIND){		// Ž�� ������ ǥ��
                  g.setColor(Color.ORANGE);
                  g.fillRect(15+widthmap*i,365-heightmap*j,30,30);            
               }
            }
         }
      }
   }
   
   // ����ũ��, ��������, Ž������, ���������� �Է¹��� �� �糭�������� �׸��� ������Ʈ
   class MovementComponent extends JComponent implements Finals{
	   	  int mapx=1, mapy=1;
	   	  
	   	 public MovementComponent(int x, int y) {
	         this.mapx = x;
	         this.mapy = y;
	      } 
	   	
	      public void paint(Graphics g) {
	          g.drawRect(x,y,width,height);
	          
	          int widthmap=width/mapx;   // ������ �ʺ�
	          int heightmap=height/mapy;   // ������ ����
	          int tempx=widthmap;
	          int tempy=heightmap;
	          
	          for(int i=0;i<mapx-1;i++){   // x�� ������ �׸���
	             g.drawLine(30+tempx,y,30+tempx,y+height);
	             tempx=tempx+widthmap;
	          }
	          
	          for(int i=0;i<mapy-1;i++){   // y�� ������ �׸���         
	             g.drawLine(x,30+tempy,x+width,30+tempy);
	             tempy=tempy+heightmap;
	          }
	          
	          for(int i=0;i<=mapx;i++){
	             for(int j=0;j<=mapy;j++){
	                if(mapdata[i][j]==COLORBLOB){   // ��ǥ���� ColorBlob�� ���
	                   g.setColor(Color.BLUE);
	                   g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // �Ķ����� �׸���
	                }
	                if(mapdata[i][j]==HAZARD){      // ��ǥ���� hazard�� ���
	                   g.setColor(Color.RED);
	                   g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // �������� �׸���
	                }
	                if(mapdata[i][j]==FIND){	// ��ǥ���� find�� ���
	                   g.setColor(Color.ORANGE);	
	                   g.fillRect(15+widthmap*i,365-heightmap*j,30,30);	// ��Ȳ�� �׸� �׸���            
	                }
	             }
	          }
	    	  
	          g.setColor(Color.MAGENTA);
	    	  switch(robot.positionSensor().getDirection()) {
	    	  case EAST :	// �κ��� ������ �ٶ󺸰� �ִ� ���
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotEsa,robotEaa);
	              break;
	    	  case SOUTH:	// �κ��� ������ �ٶ󺸰� �ִ� ���
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotSsa,robotSaa);
	              break;
	    	  case WEST:	// �κ��� ������ �ٶ󺸰� �ִ� ���
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotWsa,robotWaa);
	              break;
	    	  case NORTH:	// �κ��� ������ �ٶ󺸰� �ִ� ���
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotNsa,robotNaa);
	              break;
	    	  }
	      }
   }
   
   // set ��ư�� ������ ��� �̺�Ʈ
   private class setListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
         if(e.getSource()==txmap || e.getSource()==txhazard ||
               e.getSource()==txstart || e.getSource()==txfind ||
               e.getSource()==btnset){      
        	hazardpositionList.clear();
        	findpositionList.clear();
        	
            repaint();	// set ��ư�� �ٽ� ������ �� ������ ���� �׸��� ���� ����Ʈ�� �ʱ�ȭ �ϰ� �����Ѵ�.
            
            try{
            	int mapx = Integer.valueOf(txmap.getText().substring(0,txmap.getText().indexOf(",")));
                int mapy = Integer.valueOf(txmap.getText().substring(txmap.getText().indexOf(",")+1,txmap.getText().length()));
                mapposition = new Position(mapx,mapy); // �Է¹��� ����ũ�⸦ mapposition ����Ʈ�� ����

                int startx = Integer.valueOf(txstart.getText().substring(0,txstart.getText().indexOf(",")));
                int starty = Integer.valueOf(txstart.getText().substring(txstart.getText().indexOf(",")+1,txstart.getText().length()));
                startposition = new Position(startx,starty);	// �Է¹��� ���������� startposition ����Ʈ�� ����
                
                robot = new SimSensor(startx, starty);
                
                StringTokenizer stfind = new StringTokenizer(txfind.getText()); 
                while(stfind.hasMoreTokens()) {
                   String tmp = stfind.nextToken();
                   int findx = Integer.valueOf(tmp.substring(0,1));
                   int findy = Integer.valueOf(tmp.substring(2,tmp.length()));
                   findpositionList.add(new Position(findx,findy));                
               }	// �Է¹��� Ž�������� findpositionList�� ����
                
               StringTokenizer sthazard = new StringTokenizer(txhazard.getText());
               while(sthazard.hasMoreTokens()) {
            	    String tmp = sthazard.nextToken();
                    int hazardx = Integer.valueOf(tmp.substring(0, 1));
                    int hazardy = Integer.valueOf(tmp.substring(2, tmp.length()));
                    hazardpositionList.add(new Position(hazardx, hazardy));
               }	// �Է¹��� ���������� hazardpositionList�� ����
               
               txlog.append("���� ũ��"+mapposition+"\n���� ����"+hazardpositionList+
                       "\n���� ����"+startposition+"\nŽ�� ����"+findpositionList+"\n");
               // system log�� �Է¹��� ������ ���     
               
               MapManager map = new MapManager();
               PathManager path = new PathManager();
               map.createMap(mapx,mapy,hazardpositionList);	// MapManagerŬ������ �Է°��� �����ϰ� �ʻ���
               path.createPath(startx, starty, findpositionList);	// pathManagerŬ������ �Է°��� �����ϰ� ��λ���
               Map mapmap = new Map();      
               mapdata= mapmap.getMap(0);    // ������ �糭������ ������ �޾ƿ´�             

               MapComponent mapcomponent = new MapComponent(mapposition.getX(), mapposition.getY());
               // �Է¹��� ����ũ�⸦ �������� �糭�������� �׸���.
               MapForm.f.setComponent(mapcomponent);
               
               RobotPosition.pathCount=1;
                                
               }	// ��������, ��������, Ž�������� �Է°��� ����ũ�⿡�� ��� ���           
               catch(ArrayIndexOutOfBoundsException e1){
            		txlog.append("������ ������ ����ϴ�"+"\n");
               } 
         	}
      	}
   	}    
   // start ��ư�� ���� ��� �̺�Ʈ
   private class startListener implements ActionListener{
	   public void actionPerformed(ActionEvent e){
		   if(e.getSource()==btnstart){
			   MovementComponent mc = new MovementComponent(mapposition.getX(), mapposition.getY());
               // �Է¹��� ����ũ�⸦ �������� �糭�������� �׸���.
               MapForm.f.setComponent(mc);
			   RobotPosition rp = new RobotPosition();	// �κ��� �̵��� �����Ѵ�.   
		   }
	   }
   }
	// �κ��� �������� �ý��� �α׿� ����ϴ� �޼ҵ�
	public void moveLog(String message){
		txlog.append(message+"\n");
	}
	// ���� �޼����� �ý��� �α׿� ����ϴ� �޼ҵ�
	public void errorMessage(String message){
		txlog.append(message+"\n");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	  System.exit(0);
	}
	// �糭�������� ������Ʈ�� ���� ������ִ� �޼ҵ�
	public void updatePaint(int[][] update_map){
		mapdata = update_map;
	}
}

public class MapForm extends JFrame  {

      static MyFrame f;
      
      public static void main(String[] args) {
         open();
      }
      
      public static SimSensor getRobot() {
         return f.getRobot();
      }
   // ����ڰ� MapForm�� �����ϴ� �Լ�
      public static void open(){   
         f = new MyFrame();
      }
      
      public static Position getMapPosition() {
         return f.getMapPosition();
      }
      // �����޼����� �ý��� �α׿� ����ϴ� �޼ҵ�
      public static void errorMessage(String message){
    	  f.errorMessage(message);
      }
      // �̵���Ȳ�� �ý��� �α׿� ����ϴ� �޼ҵ�
      public static void moveLog(String message){
    	  f.moveLog(message);
      }

      // �糭�������� ������Ʈ�� ���� ������ִ� �޼ҵ�
      public static void updatePaint(int[][] update_map){
    	  f.updatePaint(update_map);
      }
}