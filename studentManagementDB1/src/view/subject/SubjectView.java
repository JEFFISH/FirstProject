package view.subject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import dao.SubjectDao;
import entity.Subject;

public class SubjectView {

	static List<Subject> list = new ArrayList<Subject>();
	static List<Subject> searchList = new ArrayList<Subject>();
	static SubjectTableModel model = null;
	JTable table;
	static SubjectDao subDao = new SubjectDao();
	JTextField nameText;
	static JFrame frame;
	JTextField stuNumsText;
	private static SubjectView instance;
	
	private SubjectView()
	{
		init();
	}
	
	public static SubjectView getInstance()
	{
		if(instance==null)
		{
			instance=new SubjectView();
		}
		else
		{
			refreshTable();
			frame.setVisible(true);
		}
		return instance;
	}
	
	private void init() {
		list = subDao.selectAll();
		if(list==null)
		{
			list=new ArrayList<Subject>();
		}
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		frame = new JFrame();
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("科目管理");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 名称查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText("名称");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);
		
		JButton searchBtn = new JButton();
		searchBtn.setText("查询");
		searchBtn.setPreferredSize(new Dimension(90, 30));
		//查询事件
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				searchList.clear();
				String name = nameText.getText();
				searchList=subDao.serchByCondition(name);				
				model.setData(searchList);
				model.fireTableDataChanged();
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new SubjectTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		JButton addBtn = new JButton();
		addBtn.setText(Until.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);
		//添加事件
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddSubjectView asv = AddSubjectView.getInstance(
						new CallBack() {
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
		//修改事件
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame, "没有选中数据");

				} else {
					ModifySubjectView msv = new ModifySubjectView(new CallBack() {
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
		//删除事件
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "没有选中数据");

				} else {

					int type = JOptionPane.showConfirmDialog(null, "是否删除数据?");
					if (type == 0) {
						Subject sub=searchList.get(index);
						list.remove(sub);
						int flagNum=subDao.delete(sub);
						boolean flag=false;
						if(flagNum!=0)
						{
							flag=true;
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
		
		list=subDao.selectAll();
		searchList.clear();
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		model.setData(list);
		model.fireTableDataChanged();
	}

}
