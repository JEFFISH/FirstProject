package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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

import until.CallBack;
import until.Until;
import view.ShowMessage;
import view.subject.SubjectTableModel;
import dao.BanjiDao;
import dao.SubToBanjiDao;
import dao.SubjectDao;
import entity.Banji;
import entity.Subject;

public class SubToBanjiView {
	
	List<Subject> listall=new ArrayList<Subject>();
	List<Subject> listid=new ArrayList<Subject>();
	static SubjectTableModel model = null;
	JFrame frame;
	CallBack callBack;
	SubToBanjiDao subToBanjiDao=new SubToBanjiDao();
	Banji banji;
	SubjectDao subjectDao=new SubjectDao();
	JComboBox box;
	JTable jTable;
	JPanel jPanel2;
	private JLabel jLabel;
	private static SubToBanjiView instance;

	public static SubToBanjiView getInstance(CallBack callBack,Banji banji) {
		if (instance == null) {
			instance = new SubToBanjiView(callBack,banji);
		}
		return instance;
	}

	private SubToBanjiView(CallBack callBack,Banji banji) {
		this.callBack = callBack;
		this.banji=banji;
	}

	public void createFrame(Banji banji) {
		this.banji=banji;
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			jLabel.setText(banji.getName());
			refreshTable();
			frame.setVisible(true);
			
		}
	}

	public void init() {
		listall=subjectDao.selectAll();
		listid=subToBanjiDao.selectById(banji.getId());
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("管理课程");

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		//文本框
		JPanel jPanel0=new JPanel();
		jLabel=new JLabel();
		jPanel0.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
		jLabel.setText(banji.getName());
		jPanel0.add(jLabel);
		mainPanel.add(jPanel0);
		//表
		JPanel jPanel1=new JPanel();
		model=new SubjectTableModel(listid);
		jTable=new JTable(model);
		JScrollPane jScrollPane=new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(600,400));
		jPanel1.add(jScrollPane);
		mainPanel.add(jPanel1);
		
		jPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
		createBox();
		
		
		//增加按钮
		JButton jAddButton=new JButton();
		jAddButton.setText("增加");
		jAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int flag=-1;
				for(int i=0;i<listall.size();i++)
				{
					if(listall.get(i).getName().equals((String)box.getSelectedItem()))
					{
						flag=subToBanjiDao.add(listall.get(i), banji);
						break;
					}
				}
				if(flag!=-1)
				{
					ShowMessage.show(true, Until.MES_ADD);					
				}
				refreshTable();
			}
		});
		
		//删除按钮
		JButton jDeleteButton=new JButton();
		jDeleteButton.setText("删除");
		jDeleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int flag=-1;
				int index=jTable.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame, "没有选中数据");
				}
				else
				{
					flag=subToBanjiDao.delete(listid.get(index), banji);
					if(flag!=-1)
					{
						ShowMessage.show(true, Until.MES_DELETE);					
					}
					refreshTable();
				}
			}
		});
		
		jPanel2.add(jAddButton);
		jPanel2.add(jDeleteButton);
		mainPanel.add(jPanel2);
		frame.setVisible(true);
	}

	public void createBox() {
		//下拉框
		box=new JComboBox();
		boolean issame=false;
		for(int i=0;i<listall.size();i++)
		{
			issame=false;
			for(int j=0;j<listid.size();j++)
			{
				if(listall.get(i).getId()==listid.get(j).getId())
				{
					issame=true;
					break;
				}
			}
			if(!issame)
			{
				box.addItem(listall.get(i).getName());
			}
		}
		box.setPreferredSize(new Dimension(100,20));
		jPanel2.add(box);
	}
	
	public void refreshTable()
	{
		listall=subjectDao.selectAll();
		listid=subToBanjiDao.selectById(banji.getId());
		boolean issame=false;
		box.removeAllItems();
		for(int i=0;i<listall.size();i++)
		{
			issame=false;
			for(int j=0;j<listid.size();j++)
			{
				if(listall.get(i).getId()==listid.get(j).getId())
				{
					issame=true;
					break;
				}
			}
			if(!issame)
			{
				box.addItem(listall.get(i).getName());
			}
		}
		model.setData(listid);
		model.fireTableDataChanged();
	}
}
