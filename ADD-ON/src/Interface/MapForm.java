package Interface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.image.*;
import java.awt.FlowLayout;
import java.awt.Image;
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
	private JPanel panelInput, panelMap, panelLog, panelDev;
	private JScrollPane scrollPane;
	private ImageIcon imcolorblob, imhazard, imrobot;
	String mapList, hazardList, startList, findList;

	public MyFrame(){
		setSize(750,500);
		setTitle("ADD-ON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener listener = new ButtonListener();
		mapList =  new String();  // ���� ũ�⸦ �Է� �޴� ���ڿ�
		hazardList = new String();  // ���� ������ �Է� �޴� ���ڿ�
		startList = new String();  // ���� ������ �Է� �޴� ���ڿ�
		findList = new String();  // Ž�� ������ �Է� �޴� ���ڿ�
		
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
		sp1.setDividerLocation(40);	// �ڸ��� ��ġ ���� 40 ���� ����
		
		sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // �¿� ����
		sp2.setDividerLocation(250); // �ڸ��� ��ġ �ʺ� 250���� ����		
		
		sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ���� ����
		sp3.setDividerLocation(300);
		
		panelInput = new JPanel(); // �Է� ������ ��ġ�ϴ� �ǳ�
		panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border border = BorderFactory.createLineBorder(null);
		panelInput.setBorder(border);
		
		panelInput.add(new JLabel("���� ũ��"));
		txmap = new JTextField(8);
		txmap.addActionListener(listener);
		panelInput.add(txmap);
		panelInput.add(new JLabel("���� ����"));
		txhazard = new JTextField(8);
		txhazard.addActionListener(listener);
		panelInput.add(txhazard);
		panelInput.add(new JLabel("���� ����"));
		txstart = new JTextField(8);
		txstart.addActionListener(listener);
		panelInput.add(txstart);
		panelInput.add(new JLabel("Ž�� ����"));
		txfind = new JTextField(8);
		txfind.addActionListener(listener);
		panelInput.add(txfind);
		btnset = new JButton("set");
		btnset.addActionListener(listener);
		panelInput.add(btnset);
		
		panelMap = new JPanel();
		imcolorblob = new ImageIcon("colorblob.png");
		imhazard = new ImageIcon("hazard.jpg");
		JLabel lbcolorblob = new JLabel(imcolorblob);
		panelMap.add(lbcolorblob);
		
		panelLog = new JPanel();	// System Log�� ��ġ�� �ǳ�	
		panelLog.add(new JLabel("System Log"));
		txlog = new TextArea(15,30);
		scrollPane = new JScrollPane(txlog);
		panelLog.add(txlog);
		
		panelDev = new JPanel(); // ������ ������ ��ġ�� �ǳ�		
				
		sp1.setTopComponent(panelInput);	// sp1�� panelInput ��ġ
		sp2.setRightComponent(panelMap);
		sp3.setTopComponent(panelLog);	// sp3�� panelLog ��ġ
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
				txlog.append("���� ũ��"+mapList+"\n���� ����"+hazardList+"\n���� ����"+startList+"\nŽ�� ����"+findList+"\n");
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
