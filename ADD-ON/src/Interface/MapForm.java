package Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.border.Border;

class MyFrame extends JFrame{
	private JTextField txmap,txhazard,txstart,txfind;
	private JButton btnset;
	private JSplitPane sp1, sp2, sp3;
	private TextArea txlog;
	private JPanel panelInput, panelLog, panelDev;
	private JScrollPane scrollPane;
	String mapList, hazardList, startList, findList;

	public MyFrame(){
		setSize(750,500);
		setTitle("ADD-ON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener listener = new ButtonListener();
		mapList =  new String();  // 지도 크기를 입력 받는 문자열
		hazardList = new String();  // 위험 지점을 입력 받는 문자열
		startList = new String();  // 시작 지점을 입력 받는 문자열
		findList = new String();  // 탐색 지점을 입력 받는 문자열
		
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
		sp1.setDividerLocation(40);	// 자르는 위치 높이 40 에서 시작
		
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분할
		sp2.setDividerLocation(250); // 자르는 위치 너비 250에서 시작		
		
		sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분할
		sp3.setDividerLocation(300);
		
		panelInput = new JPanel(); // 입력 값들을 배치하는 판넬
		panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border border = BorderFactory.createLineBorder(null);
		panelInput.setBorder(border);
		
		panelInput.add(new JLabel("지도 크기"));
		txmap = new JTextField(8);
		txmap.addActionListener(listener);
		panelInput.add(txmap);
		panelInput.add(new JLabel("위험 지역"));
		txhazard = new JTextField(8);
		txhazard.addActionListener(listener);
		panelInput.add(txhazard);
		panelInput.add(new JLabel("시작 지점"));
		txstart = new JTextField(8);
		txstart.addActionListener(listener);
		panelInput.add(txstart);
		panelInput.add(new JLabel("탐색 지점"));
		txfind = new JTextField(8);
		txfind.addActionListener(listener);
		panelInput.add(txfind);
		btnset = new JButton("set");
		btnset.addActionListener(listener);
		panelInput.add(btnset);
		
		panelLog = new JPanel();	// System Log를 배치할 판넬	
		panelLog.add(new JLabel("System Log"));
		txlog = new TextArea(15,30);
		scrollPane = new JScrollPane(txlog);
		panelLog.add(txlog);
		
		panelDev = new JPanel(); // 개발자 정보를 배치할 판넬		
				
		sp1.setTopComponent(panelInput);	// sp1에 panelInput 배치
		sp2.setRightComponent(new JLabel("Map"));
		sp3.setTopComponent(panelLog);	// sp3에 panelLog 배치
		sp3.setBottomComponent(new JLabel("Developer"));		
		sp2.add(sp3);
		sp1.add(sp2);	
		getContentPane().add(sp1);
		
		setVisible(true);
	}	

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==txmap || e.getSource()==txhazard ||
					e.getSource()==txstart || e.getSource()==txfind ||
					e.getSource()==btnset){
				mapList = txmap.getText();
				hazardList = txhazard.getText();
				startList = txstart.getText();
				findList = txfind.getText();
				txlog.append("지도 크기"+mapList+"\n위험 지점"+hazardList+"\n시작 지점"+startList+"\n탐색 지점"+findList+"\n");
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
