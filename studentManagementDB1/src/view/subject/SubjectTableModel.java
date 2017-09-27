package view.subject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Subject;
import entity.Subject;


public class SubjectTableModel extends AbstractTableModel {
	List<Subject> list = null;
	String[] columnNames = { "id", "Ãû³Æ"};

	public SubjectTableModel(List<Subject> list) {
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

		}else {
			return null;
		}
	}

	public void setData(List<Subject> list) {
		this.list = list;
	}

}
