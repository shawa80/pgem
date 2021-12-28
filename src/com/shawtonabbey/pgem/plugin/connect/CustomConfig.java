package com.shawtonabbey.pgem.plugin.connect;

import java.util.HashMap;
import javax.security.auth.login.AppConfigurationEntry;

public class CustomConfig extends javax.security.auth.login.Configuration {

	private static final String AUTH_MODULE =
            "com.shawtonabbey.pgem.plugin.connect.KerberosLogin";
  
    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        if ("pgjdbc".equals(name)) {
        	
        	return new AppConfigurationEntry[] { 
        			new AppConfigurationEntry(
        					AUTH_MODULE,
        					AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, 
        					new HashMap<String, String>()
        			)
        	};
        }
        return new AppConfigurationEntry[0];
    }
	
}
