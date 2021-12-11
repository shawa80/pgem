package com.shawtonabbey.pgem.plugin.connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConnectDialog extends JDialog
{

	private static final long serialVersionUID = 1L;

	// Variables declaration - do not modify
    private JLabel portText;
    private JSeparator jSeparator2;
    private JSeparator jSeparator4;
    private JPasswordField pass;
    private JTextField address;
    private JSeparator jSeparator5;
    private JTextField port;
    private JLabel ipAddressText;
    private JLabel passwordText;
    private JPanel jPanel2;
    private JButton cancel;
    private JTextField user;
    private JLabel userText;
    private JSeparator jSeparator1;
    private JPanel jPanel1;
    private JLabel dbText;
    private JSeparator jSeparator6;
    private JButton connect;
    private JSeparator jSeparator3;
    private JTextField database;
    private boolean selectState = false;
    
	private ConnectDialog self;
	private GridBagConstraints gridBagConstraints_1;
	private GridBagConstraints gridBagConstraints_2;
	private GridBagConstraints gridBagConstraints_3;
	private GridBagConstraints gridBagConstraints_4;
	private GridBagConstraints gridBagConstraints_5;
	private GridBagConstraints gridBagConstraints_6;
	private GridBagConstraints gridBagConstraints_7;
	private GridBagConstraints gridBagConstraints_8;
	private GridBagConstraints gridBagConstraints_9;
	private GridBagConstraints gridBagConstraints_10;
	private GridBagConstraints gridBagConstraints_11;
	private GridBagConstraints gridBagConstraints_12;
	private GridBagConstraints gridBagConstraints_13;
	private GridBagConstraints gridBagConstraints_14;
	private GridBagConstraints gridBagConstraints_15;
	private GridBagConstraints gridBagConstraints_16;
	private JCheckBox chckbxLoadPgSchema;
	private JLabel lblPgSchema;

	public ConnectDialog(Frame owner)
	{
		super(owner, "Connect to database", true);
		setType(Type.UTILITY);
		self = this;
                initComponents();
                

		connect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				self.setVisible(false);
				selectState = true;
			}
		});
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				self.setVisible(false);
				selectState = false;
			}
		});

	}


	public void set(String address, String port, String database, String user) {
		this.address.setText(address);
		this.port.setText(port);
		this.database.setText(database);
		this.user.setText(user);
	}
	
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        ipAddressText = new javax.swing.JLabel();
        portText = new javax.swing.JLabel();
        dbText = new javax.swing.JLabel();
        userText = new javax.swing.JLabel();
        passwordText = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        database = new javax.swing.JTextField();
        user = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        connect = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        GridBagLayout gbl_jPanel1 = new GridBagLayout();
        gbl_jPanel1.columnWeights = new double[]{1.0, 0.0, 0.0};
        jPanel1.setLayout(gbl_jPanel1);

        jPanel1.setBorder(new javax.swing.border.TitledBorder("Cridentals"));
        ipAddressText.setText("Ip Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(ipAddressText, gridBagConstraints);

        portText.setText("Port:");
        gridBagConstraints_8 = new java.awt.GridBagConstraints();
        gridBagConstraints_8.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_8.gridx = 0;
        gridBagConstraints_8.gridy = 1;
        gridBagConstraints_8.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(portText, gridBagConstraints_8);

        dbText.setText("DB:");
        gridBagConstraints_9 = new java.awt.GridBagConstraints();
        gridBagConstraints_9.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_9.gridx = 0;
        gridBagConstraints_9.gridy = 2;
        gridBagConstraints_9.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(dbText, gridBagConstraints_9);
        
        lblPgSchema = new JLabel("PG schema:");
        GridBagConstraints gbc_lblPgSchema = new GridBagConstraints();
        gbc_lblPgSchema.anchor = GridBagConstraints.EAST;
        gbc_lblPgSchema.insets = new Insets(0, 0, 5, 5);
        gbc_lblPgSchema.gridx = 0;
        gbc_lblPgSchema.gridy = 3;
        jPanel1.add(lblPgSchema, gbc_lblPgSchema);
        
        chckbxLoadPgSchema = new JCheckBox("");
        chckbxLoadPgSchema.setVerticalAlignment(SwingConstants.TOP);
        chckbxLoadPgSchema.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_chckbxLoadPgSchema = new GridBagConstraints();
        gbc_chckbxLoadPgSchema.anchor = GridBagConstraints.WEST;
        gbc_chckbxLoadPgSchema.insets = new Insets(0, 0, 5, 0);
        gbc_chckbxLoadPgSchema.gridx = 2;
        gbc_chckbxLoadPgSchema.gridy = 3;
        jPanel1.add(chckbxLoadPgSchema, gbc_chckbxLoadPgSchema);

        userText.setText("User:");
        gridBagConstraints_10 = new java.awt.GridBagConstraints();
        gridBagConstraints_10.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_10.gridx = 0;
        gridBagConstraints_10.gridy = 4;
        gridBagConstraints_10.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(userText, gridBagConstraints_10);

        passwordText.setText("Password:");
        gridBagConstraints_11 = new java.awt.GridBagConstraints();
        gridBagConstraints_11.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints_11.gridx = 0;
        gridBagConstraints_11.gridy = 5;
        gridBagConstraints_11.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(passwordText, gridBagConstraints_11);

        address.setColumns(40);

        gridBagConstraints_2 = new java.awt.GridBagConstraints();
        gridBagConstraints_2.insets = new Insets(0, 0, 5, 0);
        gridBagConstraints_2.gridx = 2;
        gridBagConstraints_2.gridy = 0;
        jPanel1.add(address, gridBagConstraints_2);

        port.setColumns(10);
        gridBagConstraints_3 = new java.awt.GridBagConstraints();
        gridBagConstraints_3.anchor = GridBagConstraints.WEST;
        gridBagConstraints_3.insets = new Insets(0, 0, 5, 0);
        gridBagConstraints_3.gridx = 2;
        gridBagConstraints_3.gridy = 1;
        jPanel1.add(port, gridBagConstraints_3);

        database.setColumns(40);
        gridBagConstraints_4 = new java.awt.GridBagConstraints();
        gridBagConstraints_4.insets = new Insets(0, 0, 5, 0);
        gridBagConstraints_4.gridx = 2;
        gridBagConstraints_4.gridy = 2;
        jPanel1.add(database, gridBagConstraints_4);

        user.setColumns(40);
        gridBagConstraints_5 = new java.awt.GridBagConstraints();
        gridBagConstraints_5.insets = new Insets(0, 0, 5, 0);
        gridBagConstraints_5.gridx = 2;
        gridBagConstraints_5.gridy = 4;
        jPanel1.add(user, gridBagConstraints_5);

        pass.setColumns(40);
        gridBagConstraints_6 = new java.awt.GridBagConstraints();
        gridBagConstraints_6.gridx = 2;
        gridBagConstraints_6.gridy = 5;
        jPanel1.add(pass, gridBagConstraints_6);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints_12 = new java.awt.GridBagConstraints();
        gridBagConstraints_12.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_12.gridx = 1;
        gridBagConstraints_12.gridy = 0;
        jPanel1.add(jSeparator2, gridBagConstraints_12);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints_13 = new java.awt.GridBagConstraints();
        gridBagConstraints_13.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_13.gridx = 1;
        gridBagConstraints_13.gridy = 4;
        jPanel1.add(jSeparator3, gridBagConstraints_13);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints_14 = new java.awt.GridBagConstraints();
        gridBagConstraints_14.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints_14.gridx = 1;
        gridBagConstraints_14.gridy = 5;
        jPanel1.add(jSeparator4, gridBagConstraints_14);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints_15 = new java.awt.GridBagConstraints();
        gridBagConstraints_15.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_15.gridx = 1;
        gridBagConstraints_15.gridy = 2;
        jPanel1.add(jSeparator5, gridBagConstraints_15);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints_16 = new java.awt.GridBagConstraints();
        gridBagConstraints_16.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints_16.gridx = 1;
        gridBagConstraints_16.gridy = 1;
        jPanel1.add(jSeparator6, gridBagConstraints_16);

        GridBagConstraints gbc_jPanel1 = new GridBagConstraints();
        gbc_jPanel1.insets = new Insets(5, 5, 0, 5);
        gbc_jPanel1.ipadx = 10;
        getContentPane().add(jPanel1, gbc_jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        connect.setText("Connect");
        jPanel2.add(connect, new java.awt.GridBagConstraints());

        cancel.setText("Cancel");
        gridBagConstraints_7 = new java.awt.GridBagConstraints();
        gridBagConstraints_7.anchor = GridBagConstraints.EAST;
        gridBagConstraints_7.gridx = 2;
        gridBagConstraints_7.gridy = 0;
        jPanel2.add(cancel, gridBagConstraints_7);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jSeparator1, gridBagConstraints);

        gridBagConstraints_1 = new java.awt.GridBagConstraints();
        gridBagConstraints_1.insets = new Insets(5, 0, 5, 0);
        gridBagConstraints_1.gridx = 0;
        gridBagConstraints_1.gridy = 1;
        getContentPane().add(jPanel2, gridBagConstraints_1);

        pack();
    }

    public boolean getUsePgSchema() {
    	return chckbxLoadPgSchema.isSelected();
    }
    
    public String getAddress()
	{
		return address.getText();
	}
	public String getPort()
	{
		return port.getText();
	}
	public String getDatabase()
	{
		return database.getText();
	}
	public String getUser()
	{
		return user.getText();
	}
	public String getPass()
	{
		return new String(pass.getPassword());
	}
	public boolean selectedConnect()
	{
		return selectState;
	}
}
