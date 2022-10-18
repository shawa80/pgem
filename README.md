# pgem

Postgresql query tool.  Still early in development.

![image](https://user-images.githubusercontent.com/25357671/152090102-7aac4bc3-880d-42b9-b85b-a71ff5f0e16b.png)


pgjdbc {
  com.shawtonabbey.pgem.plugin.connect.KerberosLogin required
  //com.shawtonabbey.pgem.security.KerberosLogin required
  //com.sun.security.auth.module.Krb5LoginModule required
  tryFirstPass=true
  doNotPrompt=false
  debug=true
  client=true
  //useTicketCache=true
  //ticketCache = "/home/xxxx/cache.txt";
  ;
};
//klist -c cache.txt
