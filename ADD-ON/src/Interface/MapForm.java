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

import MapArtifacts.MapManager;
import MapArtifacts.Position;

class MyFrame extends JFrame{
	private JTextField txmap,txhazard,txstart,txfind;
	private JButton btnset;
	private JSplitPane sp1, sp2, sp3;
	private TextArea txlog;
	private JPanel panelInput, panelMap, panelLog, panelDev;
	private JScrollPane scrollPane;
	private ImageIcon imcolorblob, imhazard, imrobotN, imrobotE, imrobotS, imrobotW;

	public MyFrame(){
		setSize(750,500);
		setTitle("ADD-ON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener listener = new ButtonListener();
		
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
		sp1.setDividerLocation(40);	// 자르는 위치 높이 40에서 시작
		
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분할
		sp2.setDividerLocation(250); // 자르는 위치 너비 250에서 시작		
		
		sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
		sp3.setDividerLocation(300); // 자르는 위치 높이 300에서 시작
		
		panelInput = new JPanel(); // 입력 값들을 배치하는 판넬
		panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border border = BorderFactory.createLineBorder(null);
		panelInput.setBorder(border);
		
		panelInput.add(new JLabel("지도 크기"));	// 입력값들을 배치할 판넬
		txmap = new JTextField(8);	// 지도크기를 입력받을 텍스트필드
		txmap.addActionListener(listener);
		panelInput.add(txmap);
		panelInput.add(new JLabel("위험 지역"));	
		txhazard = new JTextField(8);	// 위험지역을 입력받을 텍스트필드
		txhazard.addActionListener(listener);
		panelInput.add(txhazard);
		panelInput.add(new JLabel("시작 지점"));
		txstart = new JTextField(8);	// 시작지점을 입력받을 텍스트필드
		txstart.addActionListener(listener);
		panelInput.add(txstart);
		panelInput.add(new JLabel("탐색 지점"));
		txfind = new JTextField(8);		// 탐색지점을 입력받을 텍스트필드
		txfind.addActionListener(listener);
		panelInput.add(txfind);
		btnset = new JButton("set");
		btnset.addActionListener(listener);	// set 버튼에 listener 달아줌
		panelInput.add(btnset);
		
		panelMap = new JPanel();	// 재난지역 모델을 배치할 판넬
		imcolorblob = new ImageIcon("icon/colorblob.png");	// colorblob icon
		imhazard = new ImageIcon("icon/hazard.jpg");	// hazard icon
		imrobotN = new ImageIcon("icon/robot_N.jpg");	// 진행방향이 북쪽인 robot icon
		imrobotE = new ImageIcon("icon/robot_E.jpg");	// 진행방향이 동쪽인 robot icon
		imrobotS = new ImageIcon("icon/robot_S.jpg");	// 진행방향이 남쪽인 robot icon
		imrobotW = new ImageIcon("icon/robot_W.jpg");	// 진행방향이 서쪽인 robot icon
		JLabel lbcolorblob = new JLabel(imcolorblob);
				
		panelLog = new JPanel();	// System Log를 배치할 판넬	
		panelLog.add(new JLabel("System Log"));
		txlog = new TextArea(15,30);
		scrollPane = new JScrollPane(txlog);
		panelLog.add(txlog);
		
		panelDev = new JPanel(); // 개발자 정보를 배치할 판넬		
				
		sp1.setTopComponent(panelInput);	// sp1에 panelInput 배치
		sp2.setRightComponent(panelMap);	// sp2에 panelMap 배치
		sp3.setTopComponent(panelLog);	// sp3 상단부에 panelLog 배치
		sp3.setBottomComponent(panelDev);	// sp3 하단부에 panelDev 배치
		sp2.add(sp3);
		sp1.add(sp2);	
		getContentPane().add(sp1);
		
		setVisible(true);
	}	
	
	// set 버튼을 눌렀을 경우 이벤트
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==txmap || e.getSource()==txhazard ||
					e.getSource()==txstart || e.getSource()==txfind ||
					e.getSource()==btnset){
			  
				ArrayList<Position> mappositionList = new ArrayList<>();	// 맵 좌표를 저장하는 리스트
				ArrayList<Position> startpositionList = new ArrayList<>();	// 시작지점 좌료를 저장하는 리스트
				ArrayList<Position> hazardpositionList = new ArrayList<>();	// 위험지역 좌표를 저장하는 리스트
				ArrayList<Position> findpositionList = new ArrayList<>();	// 탐색지점 좌료를 저장하는 리스트
								 
			    StringTokenizer sthazard = new StringTokenizer(txhazard.getText());
			    StringTokenizer stfind = new StringTokenizer(txfind.getText());
			    StringTokenizer ststart = new StringTokenizer(txstart.getText());
			    StringTokenizer stmap = new StringTokenizer(txmap.getText());
			    
			    String tmp = stmap.nextToken();
			    int mapx = Integer.valueOf(tmp.substring(0,1));
			    int mapy = Integer.valueOf(tmp.substring(2,tmp.length()));
			    mappositionList.add(new Position(mapx,mapy));	
			    
			    JPanel drawpanel = new JPanel(){
					public void paint(Graphics g){         //재작성 JPanel 중 방법
						int width = 20;
						for(int i=0; i<mapx; i++){
							g.setColor(Color.BLACK);
							g.drawLine(30,30+(i*width),((mapx-1)*width),30+width);
						}
				       
					}
				};
			    
			    tmp = ststart.nextToken();
			    int x = Integer.valueOf(tmp.substring(0,1));
			    int y = Integer.valueOf(tmp.substring(2,tmp.length()));
			    startpositionList.add(new Position(x,y));
			    
			    while(stfind.hasMoreTokens()) {
			    	tmp = stfind.nextToken();
			    	x = Integer.valueOf(tmp.substring(0,1));
			    	y = Integer.valueOf(tmp.substring(2,tmp.length()));
			    	findpositionList.add(new Position(x,y));			    	
			    }
			    
			    while(sthazard.hasMoreTokens()) {
			        tmp = sthazard.nextToken();
			        x = Integer.valueOf(tmp.substring(0, 1));
			        y = Integer.valueOf(tmp.substring(2, tmp.length()));
			        hazardpositionList.add(new Position(x, y));
			    }
			    			   
			    MapManager map = new MapManager();
			    map.create(mapx,mapy,hazardpositionList);
			    
				txlog.append("지도 크기"+mappositionList+"\n위험 지점"+hazardpositionList+
						"\n시작 지점"+startpositionList+"\n탐색 지점"+findpositionList+"\n");
				// system log에 입력받은 값들을 출력
			}
		}
	}	 
}

public class MapForm  {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame f = new MyFrame();
	}
}
