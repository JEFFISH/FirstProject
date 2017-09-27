package view.banji;

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
import dao.BanjiDao;
import entity.Banji;

public class BanjiView {

	SubToBanjiView subtobj;
	static List<Banji> list = new ArrayList<Banji>();
	static List<Banji> searchList = new ArrayList<Banji>();
	static BanjiTableModel model = null;
	JTable table;
	static BanjiDao banjiDao = new BanjiDao();
	JTextField nameText;
	static JFrame frame;
	JTextField stuNumsText;
	private static BanjiView instance;
	
	private BanjiView()
	{
		init();
	}
	
	public static BanjiView getInstance()
	{
		if(instance==null)
		{
			instance=new BanjiView();
		}
		else
		{
			refreshTable();
			frame.setVisible(true);
		}
		return instance;
	}
	
	private void init() {
		list = banjiDao.selectAll();
		if(list==null)
		{
			list=new ArrayList<Banji>();
		}
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		frame = new JFrame();
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("班级管理");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
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
		
		//人数查询
		JLabel stuNumsLabel = new JLabel();
		stuNumsLabel.setText("人数");
		panel1.add(stuNumsLabel);

		stuNumsText = new JTextField();
		stuNumsText.setPreferredSize(new Dimension(120, 30));
		panel1.add(stuNumsText);
		
		JButton searchBtn = new JButton();
		searchBtn.setText("查询");
		searchBtn.setPreferredSize(new Dimension(90, 30));
		//查询事件
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				searchList.clear();
				String name = nameText.getText();
				int stuNums = -1;
				if (!stuNumsText.getText().equals("")) {
					stuNums = Integer.parseInt(stuNumsText.getText());
				}
				searchList=banjiDao.serchByCondition(name,stuNums);				
				model.setData(searchList);
				model.fireTableDataChanged();
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new BanjiTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		JButton addBtn = new JButton();
		addBtn.setText(Until.MES_ADD);
		addBtn.setPreferredSize(new Dimension(70, 30));
		panel3.add(addBtn);
		//添加事件
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddBanjiView asv = AddBanjiView.getInstance(
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
		modifyBtn.setPreferredSize(new Dimension(70, 30));
		panel3.add(modifyBtn);
		//修改事件
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame, "没有选中数据");

				} else {
					ModifyBanjiView msv = new ModifyBanjiView(new CallBack() {
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
		deleteBtn.setPreferredSize(new Dimension(70, 30));
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
						Banji banji=searchList.get(index);
						list.remove(banji);
						int flagNum=banjiDao.delete(banji);
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
		
		//管理课程
		JButton manageSubBtn = new JButton();
		manageSubBtn.setText("管理课程");
		manageSubBtn.setPreferredSize(new Dimension(90, 30));
		manageSubBtn.addActionListener(new ActionListener() {
			
			private Banji banji1;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "没有选中数据");

				}
				else
				{
					subtobj=SubToBanjiView.getInstance(new CallBack() {
						
						@Override
						public void call() {
							// TODO Auto-generated method stub
								
						}
					},searchList.get(index));
					subtobj.createFrame(searchList.get(index));
				}
			}
		});
		panel3.add(manageSubBtn);
		
		
		
		frame.setVisible(true);

	}

	public static void refreshTable() {
		
		list=banjiDao.selectAll();
		searchList.clear();
		for(int i=0;i<list.size();i++)
		{
			searchList.add(list.get(i));
		}
		model.setData(list);
		model.fireTableDataChanged();
	}

}
