package view.banji;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Banji;


public class BanjiTableModel extends AbstractTableModel {
	List<Banji> list = null;
	String[] columnNames = { "id", "Ãû³Æ", "ÈËÊý" };

	public BanjiTableModel(List<Banji> list) {
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
			return list.get(rowIndex).getStuNums();

		} else {
			return null;
		}
	}

	public void setData(List<Banji> list) {
		this.list = list;
	}

}
