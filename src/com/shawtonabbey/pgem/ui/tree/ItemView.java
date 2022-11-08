package com.shawtonabbey.pgem.ui.tree;

import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class ItemView extends JComponent {

	private static final long serialVersionUID = 1L;
	private JLabel lblLabel;
	
	public ItemView() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblLabel = new JLabel("New label");
		lblLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//lblLabel.setIcon(new ImageIcon(ItemView.class.getResource("/org/springframework/scripting/config/spring-lang.gif")));
		add(lblLabel);
		
		var padding = new JLabel("                    ");
		padding.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(padding);
		
		
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
