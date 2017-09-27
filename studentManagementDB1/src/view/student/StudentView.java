package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import until.CallBack;
import until.Until;
import view.ShowMessage;
import dao.BanjiDao;
import dao.StudentDao;
import entity.Banji;
import entity.Student;

public class StudentView {

	static List<Student> list = new ArrayList<Student>();
	static List<Student> searchList = new ArrayList<Student>();
	List<Banji> listBanji = new ArrayList<Banji>();
	private static StudentView instance;
	static StudentTableModel model = null;
	JTable table;
	static StudentDao stuDao = new StudentDao();
	BanjiDao banjiDao=new BanjiDao();
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	static JFrame frame;
	JTextField banji_nameText;
	JComboBox banji_nameBox;

	private StudentView() {
		init();
	}

	public static StudentView getInstance() {
		if (instance == null) {
			instance = new StudentView();
		}else {
			refreshTable();
			frame.setVisible(true);
		}
		return instance;
	}

	private void init() {
		list = stuDao.selectAll();
		listBanji=banjiDao.selectAll();
		if (list == null) {
			list = new ArrayList<Student>();
		}
		for (int i = 0; i < list.size(); i++) {
			searchList.add(list.get(i));
		}
		frame = new JFrame();
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("学生管理");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 姓名查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(50, 30));
		panel1.add(nameText);
		// 性别查询
		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel1.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(50, 30));
		panel1.add(sexText);
		// 年龄查询
		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel1.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(50, 30));
		panel1.add(ageText);

		// bj_name查询下拉框
		JLabel banji_nameLabel = new JLabel();
		banji_nameLabel.setText("班级");
		panel1.add(banji_nameLabel);
		
		banji_nameBox = new JComboBox();
		banji_nameBox.setPreferredSize(new Dimension(100, 30));
		banji_nameBox.addItem("请选择班级");
		for(int i=0;i<listBanji.size();i++)
		{
			banji_nameBox.addItem(listBanji.get(i).getName());
		}
		banji_nameBox.addItem("没有班级");
		panel1.add(banji_nameBox);

		JButton searchBtn = new JButton();
		searchBtn.setText("查询");
		searchBtn.setPreferredSize(new Dimension(90, 30));
		// 查询事件
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				searchList.clear();
				String name = nameText.getText();
				String sex = sexText.getText();
				String bj_name;
				int index=banji_nameBox.getSelectedIndex();
				if(index==0)
				{
					bj_name="";
				}
				else if(index==listBanji.size()+1)
				{
					bj_name="-1";
				}
				else
				{
					bj_name=listBanji.get(index-1).getName();
				}
				int age = -1;
				if (!ageText.getText().equals("")) {
					age = Integer.parseInt(ageText.getText());
				}
				searchList = stuDao.serchByCondition(name, sex, age, bj_name);
				model.setData(searchList);
				model.fireTableDataChanged();
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new StudentTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		JButton addBtn = new JButton();
		addBtn.setText(Until.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);
		// 添加事件
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentView asv = AddStudentView.getInstance(new CallBack() {
					@Override
					public void call() {
						refreshTable();
					}
				});

				asv.createFrame();

			}
		});

		JButton modifyBtn = new JButton();
		modifyBtn.setText(Until.MES_MODIFY);
		modifyBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(modifyBtn);
		// 修改事件
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame, "没有选中数据");

				} else {
					ModifyStudentView msv = new ModifyStudentView(
							new CallBack() {
								@Override
								public void call() {
									refreshTable();
								}

							}, searchList.get(index).getId());
					msv.init();
				}
			}
		});

		JButton deleteBtn = new JButton();
		deleteBtn.setText(Until.MES_DELETE);
		deleteBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(deleteBtn);
		// 删除事件
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "没有选中数据");

				} else {

					int type = JOptionPane.showConfirmDialog(null, "是否删除数据?");
					if (type == 0) {
						Student stu = searchList.get(index);
						list.remove(stu);
						int flagNum = stuDao.delete(stu);
						boolean flag = false;
						if (flagNum != 0) {
							flag = true;
						}
						ShowMessage.show(flag, Until.MES_DELETE);
						refreshTable();
					}

				}

			}
		});
		frame.setVisible(true);

	}

	public static void refreshTable() {

		list = stuDao.selectAll();
		searchList.clear();
		for (int i = 0; i < list.size(); i++) {
			searchList.add(list.get(i));
		}
		model.setData(list);
		model.fireTableDataChanged();
	}

}
