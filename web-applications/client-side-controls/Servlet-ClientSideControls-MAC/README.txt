Servlet: Client-Side Controls (using MAC)
---------------------------------------------------------------------

How to configure Wildfly to provide a system property?
---------------------------------------------------------------------

standalone.xml:
	<extensions>
	...
	</extensions>

	<system-properties>
        <property name="hmac.key" value="7cb46d33ae687fa8c98a712cfa7c0f98836405138040a9e03260d03ee8973885e6d800adcccd6dfa2961ea714f2ca3f8a1b1838cc6f86298f93d77368bae8d65"/>
    </system-properties>


How to test the hidden form field?
---------------------------------------------------------------------

If you request the page:
	URL: http://localhost:8080/Servlet-ClientSideControls-MAC/controller

you can see the hidden field embedded in the Web form (use Burp or Firebug):

	<form method="POST" action="controller">
   		<input type="hidden" name="role" value="357519aac576722cdb25266ca86d460e084abca8"/>
		...
	</form> 

By submitting this form, the hidden field will be send back to the server:

	POST /Servlet-ClientSideControls-MAC/controller HTTP/1.1
	Host: localhost:8080
	User-Agent: Mozilla/5.0 (X11; Fedora; Linux i686; rv:40.0) Gecko/20100101 Firefox/40.0
	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
	Accept-Language: en-US,en;q=0.5
	Accept-Encoding: gzip, deflate
	Referer: http://localhost:8080/Servlet-ClientSideControls-MAC/controller
	Connection: keep-alive
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 119
	
	role=357519aac576722cdb25266ca86d460e084abca8&firstName=Homer&lastName=Simpson&username=homer&password=homer&action=Add

If we try to change the value for role, we can see from the server log that the given user
is NOT in the right role.



How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		
		
