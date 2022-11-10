package com.shawtonabbey.pgem.plugin.xml;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.SysPlugin;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class XmlPlugin extends PluginBase {

	@Autowired
	private MainWindow win;
	
	public void init() {

			dispatch.find(SysPlugin.MenuEv.class).listen((menuBar, ev) -> {
				
				var topMenu = new JMenu("xPath", true);
				menuBar.add(topMenu);
				var menuItem = new JMenuItem("test");
				topMenu.add(menuItem);
				menuBar.doLayout();

				menuItem.addActionListener((a) -> {
					
					var xwin = appContext.getBean(XmlQueryWin.class);
					xwin.setModel(new NamespaceModel());
					new XmlController(xwin);
					win.launchPanel(xwin);
				});
				
			});
	}
	
}
