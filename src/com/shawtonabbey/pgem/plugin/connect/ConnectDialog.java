package com.shawtonabbey.pgem.plugin.connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

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
	private GridBagConstraints gridBagConstraints_7;
	private GridBagConstraints gridBagConstraints_8;
	private GridBagConstraints gridBagConstraints_9;
	private GridBagConstraints gridBagConstraints_12;
	private GridBagConstraints gridBagConstraints_13;
	private GridBagConstraints gridBagConstraints_14;
	private GridBagConstraints gridBagConstraints_15;
	private GridBagConstraints gridBagConstraints_16;
	private JCheckBox chckbxLoadPgSchema;
	private JLabel lblPgSchema;
	private JPanel panel;
	private JTabbedPane tabbedPane;
	private JPanel PassAuth;
	private JPanel KerberosAuthInfo;
	private JCheckBox useKrbFile;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField krbUser;
	private JTextField krbFile;
	private JPasswordField krbPass;

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
        address = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        database = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        connect = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
        gridBagLayout.columnWeights = new double[]{1.0};
        getContentPane().setLayout(gridBagLayout);

        GridBagLayout gbl_jPanel1 = new GridBagLayout();
        gbl_jPanel1.columnWeights = new double[]{1.0, 0.0, 0.0};
        jPanel1.setLayout(gbl_jPanel1);

        jPanel1.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        gbc_jPanel1.fill = GridBagConstraints.HORIZONTAL;
        gbc_jPanel1.anchor = GridBagConstraints.NORTH;
        gbc_jPanel1.gridx = 0;
        gbc_jPanel1.gridy = 0;
        gbc_jPanel1.insets = new Insets(5, 5, 5, 0);
        gbc_jPanel1.ipadx = 10;
        getContentPane().add(jPanel1, gbc_jPanel1);
        
        panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        getContentPane().add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        panel.add(tabbedPane);
        
        PassAuth = new JPanel();
        PassAuth.setBorder(new TitledBorder(null, "test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        tabbedPane.addTab("Pass", null, PassAuth, null);
        GridBagLayout gbl_PassAuth = new GridBagLayout();
        gbl_PassAuth.columnWidths = new int[] {0, 30};
        gbl_PassAuth.rowHeights = new int[]{0, 0};
        gbl_PassAuth.columnWeights = new double[]{0.0, 0.0};
        gbl_PassAuth.rowWeights = new double[]{0.0, 0.0};
        PassAuth.setLayout(gbl_PassAuth);
                        userText = new javax.swing.JLabel();
                        GridBagConstraints gbc_userText = new GridBagConstraints();
                        gbc_userText.anchor = GridBagConstraints.EAST;
                        gbc_userText.insets = new Insets(0, 0, 5, 5);
                        gbc_userText.gridx = 0;
                        gbc_userText.gridy = 0;
                        PassAuth.add(userText, gbc_userText);
                        
                                userText.setText("User:");
                        user = new javax.swing.JTextField();
                        GridBagConstraints gbc_user = new GridBagConstraints();
                        gbc_user.insets = new Insets(0, 0, 5, 5);
                        gbc_user.gridx = 1;
                        gbc_user.gridy = 0;
                        PassAuth.add(user, gbc_user);
                        
                                user.setColumns(40);
                        passwordText = new javax.swing.JLabel();
                        GridBagConstraints gbc_passwordText = new GridBagConstraints();
                        gbc_passwordText.anchor = GridBagConstraints.EAST;
                        gbc_passwordText.insets = new Insets(0, 0, 5, 5);
                        gbc_passwordText.gridx = 0;
                        gbc_passwordText.gridy = 1;
                        PassAuth.add(passwordText, gbc_passwordText);
                        
                                passwordText.setText("Password:");
                        pass = new javax.swing.JPasswordField();
                        GridBagConstraints gbc_pass = new GridBagConstraints();
                        gbc_pass.insets = new Insets(0, 0, 5, 5);
                        gbc_pass.gridx = 1;
                        gbc_pass.gridy = 1;
                        PassAuth.add(pass, gbc_pass);
                        
                                pass.setColumns(40);
        
        KerberosAuthInfo = new JPanel();
        KerberosAuthInfo.setBorder(new TitledBorder(null, "Kerberos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        tabbedPane.addTab("Kerberos", null, KerberosAuthInfo, null);
        GridBagLayout gbl_KerberosAuthInfo = new GridBagLayout();
        gbl_KerberosAuthInfo.columnWidths = new int[]{0, 0, 0};
        gbl_KerberosAuthInfo.rowHeights = new int[]{0, 0, 0, 0};
        gbl_KerberosAuthInfo.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_KerberosAuthInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0};
        KerberosAuthInfo.setLayout(gbl_KerberosAuthInfo);
        
        useKrbFile = new JCheckBox("Cache File");
        GridBagConstraints gbc_useKrbFile = new GridBagConstraints();
        gbc_useKrbFile.insets = new Insets(0, 0, 5, 5);
        gbc_useKrbFile.gridx = 0;
        gbc_useKrbFile.gridy = 0;
        KerberosAuthInfo.add(useKrbFile, gbc_useKrbFile);
        
        krbFile = new JTextField();
        GridBagConstraints gbc_krbFile = new GridBagConstraints();
        gbc_krbFile.insets = new Insets(0, 0, 5, 0);
        gbc_krbFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_krbFile.gridx = 1;
        gbc_krbFile.gridy = 0;
        KerberosAuthInfo.add(krbFile, gbc_krbFile);
        krbFile.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("Fallback creds");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        KerberosAuthInfo.add(lblNewLabel, gbc_lblNewLabel);
        
        lblNewLabel_1 = new JLabel("User:");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        KerberosAuthInfo.add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        krbUser = new JTextField();
        GridBagConstraints gbc_krbUser = new GridBagConstraints();
        gbc_krbUser.insets = new Insets(0, 0, 5, 0);
        gbc_krbUser.fill = GridBagConstraints.HORIZONTAL;
        gbc_krbUser.gridx = 1;
        gbc_krbUser.gridy = 2;
        KerberosAuthInfo.add(krbUser, gbc_krbUser);
        krbUser.setColumns(10);
        
        lblNewLabel_2 = new JLabel("Password:");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 3;
        KerberosAuthInfo.add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        krbPass = new JPasswordField();
        GridBagConstraints gbc_krbPass = new GridBagConstraints();
        gbc_krbPass.fill = GridBagConstraints.HORIZONTAL;
        gbc_krbPass.gridx = 1;
        gbc_krbPass.gridy = 3;
        KerberosAuthInfo.add(krbPass, gbc_krbPass);

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
        gridBagConstraints_1.insets = new Insets(5, 0, 0, 0);
        gridBagConstraints_1.gridx = 0;
        gridBagConstraints_1.gridy = 2;
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
	
	public boolean isKerberosfile() {
		return useKrbFile.getModel().isSelected();
	}
	
	public String getKerberosFile()
	{
		return this.krbFile.getText();
	}
	
	public String getKerberosUser()
	{
		return this.krbUser.getText();
	}
	public String getKerberosPass()
	{
		return new String(this.krbPass.getPassword());
	}

	public int getTab() {
		return this.tabbedPane.getSelectedIndex();
	}
	public void setTab(int tab) {
		this.tabbedPane.setSelectedIndex(tab);
	}

	public void setKerberos(String krbUser, String krbFile, boolean useFile) {
		this.krbUser.setText(krbUser);
		this.krbFile.setText(krbFile);
		this.useKrbFile.getModel().setSelected(useFile);
	}
}
