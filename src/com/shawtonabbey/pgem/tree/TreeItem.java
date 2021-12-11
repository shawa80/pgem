package com.shawtonabbey.pgem.tree;

import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class TreeItem extends JComponent {

	private static final long serialVersionUID = 1L;
	private JLabel lblLabel;
	
	public TreeItem() {
		setLayout(new BorderLayout(0, 0));
		
		lblLabel = new JLabel("New label");
		lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLabel.setIcon(new ImageIcon(TreeItem.class.getResource("/org/springframework/scripting/config/spring-lang.gif")));
		add(lblLabel);
	}
	
	public void setSelected(boolean selected) {
		
		if (selected) {	
			lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblLabel.setForeground(Color.WHITE);
			lblLabel.setOpaque(true);
			lblLabel.setBackground(Color.BLUE);
		}
	}
	
	public void setText(String txt) {
		lblLabel.setText(txt + " ");
	}
	
	public void setIcon(ImageIcon icon) {
		lblLabel.setIcon(icon);
	}
}
