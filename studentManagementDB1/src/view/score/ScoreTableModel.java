package view.score;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import entity.Banji;
import entity.Score;


public class ScoreTableModel extends AbstractTableModel {
	List<Score> list = new ArrayList<Score>();
	Set<Score> listChanged=new HashSet<Score>();
	String[] columnNames = {"学生名","班级名","课程名","分数","等级"};

	public ScoreTableModel(List<Score> list) {
		this.list = list;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public String getColumnName(int columnIndex) {

		return columnNames[columnIndex];

	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return list.get(rowIndex).getStudent().getName();
		} else if (columnIndex == 1) {
			return list.get(rowIndex).getStudent().getBanji().getName();
		}else if (columnIndex == 2) {
			return list.get(rowIndex).getSubject().getName();
		}else if (columnIndex == 3) {
			if(list.get(rowIndex).getScore()==0)
			{
				return "";				
			}
			else
			{
				return list.get(rowIndex).getScore();
			}
		}else if (columnIndex == 4) {
			return list.get(rowIndex).getGrade();
		}
		else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==3)
		{
			return true;			
		}
		else
		{
			return false;
		}
	}
	
	public void setData(List<Score> list) {
		this.list = list;
	}

	//监听器
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
			Score score=new Score();
			score.setId(list.get(rowIndex).getId());
			score.setScore(Integer.valueOf(aValue.toString()));
			score.setStudent(list.get(rowIndex).getStudent());
			score.setGrade(list.get(rowIndex).getGrade());
			score.setSubject(list.get(rowIndex).getSubject());
			listChanged.add(score);
			list.get(rowIndex).setScore(Integer.valueOf(aValue.toString()));
	}
	
	public Set getListChanged()
	{
		return listChanged;
	}
}
