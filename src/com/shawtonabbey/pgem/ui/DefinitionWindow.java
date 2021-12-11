package com.shawtonabbey.pgem.ui;
//package com.shawtonabbey.pgem;
//import javax.swing.*;
//import javax.swing.border.*;
//import java.awt.*;
//
//import javax.swing.event.*;
//
//import com.shawtonabbey.pgem.database.ARecordSet;
//import com.shawtonabbey.pgem.database.DBC;
//import com.shawtonabbey.pgem.tree.TableInstance;
//
//import java.awt.event.*;
//import java.util.*;
//
//public class DefinitionWindow extends JInternalFrame implements TableModelListener
//{
//	private static final long serialVersionUID = 1L;
//
//	private MyTableModel tableModel;
//	private boolean newTable;
//
//	private String changeText;
//	private DBC conn;
//	private SQLDataTypes typesDatabase;
//	private DefinitionWindow self;
//
//	// Variables declaration - do not modify//GEN-BEGIN:variables
//	private JButton keys;
//	private JPanel buttonBar;
//	private JToolBar fileBar;
//	private JToolBar keysBar;
//	private JPanel tablePanel;
//	private JButton changeCode;
//	private JButton indexes;
//	private JButton cancel;
//	private JButton addRow;
//	private JPanel tableDiscription;
//	private JButton tableCode;
//	private JScrollPane scrollPanel;
//	private JTable sqlTable;
//	private JButton commit;
//	private JComboBox dataTypes;
//	private JTabbedPane tabs;
//	private TableIndex tableIndexes;
//	// End of variables declaration//GEN-END:variables
//	private TableInstance table;
//
//	public DefinitionWindow(DBC c, TableInstance t)
//	{
//		super("Definition", true, true, true, true);
//
//		ARecordSet recordSet;
//		String sql = "";
//		changeText = "";
//
//		conn = c;
//		table = t;
//		self = this;
//
//		newTable = false;
//
//		initComponents();
//		//setVisible(true);
//
//		if (!newTable)
//		{
//			/*********************************************************/
//			//Get Table Attributes
//			/*********************************************************/
//			String primaryKeyStr = "";
//			Hashtable primaryKey = new Hashtable();
//
//			sql = "select relname, indkey from pg_class c " + 
//			"left join pg_index i on i.indrelid = c.oid " +
//			" where relname = '" + table.getName() + "';";
//			recordSet = conn.exec(sql);
//			
//			if (recordSet.next())
//			{
//				primaryKeyStr = recordSet.get("indkey");
//			}
//			recordSet.close();
//			if (primaryKeyStr == null)
//				primaryKeyStr = "";
//
//			StringTokenizer st = new StringTokenizer(primaryKeyStr);
//			while (st.hasMoreTokens())
//			{
//				String item = st.nextToken();
//				primaryKey.put(item, "yes");
//			}
//
//
//			/*********************************************************/
//			//Get Columns
//			/*********************************************************/
//
//			sql = "select a.attname, t.typname, a.attlen, a.attnotnull, a.attnum, d.adsrc" + 
//			" from pg_attribute a " +
//			" right join pg_class c on c.oid = a.attrelid " +
//			" left join pg_type t on t.oid = a.atttypid " +
//			" left join pg_attrdef d on d.adrelid = a.attrelid and d.adnum = a.attnum " +
//			" where relname = '" + table.getName() + "' and attnum >= 0;";
//
//			recordSet = conn.exec(sql);
//			tableModel.removeRow(0);
//
//			boolean isNull;
//			boolean isPrimary;
//			String typeLengthStr = "";
//			while (recordSet.next())
//			{
//				//check if we allow nulls
//				if ("f".equals(recordSet.get("attnotnull")))
//					isNull = false;
//				else
//					isNull = true;
//
//				//check to see if the column is part of the primary key
//				if ("yes".equals(primaryKey.get(recordSet.get("attnum")+"")))
//					isPrimary = true;
//				else
//					isPrimary = false;
//
//				typeLengthStr = "";
//				//get Length not sure varchar length works
//				try {
//					int typeLength;
//					typeLength = Integer.parseInt(recordSet.get("attlen"));
//					if (typeLength > 0)
//					{
//						typeLengthStr = typeLength + "";
//					}
//
//				} catch (NumberFormatException e)
//				{
//				}
//
//				tableModel.addRow(new Object[] {recordSet.get("attname"), new Boolean(isPrimary), 
//							recordSet.get("typname"),
//							typeLengthStr, new Boolean(isNull), recordSet.get("adsrc")});
//			}
//
//			recordSet.close();
//			tableIndexes.setData(new TableToListModel(tableModel, 0));
//		}
//	}
//
//	private void initComponents() 
//	{//GEN-BEGIN:initComponents
//		GridBagConstraints gridBagConstraints;
//
//		buttonBar = new JPanel();
//		keysBar = new JToolBar();
//		keys = new JButton();
//		indexes = new JButton();
//		fileBar = new JToolBar();
//		commit = new JButton();
//		addRow = new JButton();
//		changeCode = new JButton();
//		cancel = new JButton();
//		tableCode = new JButton();
//		scrollPanel = new JScrollPane();
//		tableDiscription = new JPanel();
//		tablePanel = new JPanel();
//		sqlTable = new JTable();
//		tabs = new JTabbedPane();
//		tableIndexes = new TableIndex(conn, table);
//
//		buttonBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//		keys.setText("keys");
//		keysBar.add(keys);
//
//		indexes.setText("Indexes");
//		keysBar.add(indexes);
//
//		buttonBar.add(keysBar);
//
//		commit.setText("commit");
//		commit.addActionListener(new ActionListener()
//                {
//                        public void actionPerformed(ActionEvent e)
//                        {
//				self.commit();
//                        }
//                });
//		fileBar.add(commit);
//
//		changeCode.setText("change code");
//		fileBar.add(changeCode);
//
//		cancel.setText("cancel");
//		fileBar.add(cancel);
//
//		tableCode.setText("table code");
//		fileBar.add(tableCode);
//
//		addRow.setText("add row");
//		addRow.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				tableModel.addRow(new Object[] {"", new Boolean(false), "", "", new Boolean(false), ""});
//			}
//		});
//		fileBar.add(addRow);
//
//		buttonBar.add(fileBar);
//
//		getContentPane().add(buttonBar, BorderLayout.NORTH);
//
//		//tableDiscription.setLayout(new FlowLayout());
//
//		tableDiscription.setBorder(new TitledBorder("Table Definition"));
//		//tablePanel.setLayout(new GridBagLayout());
//
//		sqlTable.setBorder(new EtchedBorder());
//
//		tableModel = new MyTableModel(
//			new Object [][] 
//			{
//				{"", new Boolean(false), "", "", new Boolean(false), ""}
//			},
//			new String [] 
//			{
//				"Col Name", "Primary", "Type", "Length", "Allow Null", "Default"
//			}
//		);
//		sqlTable.setModel(tableModel);
//		sqlTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		tableModel.addTableModelListener(this);
//
//
//		//set up the drop down for the data types
//		typesDatabase = new SQLDataTypes(conn);
//		dataTypes = new JComboBox(typesDatabase.getNames());
//		dataTypes.setEditable(true);
//		DefaultCellEditor editor = new DefaultCellEditor(dataTypes);
//		sqlTable.getColumnModel().getColumn(2).setCellEditor(editor);
//
//
//		setSize(300, 200);
//
//		JScrollPane sp = new JScrollPane();
//		//tableDiscription.add(sp);
//		sp.setViewportView(sqlTable);
//		tabs.add("Definition", sp);
//		tabs.add("Foriegn Keys", new JTextField("test"));
//		tabs.add("Indexes", tableIndexes);
//
//		getContentPane().add(tabs, BorderLayout.CENTER);
//
//
//		pack();
//	}//GEN-END:initComponents
//    
//	public void tableChanged(TableModelEvent event)
//	{
//		int value;
//
//
//		//Set the default size of the data
//		if (event.getColumn() == 2)
//		{
//			value = typesDatabase.getLength((String)tableModel.getValueAt(event.getFirstRow(), 2));
//			if (value > 0)	//we have a valid size
//			{
//				tableModel.setValueAt(""+value, event.getFirstRow(), 3);
//			}
//			else //size is unknown... set it to blank
//			{
//				tableModel.setValueAt("", event.getFirstRow(), 3);
//			}
//		}
//
//	}
//
//
//	void commit()
//	{
//		String sql = "";
//		int rows = tableModel.getRowCount();
//
//		//make sure we have something to save
//		if (rows <= 0)
//		{
//			JOptionPane.showMessageDialog(this, "No rows are presnt in the table", "Error", 
//				JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		System.out.println("Commit changes");
//
//		if (newTable)
//		{
//			sql = "create table test (";
//
//			for (int i = 0; i < rows; i++)
//			{
//				sql += tableModel.getValueAt(i, 0);	//name
//				sql += " ";
//				sql += tableModel.getValueAt(i, 2);	//datatype
//				if ("varchar".equals(tableModel.getValueAt(i, 2)))  //add length for varchar
//				{
//					try {
//						if (Integer.parseInt((String)tableModel.getValueAt(i, 3)) > 0)
//						{
//							sql += " (" + tableModel.getValueAt(i, 3) + ")";
//						}
//					} catch (NumberFormatException e)
//					{}
//
//				}
//				if (!((Boolean)tableModel.getValueAt(i, 4)).booleanValue())	 //not null
//				{
//					sql += " ";
//					sql += " not NULL";
//				}
//				if ("".equals(tableModel.getValueAt(i, 5)))		//get default
//				{
//					sql += " ";
//					sql += " default " + tableModel.getValueAt(i, 5);
//				}
//				if (i < rows-1) //add, if we have more data
//					sql += ", ";
//			}			
//
//			sql += " );";
//
//			System.out.println(sql);
//		}
//	}
//}
