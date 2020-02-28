Servlet: Simple Login
-------------------------------------------------------------------------------


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://lab.se.org:8080/Servlet-SimpleLogin/


How to access the Web application from cURL?
-------------------------------------------------------------------------------

$ curl -i -X GET http://localhost:8080/Servlet-SimpleLogin/

$ curl --proxy localhost:8010 -i -X POST -H 'Content-Type: application/x-www-form-urlencoded' --data-binary $'username=student&password=student&usergroup=Guest&action=Login' http://localhost:8080/Servlet-SimpleLogin/controller

$ curl -i -X GET "http://localhost:8080/Servlet-SimpleLogin/controller?username=student&password=student&usergroup=Guest&action=Login"
    
    
				
How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ cd wildfly-x.y.z.final
$ bin/standalone.sh

Within the project folder:
$ pwd
Servlet-SimpleLogin/
$ mvn wildfly:deploy
