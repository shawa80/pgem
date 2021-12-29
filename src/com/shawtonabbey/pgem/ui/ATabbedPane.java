package com.shawtonabbey.pgem.ui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.shawtonabbey.pgem.ui.lambda.AMouseListener;

@org.springframework.stereotype.Component
public class ATabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	//@Autowired
	//private DebugWindow win;
	
	public ATabbedPane() {
		
	}
		
	public void addTab(Component comp) {

			
		this.addTab(comp.toString(), comp);
		var c = this.getTabCount()-1;
		
		
		var p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new FlowLayout());
		var b = new JButton("X");
		
		b.addMouseListener((AMouseListener)(me) -> removeTab(comp));
		
		
		var l = new JLabel(comp.toString());
		
		
		p.add(l);
		p.add(b);
		
		this.setTabComponentAt(c, p);
		
	}
	
	private void removeTab(Component comp) {
		
		var size = getTabCount();
		for (var i = 0; i < size; i++) {
			
			var x = getComponentAt(i);
			
			if (x == comp)
				removeTabAt(i);
		}		
	}
	
}
