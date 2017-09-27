package view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import until.CallBack;
import until.Until;
import view.ShowMessage;
import dao.ScoreDao;
import entity.Banji;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreView {

	Set<Score> listChanged=new HashSet<Score>();
	static List<Score> list = new ArrayList<Score>();
	static List<Score> searchList = new ArrayList<Score>();
	static ScoreTableModel model = null;
	JTable table;
	static ScoreDao scoreDao = new ScoreDao();
	JTextField studentNameText;
	static JFrame frame;
	JTextField banjiNameText;
	private JTextField subNameText;
	private JTextField scoreText;
	private static ScoreView instance;
	
	private ScoreView()
	{
		init();
	}
	
	public static ScoreView getInstance()
	{
		if(instance==null)
		{
			instance=new ScoreView();
		}
		else
		{
			refreshTable();
			frame.setVisible(true);
		}
		return instance;
	}
	
	private void init() {
		list = scoreDao.selectAll();
		if(list==null)
		{
			list=new ArrayList<Score>();
		}
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		frame = new JFrame();
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("成绩管理");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 学生名称查询
		JLabel studentNameLabel = new JLabel();
		studentNameLabel.setText("学生名");
		panel1.add(studentNameLabel);
		studentNameText = new JTextField();
		studentNameText.setPreferredSize(new Dimension(70, 30));
		panel1.add(studentNameText);
		
		//班级名查询
		JLabel banjiNameLabel = new JLabel();
		banjiNameLabel.setText("班级名");
		panel1.add(banjiNameLabel);
		banjiNameText = new JTextField();
		banjiNameText.setPreferredSize(new Dimension(70, 30));
		panel1.add(banjiNameText);
		
		//课程名
		JLabel subNameLabel = new JLabel();
		subNameLabel.setText("课程名");
		panel1.add(subNameLabel);
		subNameText = new JTextField();
		subNameText.setPreferredSize(new Dimension(70, 30));
		panel1.add(subNameText);
		
		//分数
		JLabel scoreLabel = new JLabel();
		scoreLabel.setText("分数");
		panel1.add(scoreLabel);
		scoreText = new JTextField();
		scoreText.setPreferredSize(new Dimension(70, 30));
		panel1.add(scoreText);
		
		
		//查询事件
		JButton searchBtn = new JButton();
		searchBtn.setText("查询");
		searchBtn.setPreferredSize(new Dimension(70, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchList.clear();
				String studentName=studentNameText.getText().toString().trim();
				String banjiName=banjiNameText.getText().toString().trim();
				String subName=subNameText.getText().toString().trim();
				String scoreString=scoreText.getText().toString().trim();
				int score=0;
				if(!"".equals(scoreString))
				{
					score=Integer.valueOf(scoreString);
				}
				else
				{
					score=-1;
				}
				Score score2=new Score();
				Student stu=new  Student();
				Subject sub=new Subject();
				Banji banji=new Banji();
				banji.setName(banjiName);
				stu.setName(studentName);
				sub.setName(subName);
				stu.setBanji(banji);
				score2.setScore(score);
				score2.setStudent(stu);
				score2.setSubject(sub);
				searchList=scoreDao.selectBycondition(score2);
				model.setData(searchList);
				model.fireTableDataChanged();
			}
		});
		panel1.add(searchBtn);

		//表格
		table = new JTable();
		model = new ScoreTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);
		
		//保存
		JButton jSaveButton=new JButton();
		jSaveButton.setText("保存");
		jSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				listChanged=model.getListChanged();
				boolean flag=false;
				flag=scoreDao.save(listChanged);
				ShowMessage.show(flag, "保存");
				refreshTable();
			}
		});
		panel3.add(jSaveButton);
		frame.setVisible(true);
	}

	public static void refreshTable() {
		
		list=scoreDao.selectAll();
		searchList.clear();
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		model.setData(list);
		model.listChanged.clear();
		model.fireTableDataChanged();
	}

	
}
