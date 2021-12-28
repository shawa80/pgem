package com.shawtonabbey.pgem.plugin.connect;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class UserPassPrompt implements CallbackHandler {

	
	public static String user = null;
	public static String password = null;
	
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		
		
		for (var callback : callbacks) {
			
			if (callback instanceof NameCallback) {
				var name = (NameCallback)callback;
				name.setName(user);
			}
			if (callback instanceof PasswordCallback) {
				var pass = (PasswordCallback)callback;
				pass.setPassword(password.toCharArray());
			}
			
		}
		
	}

}
