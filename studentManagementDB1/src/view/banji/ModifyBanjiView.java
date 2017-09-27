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

public class ModifyBanjiView {
	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JFrame frame;
	CallBack callBack;
	Banji banji;
	int id;
	BanjiDao banjiDao = new BanjiDao();

	public ModifyBanjiView(CallBack callBack,int id) {
		this.callBack = callBack;
		this.id = id;
		banji=banjiDao.selectById(id);
	}

	public void init() {

		frame = new JFrame();
		frame.setSize(200, 300);
		frame.setLocationRelativeTo(null);
		frame.setTitle("修改班级");

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
		nameText.setText(banji.getName());
		panel1.add(nameText);

		JButton saveBtn = new JButton();
		saveBtn.setText("保存");
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel2.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				Banji banjiNew=new Banji();
				banjiNew.setName(name);
				int flagNum= banjiDao.update(banji,banjiNew);
				boolean flag=false;
				if(flagNum!=0)
				{
					flag=true;
				}
				ShowMessage.show(flag, Until.MES_MODIFY);
				frame.dispose();
				callBack.call();

			}
		});
		frame.setVisible(true);

	}

}
