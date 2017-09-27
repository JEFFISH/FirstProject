package view.student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Student;

public class StudentTableModel extends AbstractTableModel {
	List<Student> list = null;
	String[] columnNames = { "id", "姓名", "性别", "年龄", "班级" };

	public StudentTableModel(List<Student> list) {
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
			return list.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return list.get(rowIndex).getName();

		} else if (columnIndex == 2) {
			return list.get(rowIndex).getSex();

		} 
		else if (columnIndex == 3) {
			return list.get(rowIndex).getAge();

		}
		else if (columnIndex == 4) {
			return list.get(rowIndex).getBanji().getName();

		}
		else {
			return null;
		}
	}

	public void setData(List<Student> list) {
		this.list = list;
	}

}
