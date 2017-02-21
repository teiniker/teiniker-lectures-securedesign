Servlet: Servlet Filter
-------------------------------------------------------------------------------


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Filter/

Have a look on Wildfly's console output:
19:23:43,261 INFO  [org.se.lab.RequestFilter] (default task-11) ---------------------------------------------------
19:23:43,266 INFO  [org.se.lab.RequestFilter] (default task-11) Request Filter
19:23:43,267 INFO  [org.se.lab.RequestFilter] (default task-11) ---------------------------------------------------
19:23:43,269 INFO  [org.se.lab.RequestFilter] (default task-11) Server:
19:23:43,269 INFO  [org.se.lab.RequestFilter] (default task-11)     server name = localhost
19:23:43,270 INFO  [org.se.lab.RequestFilter] (default task-11)     server port = 8080
19:23:43,271 INFO  [org.se.lab.RequestFilter] (default task-11) ---------------------------------------------------
19:23:43,271 INFO  [org.se.lab.RequestFilter] (default task-11) Client:
19:23:43,272 INFO  [org.se.lab.RequestFilter] (default task-11)     remote address = 127.0.0.1
19:23:43,272 INFO  [org.se.lab.RequestFilter] (default task-11)     remote host    = 127.0.0.1
19:23:43,274 INFO  [org.se.lab.RequestFilter] (default task-11)     remote port    = 39800
19:23:43,274 INFO  [org.se.lab.RequestFilter] (default task-11)     protocol       = HTTP/1.1
19:23:43,274 INFO  [org.se.lab.RequestFilter] (default task-11)     https          = false
19:23:43,274 INFO  [org.se.lab.RequestFilter] (default task-11) ---------------------------------------------------
19:23:43,274 INFO  [org.se.lab.RequestFilter] (default task-11) Parameters:
19:23:43,275 INFO  [org.se.lab.RequestFilter] (default task-11)     password  = student
19:23:43,276 INFO  [org.se.lab.RequestFilter] (default task-11)     usergroup  = Guest
19:23:43,276 INFO  [org.se.lab.RequestFilter] (default task-11)     action  = Login
19:23:43,276 INFO  [org.se.lab.RequestFilter] (default task-11)     username  = student
19:23:43,279 INFO  [org.se.lab.RequestFilter] (default task-11) ---------------------------------------------------

19:23:43,280 INFO  [org.se.lab.ResponseFilter] (default task-11) ---------------------------------------------------
19:23:43,280 INFO  [org.se.lab.ResponseFilter] (default task-11) Response Filter
19:23:43,282 INFO  [org.se.lab.ResponseFilter] (default task-11) ---------------------------------------------------
19:23:43,283 INFO  [org.se.lab.ResponseFilter] (default task-11) <html>  <head>    <title>Login Servlet</title>  </head>  <body>    <h1> Request Parameters: </h1><p>    <pre>      username  = "student"<br/>      password  = "student"<br/>      usergroup = "Guest"<br/>      action    = "Login"<br/>    </pre>  </body></html>


How to access the Web application from cURL?
-------------------------------------------------------------------------------

$ curl --proxy localhost:8010 -i -X POST -H 'Content-Type: application/x-www-form-urlencoded' --data-binary $'username=student&password=student&usergroup=Guest&action=Login' http://localhost:8080/Servlet-Filter/login.html


How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		