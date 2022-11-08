package com.shawtonabbey.pgem;

import java.util.ServiceLoader;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.plugin.PluginFactory;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class Pgem
{
	

	@Configuration
	@ComponentScan("com.shawtonabbey.pgem")
	public class AppConfig {
	 
	}
	
	
	public static void main(String[] args)
	{

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |UnsupportedLookAndFeelException e) {
			System.out.println("here" + e.getMessage());
		} 



		
		var context = new AnnotationConfigApplicationContext(
            AppConfig.class);
    
		//var loader = ServiceLoader.load(PluginFactory.class);
		//for (PluginFactory plugin : loader) {
		//    System.out.println(plugin);
		//    plugin.load(context).init();
		//}

		
		var beans = context.getBeansOfType(Plugin.class);
		
		beans.values().stream().forEach(b -> {
			b.register();
		});
		
		beans.values().stream().forEach(b -> {
			b.init();
		});
		
		var win = context.getBean(MainWindow.class);
		        
            
        ((PgemMainWindow)win).start();

       
		return;
	
	}

}
