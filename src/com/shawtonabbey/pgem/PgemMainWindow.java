package com.shawtonabbey.pgem;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.SysPlugin;
import com.shawtonabbey.pgem.query.QueryWindow;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.DBManager;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.ui.ATabbedPane;
import com.shawtonabbey.pgem.ui.MainWindow;
import com.shawtonabbey.pgem.ui.lambda.AMouseListener;
import com.shawtonabbey.pgem.ui.lambda.AWindowListener;
import com.shawtonabbey.pgem.ui.tree.ItemModel;
import com.shawtonabbey.pgem.ui.tree.Renderer;
import java.awt.event.*;

@Component
public class PgemMainWindow extends JFrame implements MainWindow
{
	private static final long serialVersionUID = 1L;
	private ATabbedPane desktop;
	private JTree tree;
	private JSplitPane splitPane;
	
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DBManager root;
	
	@Autowired
	private EventDispatch dispatch;
	
	private JMenu tools;

	public interface Added extends Add<PgemMainWindow> {}
	
	PgemMainWindow()
	{
		super("pGem");

		addWindowListener((AWindowListener)(e) -> {
				System.exit(0);	
		});
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	}
	
	public void message(String msg) {
		
		JOptionPane.showMessageDialog(this,
			    msg,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		
	}
	
	public void start() {
		
		tree = new JTree(root);
		root.load();
		root.setTree(tree);
	
		
		tree.setCellRenderer(new Renderer());
		
		tree.addMouseListener((AMouseListener)(e)-> {
			ItemModel node;

			if ((e.getModifiersEx() & InputEvent.META_DOWN_MASK) == InputEvent.META_DOWN_MASK)
			{
			
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				if(selRow != -1)
				{
					tree.setSelectionRow(selRow);
					node = (ItemModel)tree.getLastSelectedPathComponent();
					var menu = node.getMenu();
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		desktop = appContext.getBean(ATabbedPane.class);
		//desktop = new ATabbedPane();


		setSize(1000, 700);
		var m = getMenu();
		setJMenuBar(m);

		splitPane.setLeftComponent(new JScrollPane(tree)	);
		splitPane.setRightComponent(desktop);
		getContentPane().add(splitPane);

		
		setVisible(true);
		splitPane.setDividerLocation(0.25);
		
		dispatch.find(SysPlugin.MenuEv.class).fire(o->o.added(m, null));
		
		dispatch.find(Added.class).fire(o->o.added(this, new Event()));
	}
	
	public JTree getTree()
	{
		return tree;
	}
	public ATabbedPane getDesktop()
	{
		return desktop;
	}

	private JMenuBar getMenu()
	{


		var menu = new JMenuBar();
		tools = new JMenu("Tools", true);
		
		menu.add(tools);
		return menu;
	}
	
	public void addMenu(JMenuItem menu) {
		tools.add(menu);
	}

	
	/**
	 * @param connection
	 * @param query
	 */
	public void launchQueryWin(DBC db, String query) {

		var sw = new SwingWorker<DBC>()
				.setWork(() -> db.duplicate())
				.thenOnEdt((dbc) -> {
					
					var qw = appContext.getBean(QueryWindow.class);
					qw.setConnection(dbc);
					qw.init();
					qw.enableSql();
					qw.setSql(query);
					
					getDesktop().addTab((java.awt.Component)qw);
				});
		sw.start();

	}

	public void launchPanel(JPanel panel)
	{
		getDesktop().addTab(panel);
	}
	
//	@Override
//	public void launchImport() {
//		
//		var win = new CsvImportWin();
//		win.setVisible(true);
//		
//	}
	
	
}
