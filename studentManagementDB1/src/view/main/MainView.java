package view.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.banji.BanjiView;
import view.score.ScoreView;
import view.student.StudentView;
import view.subject.SubjectView;

public class MainView extends JFrame{
	
	private SubjectView subv;
	private BanjiView bv;
	private StudentView sv;
	ScoreView scv;
	
	public void init()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(600, 400);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,50,80));
		
		JPanel jPanel=new JPanel();
		jPanel.setPreferredSize(new Dimension(600,400));
		
		JButton buttonStudent=new JButton();
		buttonStudent.setText("学生管理");
		buttonStudent.setPreferredSize(new Dimension(200,60));
		buttonStudent.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sv=StudentView.getInstance();
			}
		});
		jPanel.add(buttonStudent);
		
		JButton buttonBanji=new JButton();
		buttonBanji.setText("班级管理");
		buttonBanji.setPreferredSize(new Dimension(200,60));
		buttonBanji.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bv=BanjiView.getInstance();
			}
		});
		jPanel.add(buttonBanji);
		
		JButton button3=new JButton();
		button3.setText("科目管理");
		button3.setPreferredSize(new Dimension(200,60));
		button3.addActionListener(new ActionListener() {
		

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				subv=SubjectView.getInstance();
			}
		});
		jPanel.add(button3);
		
		JButton button4=new JButton();
		button4.setText("成绩管理");
		button4.setPreferredSize(new Dimension(200,60));
		button4.addActionListener(new ActionListener() {
		

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				scv=ScoreView.getInstance();
			}
		});
		jPanel.add(button4);
		
		this.add(jPanel);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		MainView mainView=new MainView();
		mainView.init();
	}
}
