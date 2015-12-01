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
		
		panelMap = new JPanel();	// �糭���� ���� ��ġ�� �ǳ�
		imcolorblob = new ImageIcon("icon/colorblob.png");	// colorblob icon
		imhazard = new ImageIcon("icon/hazard.jpg");	// hazard icon
		imrobotN = new ImageIcon("icon/robot_N.jpg");	// ��������� ������ robot icon
		imrobotE = new ImageIcon("icon/robot_E.jpg");	// ��������� ������ robot icon
		imrobotS = new ImageIcon("icon/robot_S.jpg");	// ��������� ������ robot icon
		imrobotW = new ImageIcon("icon/robot_W.jpg");	// ��������� ������ robot icon
		JLabel lbcolorblob = new JLabel(imcolorblob);
				
		panelLog = new JPanel();	// System Log�� ��ġ�� �ǳ�	
		panelLog.add(new JLabel("System Log"));
		txlog = new TextArea(15,30);
		scrollPane = new JScrollPane(txlog);
		panelLog.add(txlog);
		
		panelDev = new JPanel(); // ������ ������ ��ġ�� �ǳ�		
				
		sp1.setTopComponent(panelInput);	// sp1�� panelInput ��ġ
		sp2.setRightComponent(panelMap);	// sp2�� panelMap ��ġ
		sp3.setTopComponent(panelLog);	// sp3 ��ܺο� panelLog ��ġ
		sp3.setBottomComponent(panelDev);	// sp3 �ϴܺο� panelDev ��ġ
		sp2.add(sp3);
		sp1.add(sp2);	
		getContentPane().add(sp1);
		
		setVisible(true);
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
			    
			    JPanel drawpanel = new JPanel(){
					public void paint(Graphics g){         //���ۼ� JPanel �� ���
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
			    
				txlog.append("���� ũ��"+mappositionList+"\n���� ����"+hazardpositionList+
						"\n���� ����"+startpositionList+"\nŽ�� ����"+findpositionList+"\n");
				// system log�� �Է¹��� ������ ���
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
