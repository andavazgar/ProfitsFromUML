package com.horstmann.violet.framework.file;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DisplayWarning extends JFrame
{
	public static void DisplayDetectionMessage(String title, String message)
	{
		// Create  a JFrame
		JFrame frame = new JFrame("Warning");
		
		// Show a JOptionPane Dialog, mode 2 -> warning
		JOptionPane.showMessageDialog(frame, message, title, 2);
	}
	
}
