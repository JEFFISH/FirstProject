package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import until.CallBack;
import until.Until;
import view.ShowMessage;
import dao.BanjiDao;
import entity.Banji;

public class AddBanjiView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
	CallBack callBack;
	BanjiDao banjiDao = new BanjiDao();
	private static AddBanjiView instance;

	public static AddBanjiView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddBanjiView(callBack);
		}
		return instance;
	}

	private AddBanjiView(CallBack callBack) {
		this.callBack = callBack;

	}                             

	public void createFrame() {
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			frame.setVisible(true);
		}
		nameText.setText("");
	}

	public void init() {

		frame.setSize(200, 300);
		frame.setLocationRelativeTo(null);
		frame.setTitle("新增班级");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		// 名称
		JLabel nameLabel = new JLabel();
		nameLabel.setText("名称");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);

		JButton saveBtn = new JButton();
		saveBtn.setText("保存");
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel2.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				Banji banji = new Banji();
				banji.setName(name);
				int flagNum = banjiDao.add(banji);
				boolean flag=false;
				if(flagNum!=0)
				{
					flag=true;
				}
				ShowMessage.show(flag, Until.MES_ADD);					
				frame.dispose();
				callBack.call();
			}
		});
		frame.setVisible(true);
	}
}
