Web Application: Resources
-------------------------------------------------------------------------------
This static Web content is stored in different files which will be requested
by the client seperately.


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/WebApp-Resources/




				
How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		

		
		
		