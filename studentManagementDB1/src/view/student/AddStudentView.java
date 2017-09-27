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

public class AddStudentView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
	CallBack callBack;
	StudentDao stuDao = new StudentDao();
	JLabel bj_idLabel;
	JComboBox banji_nameBox;
	private static AddStudentView instance;
	BanjiDao banjiDao=new BanjiDao();
	List<Banji> listBanji = new ArrayList<Banji>();

	public static AddStudentView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddStudentView(callBack);
		}
		return instance;
	}

	private AddStudentView(CallBack callBack) {
		this.callBack = callBack;

	}

	public void createFrame() {
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			nameText.setText("");
			ageText.setText("");
			sexText.setText("");
			banji_nameBox.setSelectedIndex(0);
			frame.setVisible(true);
		}
	}

	public void init() {
		listBanji=banjiDao.selectAll();
		frame.setSize(300, 400);
		frame.setLocationRelativeTo(null);
		frame.setTitle("����ѧ��");

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
		// ����
		JLabel nameLabel = new JLabel();
		nameLabel.setText("����");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);
		// �Ա�
		JLabel sexLabel = new JLabel();
		sexLabel.setText("�Ա�");
		panel2.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(120, 30));
		panel2.add(sexText);
		// ����
		JLabel ageLabel = new JLabel();
		ageLabel.setText("����");
		panel3.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(120, 30));
		panel3.add(ageText);

		// bj_name��ѯ������
		JLabel banji_nameLabel = new JLabel();
		banji_nameLabel.setText("�༶");
		panel4.add(banji_nameLabel);
		
		banji_nameBox = new JComboBox();
		banji_nameBox.setPreferredSize(new Dimension(120, 30));
		banji_nameBox.addItem("��ѡ��༶");
		for (int i = 0; i < listBanji.size(); i++) {
			banji_nameBox.addItem(listBanji.get(i).getName());
		}
		banji_nameBox.addItem("û�а༶");
		panel4.add(banji_nameBox);

		JButton saveBtn = new JButton();
		saveBtn.setText("����");
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel5.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Banji banji=new Banji();
				int index=banji_nameBox.getSelectedIndex();
				if(index>0&&index<=listBanji.size())
				{
					banji.setName(listBanji.get(index-1).getName());
					banji.setId(listBanji.get(index-1).getId());
				}
				String name = nameText.getText();
				String sex = sexText.getText();
				int age = Integer.parseInt(ageText.getText());
				Student stu = new Student();
				stu.setName(name);
				stu.setSex(sex);
				stu.setAge(age);
				stu.setBanji(banji);
				int flagNum = stuDao.add(stu);
				boolean flag = false;
				if (flagNum != 0) {
					flag = true;
				}
				ShowMessage.show(flag, Until.MES_ADD);
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);
	}
}
