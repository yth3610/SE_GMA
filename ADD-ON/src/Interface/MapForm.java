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
   private static int width=450;   // 재난 지역 모델 너비
   private static int height=350;   // 재난 지역 모델 높이
   private static int x=30, y=30;   // 재난 지역 모델 좌측 상단 x,y 좌표
   private static int robotWsa=50,robotWaa=-100;   // 로봇이 서쪽을 향하고 있는 경우
   private static int robotSsa=40,robotSaa=100;   // 로봇이 남쪽을 향하고 있는 경우
   private static int robotEsa=130,robotEaa=100;   // 로봇이 동쪽을 향하고 있는 경우
   private static int robotNsa=-40,robotNaa=-100;   // 로봇이 북쪽을 향하고 있는 경우
   private int[][] mapdata = new int[5][5];	// 재난지역모델 정보를 저장할 리스트
   private ArrayList<Position> hazardpositionList = new ArrayList<>();   // 위험지역 좌표를 저장하는 리스트
   private ArrayList<Position> findpositionList = new ArrayList<>();   // 탐색지점 좌료를 저장하는 리스트
   private Position startposition, mapposition, robotposition;	// 입력값들을 저장할 리스트

   public MyFrame(){
      setSize(800,500);
      setTitle("ADD-ON");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      setListener setlistener = new setListener();
      startListener startlistener = new startListener();
      
      sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
      sp1.setDividerLocation(40);   // 자르는 위치 높이 40에서 시작
      
      sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분할
      sp2.setDividerLocation(250); // 자르는 위치 너비 250에서 시작      
      
      sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
      sp3.setDividerLocation(350); // 자르는 위치 높이 300에서 시작
      
      panelInput = new JPanel(); // 입력 값들을 배치하는 판넬
      panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
      Border border = BorderFactory.createLineBorder(null);
      panelInput.setBorder(border);
      
      panelInput.add(new JLabel("지도 크기"));   // 입력값들을 배치할 판넬
      txmap = new JTextField(8);   // 지도크기를 입력받을 텍스트필드
      txmap.addActionListener(setlistener);
      panelInput.add(txmap);
      panelInput.add(new JLabel("위험 지역"));   
      txhazard = new JTextField(8);   // 위험지역을 입력받을 텍스트필드
      txhazard.addActionListener(setlistener);
      panelInput.add(txhazard);
      panelInput.add(new JLabel("시작 지점"));
      txstart = new JTextField(8);   // 시작지점을 입력받을 텍스트필드
      txstart.addActionListener(setlistener);
      panelInput.add(txstart);
      panelInput.add(new JLabel("탐색 지점"));
      txfind = new JTextField(8);      // 탐색지점을 입력받을 텍스트필드
      txfind.addActionListener(setlistener);
      panelInput.add(txfind);
      btnset = new JButton("set");
      btnset.addActionListener(setlistener);   // set 버튼에 setlistener 달아줌
      panelInput.add(btnset);
      btnstart = new JButton("start");	// start 버튼에 startlisener 달아줌
      btnstart.addActionListener(startlistener);
      panelInput.add(btnstart);
            
      panelLog = new JPanel();   // System Log를 배치할 판넬   
      panelLog.setLayout(new BorderLayout());
      panelLog.add(new JLabel("System Log"),BorderLayout.PAGE_START);
      txlog = new TextArea(15,30);
      scrollPane = new JScrollPane(txlog);
      panelLog.add(txlog,BorderLayout.CENTER);
      
      panelDev = new JPanel(); // 개발자 정보를 배치할 판넬
      panelDev.setLayout(new BoxLayout(panelDev, BoxLayout.Y_AXIS));      
      panelDev.add(new JLabel("2015 SE - Team GMA"));
      panelDev.add(new JLabel("Kim YeoJung, Seo Jung, Cheon SangJin"));
      
      panelMap = new JPanel(); // 초창기 재난지역 모델
            
      sp1.setTopComponent(panelInput);   // sp1에 panelInput 배치
      sp2.setRightComponent(panelMap);   // sp2에 panelMap 배치
      sp3.setTopComponent(panelLog);   // sp3 상단부에 panelLog 배치
      sp3.setBottomComponent(panelDev);   // sp3 하단부에 panelDev 배치
      sp2.add(sp3);
      sp1.add(sp2);   
      getContentPane().add(sp1);
      
      setVisible(true);
   }
   
   // MapComponent를 불러오는 생성자
   public void setComponent(MapComponent mapcomponent) {
       sp2.setRightComponent(mapcomponent);
   }
   
   // MovementComponent를 불러오는 생성자
   public void setComponent(MovementComponent movecomponent){
	   sp2.setRightComponent(movecomponent);
   }

   public SimSensor getRobot() {
         return this.robot;
   }
   
   public Position getMapPosition() {
      return this.mapposition;

   }
   
   // 지도크기, 위험지역, 탐색지점, 시작지점을 입력받은 뒤 재난지역모델을 그리는 컴포넌트
   class MapComponent extends JComponent implements Finals{
      int mapx = 1, mapy = 1;
      
      // 생성자
      public MapComponent(int x, int y) {
         this.mapx = x;
         this.mapy = y;
      }   

      public void paint(Graphics g){

         g.drawRect(x,y,width,height);  

         int widthmap=width/mapx;   // 격자의 너비
         int heightmap=height/mapy;   // 격자의 높이
         int tempx=widthmap;
         int tempy=heightmap;
         
         for(int i=0;i<mapx-1;i++){   // x축 라인을 그린다
            g.drawLine(30+tempx,y,30+tempx,y+height);
            tempx=tempx+widthmap;
         }
         
         for(int i=0;i<mapy-1;i++){   // y축 라인을 그린다         
            g.drawLine(x,30+tempy,x+width,30+tempy);
            tempy=tempy+heightmap;
         }
         
         for(int i=0;i<=mapx;i++){
            for(int j=0;j<=mapy;j++){
               if(mapdata[i][j]==COLORBLOB){   // 좌표값이 ColorBlob인 경우
                  g.setColor(Color.BLUE);
                  g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // 파란원을 그린다
               }
               if(mapdata[i][j]==HAZARD){      // 좌표값이 hazard인 경우
                  g.setColor(Color.RED);
                  g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // 빨간원을 그린다
               }
               if(mapdata[i][j]==START){   // 시작 지점에 로봇을 그린다
                  g.setColor(Color.MAGENTA);
                  g.fillArc(20+widthmap*i,355-heightmap*j,50,50,robotEsa,robotEaa);
               }
               if(mapdata[i][j]==FIND){		// 탐색 지점을 표시
                  g.setColor(Color.ORANGE);
                  g.fillRect(15+widthmap*i,365-heightmap*j,30,30);            
               }
            }
         }
      }
   }
   
   // 지도크기, 위험지역, 탐색지점, 시작지점을 입력받은 뒤 재난지역모델을 그리는 컴포넌트
   class MovementComponent extends JComponent implements Finals{
	   	  int mapx=1, mapy=1;
	   	  
	   	 public MovementComponent(int x, int y) {
	         this.mapx = x;
	         this.mapy = y;
	      } 
	   	
	      public void paint(Graphics g) {
	          g.drawRect(x,y,width,height);
	          
	          int widthmap=width/mapx;   // 격자의 너비
	          int heightmap=height/mapy;   // 격자의 높이
	          int tempx=widthmap;
	          int tempy=heightmap;
	          
	          for(int i=0;i<mapx-1;i++){   // x축 라인을 그린다
	             g.drawLine(30+tempx,y,30+tempx,y+height);
	             tempx=tempx+widthmap;
	          }
	          
	          for(int i=0;i<mapy-1;i++){   // y축 라인을 그린다         
	             g.drawLine(x,30+tempy,x+width,30+tempy);
	             tempy=tempy+heightmap;
	          }
	          
	          for(int i=0;i<=mapx;i++){
	             for(int j=0;j<=mapy;j++){
	                if(mapdata[i][j]==COLORBLOB){   // 좌표값이 ColorBlob인 경우
	                   g.setColor(Color.BLUE);
	                   g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // 파란원을 그린다
	                }
	                if(mapdata[i][j]==HAZARD){      // 좌표값이 hazard인 경우
	                   g.setColor(Color.RED);
	                   g.fillOval(15+widthmap*i,365-heightmap*j,30,30);   // 빨간원을 그린다
	                }
	                if(mapdata[i][j]==FIND){	// 좌표값이 find인 경우
	                   g.setColor(Color.ORANGE);	
	                   g.fillRect(15+widthmap*i,365-heightmap*j,30,30);	// 주황색 네모를 그린다            
	                }
	             }
	          }
	    	  
	          g.setColor(Color.MAGENTA);
	    	  switch(robot.positionSensor().getDirection()) {
	    	  case EAST :	// 로봇이 동쪽을 바라보고 있는 경우
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotEsa,robotEaa);
	              break;
	    	  case SOUTH:	// 로봇이 남쪽을 바라보고 있는 경우
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotSsa,robotSaa);
	              break;
	    	  case WEST:	// 로봇이 서쪽을 바라보고 있는 경우
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotWsa,robotWaa);
	              break;
	    	  case NORTH:	// 로봇이 북쪽을 바라보고 있는 경우
	              g.fillArc(20+widthmap*robot.positionSensor().getX(),
	            		  355-heightmap*robot.positionSensor().getY(),50,50,robotNsa,robotNaa);
	              break;
	    	  }
	      }
   }
   
   // set 버튼을 눌렀을 경우 이벤트
   private class setListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
         if(e.getSource()==txmap || e.getSource()==txhazard ||
               e.getSource()==txstart || e.getSource()==txfind ||
               e.getSource()==btnset){      
        	hazardpositionList.clear();
        	findpositionList.clear();
        	
            repaint();	// set 버튼을 다시 눌렀을 때 지도를 새로 그리기 위해 리스트를 초기화 하고 시작한다.
            
            try{
            	int mapx = Integer.valueOf(txmap.getText().substring(0,txmap.getText().indexOf(",")));
                int mapy = Integer.valueOf(txmap.getText().substring(txmap.getText().indexOf(",")+1,txmap.getText().length()));
                mapposition = new Position(mapx,mapy); // 입력받은 지도크기를 mapposition 리스트에 저장

                int startx = Integer.valueOf(txstart.getText().substring(0,txstart.getText().indexOf(",")));
                int starty = Integer.valueOf(txstart.getText().substring(txstart.getText().indexOf(",")+1,txstart.getText().length()));
                startposition = new Position(startx,starty);	// 입력받은 시작지점을 startposition 리스트에 저장
                
                robot = new SimSensor(startx, starty);
                
                StringTokenizer stfind = new StringTokenizer(txfind.getText()); 
                while(stfind.hasMoreTokens()) {
                   String tmp = stfind.nextToken();
                   int findx = Integer.valueOf(tmp.substring(0,1));
                   int findy = Integer.valueOf(tmp.substring(2,tmp.length()));
                   findpositionList.add(new Position(findx,findy));                
               }	// 입력받은 탐색지점을 findpositionList에 저장
                
               StringTokenizer sthazard = new StringTokenizer(txhazard.getText());
               while(sthazard.hasMoreTokens()) {
            	    String tmp = sthazard.nextToken();
                    int hazardx = Integer.valueOf(tmp.substring(0, 1));
                    int hazardy = Integer.valueOf(tmp.substring(2, tmp.length()));
                    hazardpositionList.add(new Position(hazardx, hazardy));
               }	// 입력받은 위험지점을 hazardpositionList에 저장
               
               txlog.append("지도 크기"+mapposition+"\n위험 지점"+hazardpositionList+
                       "\n시작 지점"+startposition+"\n탐색 지점"+findpositionList+"\n");
               // system log에 입력받은 값들을 출력     
               
               MapManager map = new MapManager();
               PathManager path = new PathManager();
               map.createMap(mapx,mapy,hazardpositionList);	// MapManager클래스로 입력값을 전달하고 맵생성
               path.createPath(startx, starty, findpositionList);	// pathManager클래스로 입력값을 전달하고 경로생성
               Map mapmap = new Map();      
               mapdata= mapmap.getMap(0);    // 생성된 재난지역모델 정보를 받아온다             

               MapComponent mapcomponent = new MapComponent(mapposition.getX(), mapposition.getY());
               // 입력받은 지도크기를 바탕으로 재난지역모델을 그린다.
               MapForm.f.setComponent(mapcomponent);
               
               RobotPosition.pathCount=1;
                                
               }	// 위험지역, 시작지점, 탐색지점의 입력값이 지도크기에서 벗어난 경우           
               catch(ArrayIndexOutOfBoundsException e1){
            		txlog.append("지도의 범위를 벗어납니다"+"\n");
               } 
         	}
      	}
   	}    
   // start 버튼을 누른 경우 이벤트
   private class startListener implements ActionListener{
	   public void actionPerformed(ActionEvent e){
		   if(e.getSource()==btnstart){
			   MovementComponent mc = new MovementComponent(mapposition.getX(), mapposition.getY());
               // 입력받은 지도크기를 바탕으로 재난지역모델을 그린다.
               MapForm.f.setComponent(mc);
			   RobotPosition rp = new RobotPosition();	// 로봇이 이동을 시작한다.   
		   }
	   }
   }
	// 로봇의 움직임을 시스템 로그에 출력하는 메소드
	public void moveLog(String message){
		txlog.append(message+"\n");
	}
	// 에러 메세지를 시스템 로그에 출력하는 메소드
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
	// 재난지역모델이 업데이트된 것을 출력해주는 메소드
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
   // 사용자가 MapForm에 접근하는 함수
      public static void open(){   
         f = new MyFrame();
      }
      
      public static Position getMapPosition() {
         return f.getMapPosition();
      }
      // 에러메세지를 시스템 로그에 출력하는 메소드
      public static void errorMessage(String message){
    	  f.errorMessage(message);
      }
      // 이동상황을 시스템 로그에 출력하는 메소드
      public static void moveLog(String message){
    	  f.moveLog(message);
      }

      // 재난지역모델이 업데이트된 것을 출력해주는 메소드
      public static void updatePaint(int[][] update_map){
    	  f.updatePaint(update_map);
      }
}