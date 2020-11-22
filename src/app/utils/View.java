package app.utils;

import javax.swing.JOptionPane;

public class View {
	
	public static String getString(String message, String title) {
		return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE).trim();
	}
}
