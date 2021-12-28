package com.shawtonabbey.pgem.plugin.connect;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import com.sun.security.auth.module.Krb5LoginModule;

public class KerberosLogin extends Krb5LoginModule {

	public static String cachePath = null;
	

	@Override
	public void initialize(Subject sub, CallbackHandler callback, Map<String, ?> sharedState, Map<String, ?> options) {
		
		var map = copy(options);
		
		if (cachePath != null) {
			map.put("useTicketCache", "true");
			map.put("ticketCache", cachePath);
		}
		
		map.put("tryFirstPass",  "true");
		map.put("doNotPrompt",  "false");
		map.put("debug",  "true");
		map.put("client",  "true");

		
		var handler = new UserPassPrompt();
		super.initialize(sub, handler, sharedState, map);
		
	}
	
	private Map<String, Object> copy(Map<String, ?> options) {
		
		var map = new HashMap<String, Object>();
		for (var key : options.keySet()) {
			var value = map.get(key);
			
			map.put(key, value);
		}
		return map;
	}


}
