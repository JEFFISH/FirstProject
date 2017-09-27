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
import javax.swing.JPanel;
import javax.swing.JTextField;

import until.CallBack;
import until.Until;
import view.ShowMessage;
import dao.BanjiDao;
import dao.StudentDao;
import entity.Banji;
import entity.Student;

public class ModifyStudentView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
	CallBack callBack;
	Student stu;
	int id;
	StudentDao stuDao = new StudentDao();
	JTextField idText;
	BanjiDao banjiDao=new BanjiDao();
	List<Banji> listBanji = new ArrayList<Banji>();
	JComboBox banji_nameBox;

	public ModifyStudentView(CallBack callBack, int id) {
		this.callBack = callBack;
		this.id = id;
		stu = stuDao.selectById(id);
	}

	public void init() {
		listBanji=banjiDao.selectAll();
		frame = new JFrame();
		frame.setSize(300, 400);
		frame.setLocationRelativeTo(null);
		frame.setTitle("修改学生");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);
		mainPanel.add(panel5);
		// 姓名
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		nameText.setText(stu.getName());
		panel1.add(nameText);
		// 性别
		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel2.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(120, 30));
		sexText.setText(stu.getSex());
		panel2.add(sexText);
		// 年龄
		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel3.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(120, 30));
		ageText.setText(String.valueOf(stu.getAge()));
		panel3.add(ageText);

		// bj_name查询下拉框
		JLabel banji_nameLabel = new JLabel();
		banji_nameLabel.setText("班级");
		panel4.add(banji_nameLabel);

		banji_nameBox = new JComboBox();
		banji_nameBox.setPreferredSize(new Dimension(120, 30));
		banji_nameBox.addItem("请选择班级");
		for (int i = 0; i < listBanji.size(); i++) {
			banji_nameBox.addItem(listBanji.get(i).getName());
		}
		banji_nameBox.addItem("没有班级");
		boolean flag=false;
		for(int i=0;i<listBanji.size();i++)
		{
			if(listBanji.get(i).getName().equals(stu.getBanji().getName()))
			{
				banji_nameBox.setSelectedIndex(i+1);
				flag=true;
				break;
			}
		}
		if(!flag)
		{
			banji_nameBox.setSelectedIndex(listBanji.size()+1);
		}
		panel4.add(banji_nameBox);

		JButton saveBtn = new JButton();
		saveBtn.setText("保存");
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel5.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				String sex = sexText.getText();
				int index=banji_nameBox.getSelectedIndex();
				int age = Integer.parseInt(ageText.getText());
				Student stuNew = new Student();
				Banji banji = new Banji();
				if(index>0&&index<=listBanji.size())
				{
					banji.setName(listBanji.get(index-1).getName());
					banji.setId(listBanji.get(index-1).getId());
				}
				stuNew.setName(name);
				stuNew.setSex(sex);
				stuNew.setAge(age);
				stuNew.setBanji(banji);
				int flagNum = stuDao.update(stu, stuNew);
				boolean flag = false;
				if (flagNum != 0) {
					flag = true;
				}
				ShowMessage.show(flag, Until.MES_MODIFY);
				frame.dispose();
				callBack.call();

			}
		});
		frame.setVisible(true);

	}

}
