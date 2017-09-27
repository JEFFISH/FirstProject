package view;

import javax.swing.JOptionPane;

import until.Until;

public class ShowMessage {
	public static void show(boolean flag, String type) {
		if (flag) {
			JOptionPane.showMessageDialog(null, type + Until.MES_SUCCESS);
		} else {
			JOptionPane.showMessageDialog(null, type + Until.MES_FAIL);

		}
	}
}
